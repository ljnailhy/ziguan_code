package com.jk.asset.service.impl.proceeding;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.enums.FlowBillEnum;
import com.jk.asset.mapper.proceeding.RecoveryAdjustTrialMapper;
import com.jk.asset.model.dto.proceeding.RecoveryAdjustTrialDTO;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.entity.proceeding.RecoveryAdjustTrial;
import com.jk.asset.model.entity.proceeding.RecoveryLitigationExt;
import com.jk.asset.model.request.page.proceeding.RecoveryAdjustTrialPageRequest;
import com.jk.asset.model.request.proceeding.RecoveryAdjustTrialRequest;
import com.jk.asset.model.request.proceeding.RecoveryAdjustTrialRequest.AdjustTrialTypeEnum;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.proceeding.RecoveryAdjustTrialHandler;
import com.jk.asset.service.handler.proceeding.RecoveryLitigationExtHandler;
import com.jk.asset.service.handler.proceeding.RecoveryLitigationHandler;
import com.jk.asset.service.proceeding.RecoveryAdjustTrialService;
import com.jk.common.constant.PlatformConstant;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.dto.SysDictionaryDTO;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.service.client.ProcessClient;
import com.jk.service.client.SysDictionaryClient;
import com.jk.service.utils.PlatformFileUtils;
import com.jk.workflow.constant.ProcessConstants;
import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 调解或审判信息	接口实现类
 *
 * @author wangshuai
 * @since 2024-07-03 10:13:14
 */
@RestController
@Slf4j
@Api(tags = "调解或审判信息	接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecoveryAdjustTrialServiceImpl implements RecoveryAdjustTrialService {

  private final RecoveryAdjustTrialHandler handler;
  private final RecoveryAdjustTrialMapper mapper;
  private final PlatformFileUtils platformFileUtils;
  private final ProcessClient processClient;
  private final SysDictionaryClient dictionaryClient;
  private final RecoveryLitigationHandler recoveryLitigationHandler;
  private final ProjectInfoHandler projectInfoHandler;
  private final RecoveryLitigationExtHandler recoveryLitigationExtHandler;

  @Override
  public Result<RecoveryAdjustTrialDTO> add(RecoveryAdjustTrialRequest request) {
    if (null == request || request.unverified()) {
      String message = "调解或审判信息	新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    RecoveryAdjustTrial recoveryAdjustTrial = PlatformMapUtils.getInstance().map(request, RecoveryAdjustTrial.class);
    handler.saveOrUpdate(recoveryAdjustTrial);

    //  通用附件新增
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),recoveryAdjustTrial.getId(), BillTypeEnum.RECOVERY_ADJUST_TRIAL.getKey());

    RecoveryAdjustTrialDTO dto = new RecoveryAdjustTrialDTO();
    dto.setId(recoveryAdjustTrial.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<RecoveryAdjustTrialDTO> update(RecoveryAdjustTrialRequest request) {
    if (null == request ) {
      String message = "调解或审判信息	修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    RecoveryAdjustTrial recoveryAdjustTrial = PlatformMapUtils.getInstance().map(request, RecoveryAdjustTrial.class);
    handler.saveOrUpdate(recoveryAdjustTrial);

    //  通用附件修改
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),recoveryAdjustTrial.getId(), BillTypeEnum.RECOVERY_ADJUST_TRIAL.getKey());

    RecoveryAdjustTrialDTO dto = new RecoveryAdjustTrialDTO();
    dto.setId(recoveryAdjustTrial.getId());
    return Result.success(dto);
  }

  @Override
  public Result<RecoveryAdjustTrialDTO> submit(RecoveryAdjustTrialRequest request) {
    request.setFlowState(ProcessStatus.running);
    ProjectInfo projectInfo = projectInfoHandler.getById(request.getProjectId());
    if (StringUtils.isBlank(projectInfo.getManage())) {
      return Result.error("提交失败 保全经理为空");
    }
    Result<RecoveryAdjustTrialDTO> updateResult = update(request);
    if (null == updateResult || !updateResult.succeedWithData()) {
      return Result.error("提交失败");
    }


    //  【调解审判】+项目名称+于+调解或审判日期+提交+调解或审判类型+申请，
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
    String flowTitle = "【调解审判】%s";


    HashMap<String, Object> variables = new HashMap<>(1);
    variables.put(PlatformConstant.DATA_OBJECT_ID, updateResult.getData().getId());
    variables.put("manage", projectInfo.getManage());
    variables.put(ProcessConstants.VARIABLE_OBJECT, String.format(flowTitle,projectInfo.getProjectName(),formatter.format(request.getAdjustTrialDate()),request.getAdjustTrialType().getValue()));
    processClient.startProcess(FlowBillEnum.RECOVERY_ADJUST_TRIAL.getKey(), variables);

    return Result.success();
  }

  @Override
  public Result<RecoveryAdjustTrialDTO> writeBackProject(Long id) {
    RecoveryAdjustTrial recoveryAdjustTrial = handler.getById(id);
    if (null == recoveryAdjustTrial) {
      String message = "调解或审判信息	单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    //  查询诉状信息
//    RecoveryLitigation recoveryLitigation = recoveryLitigationHandler.getById(recoveryAdjustTrial.getLitigationId());
//    if (null == recoveryLitigation) {
//      String message = "诉状	单个查找 该记录不存在";
//      log.info("{} litigationId {}", message, recoveryAdjustTrial.getLitigationId());
//      return Result.error(message);
//    }
    //  查询要修改的状态
    String code = AdjustTrialTypeEnum.BEFORE_LITIGATION_MEDIATION.getKey().equals(recoveryAdjustTrial.getAdjustTrialType().getKey())
        ? DictEnum.PROJECT_STATE_10.getKey() : AdjustTrialTypeEnum.MIDDLE_LITIGATION_MEDIATION.getKey().equals(recoveryAdjustTrial.getAdjustTrialType().getKey())
        ? DictEnum.PROJECT_STATE_11.getKey() : DictEnum.PROJECT_STATE_12.getKey();
    Long state = null;
    try {
      Result<SysDictionaryDTO> byCode = dictionaryClient.findByCode(DictEnum.PROJECT_STATE.getKey());
      if (byCode.getCode().equals(0)) {
        Optional<Long> first = byCode.getData().getItems()
            .stream()
            .filter(item -> code.equals(item.getItemCode()))
            .map(SysDictionaryItemDTO::getId)
            .findFirst();
        state = first.get();
      }
    }catch (Exception e){
      String message = "状态失败，请联系管理员";
      log.info("{} code {}", message, code);
      return Result.error(message);
    }


    //  修改诉讼状态
//    recoveryLitigationHandler.update(new LambdaUpdateWrapper<RecoveryLitigation>()
//        .set(RecoveryLitigation::getProjectStatus, state)
//        .eq(RecoveryLitigation::getId, recoveryLitigation.getId()));
    //  反写诉讼信息
    //  查询诉讼扩展表
    RecoveryLitigationExt recoveryLitigationExt = recoveryLitigationExtHandler.getById(recoveryAdjustTrial.getLitigationId());
    if (recoveryLitigationExt == null){
      recoveryLitigationExt = new RecoveryLitigationExt();
    }
    recoveryLitigationExt.setAdjustTrialDate(recoveryAdjustTrial.getAdjustTrialDate())
        .setAdjustCode(recoveryAdjustTrial.getAdjustCode())
            .setBackCompensationAmount(recoveryAdjustTrial.getCompensatoryAmount())
            .setBackInterest(recoveryAdjustTrial.getInterest())
            .setBackLiquidatedDamages(recoveryAdjustTrial.getBackOutAmount())
            .setBackOtherFees(recoveryAdjustTrial.getOtherAmount())
            .setId(recoveryAdjustTrial.getLitigationId());
    recoveryLitigationExtHandler.saveOrUpdate(recoveryLitigationExt);

    //  修改项目状态
    try {
      GregorianCalendar gc = new GregorianCalendar();
      gc.setTimeInMillis(recoveryAdjustTrial.getAdjustTrialDate().getTime());
      gc.add(Calendar.YEAR, 2);
      projectInfoHandler.update(new LambdaUpdateWrapper<ProjectInfo>()
          .set(ProjectInfo::getProjectState, state)
          .set(ProjectInfo::getAdjustTrialDate, gc.getTime())
          .eq(ProjectInfo::getId, recoveryAdjustTrial.getProjectId()));
    }catch (Exception e){
      log.error("反写项目信息失败  adjustTrialDate：{} projectId：{}", recoveryAdjustTrial.getAdjustTrialDate(), recoveryAdjustTrial.getProjectId());
    }

    return Result.success();
  }

  @Override
  public Result<RecoveryAdjustTrialDTO> findById(Long id) {
    RecoveryAdjustTrial recoveryAdjustTrial = handler.getById(id);
    if (null == recoveryAdjustTrial) {
      String message = "调解或审判信息	单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    RecoveryAdjustTrialDTO dto = PlatformMapUtils.getInstance().map(recoveryAdjustTrial, RecoveryAdjustTrialDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<Map<String, Object>> getExamineUser(Long id) {
    Map<String, Object> map = Maps.newHashMap();

    RecoveryAdjustTrial assetTransfer = handler.getById(id);

    ProjectInfo projectInfo = projectInfoHandler.getById(assetTransfer.getProjectId());

    if (!ObjectUtils.isEmpty(projectInfo)) {
      map.put("manage", projectInfo.getManage());
    }

    return Result.success(map);
  }

  @Override
  public Result<List<RecoveryAdjustTrialDTO>> findByLitigationId(Long litigationId) {

    List<RecoveryAdjustTrial> list = handler.list(new LambdaUpdateWrapper<RecoveryAdjustTrial>()
        .eq(RecoveryAdjustTrial::getLitigationId, litigationId));

    if (ObjectUtils.isEmpty(list)){
      Lists.newArrayList();
    }

    List<RecoveryAdjustTrialDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, RecoveryAdjustTrialDTO.class);

    return Result.success(dtoList);
  }

  @Override
  public Result<List<RecoveryAdjustTrialDTO>> findAll(Integer current, Integer size, RecoveryAdjustTrialPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<RecoveryAdjustTrial> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<RecoveryAdjustTrial> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<RecoveryAdjustTrialDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, RecoveryAdjustTrialDTO.class);
//    for (RecoveryAdjustTrialDTO dto : dtoList) {
//      Long id = dto.getId();
//      // 流程状态
//      Result<ProcessStatus> statusResult = processClient.getStatusByDoId(id);
//      if (null != statusResult && statusResult.succeedWithData()) {
//        dto.setProcessStatus(statusResult.getData());
//      }
//    }
    return Result.success(dtoList, PageFactory.page(page));
  }

}