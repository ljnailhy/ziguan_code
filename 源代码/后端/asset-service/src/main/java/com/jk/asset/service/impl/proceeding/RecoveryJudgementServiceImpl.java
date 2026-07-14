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
import com.jk.asset.enums.LitigationTypeEnum;
import com.jk.asset.mapper.proceeding.RecoveryJudgementMapper;
import com.jk.asset.model.dto.proceeding.RecoveryJudgementDTO;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.entity.proceeding.RecoveryExecute;
import com.jk.asset.model.entity.proceeding.RecoveryJudgement;
import com.jk.asset.model.entity.proceeding.RecoveryLitigationExt;
import com.jk.asset.model.request.page.proceeding.RecoveryJudgementPageRequest;
import com.jk.asset.model.request.proceeding.RecoveryJudgementRequest;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.proceeding.RecoveryJudgementHandler;
import com.jk.asset.service.handler.proceeding.RecoveryLitigationExtHandler;
import com.jk.asset.service.handler.proceeding.RecoveryLitigationHandler;
import com.jk.asset.service.proceeding.RecoveryJudgementService;
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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * 审判信息（立案一审二审再审）接口实现类
 *
 * @author wangshuai
 * @since 2024-07-02 09:56:48
 */
@RestController
@Slf4j
@Api(tags = "审判信息（立案一审二审再审）接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecoveryJudgementServiceImpl implements RecoveryJudgementService {

  private final RecoveryJudgementHandler handler;
  private final RecoveryJudgementMapper mapper;
  private final PlatformFileUtils platformFileUtils;
  private final ProcessClient processClient;
  private final SysDictionaryClient dictionaryClient;
  private final RecoveryLitigationHandler recoveryLitigationHandler;
  private final ProjectInfoHandler projectInfoHandler;
  private final RecoveryLitigationExtHandler recoveryLitigationExtHandler;

  @Override
  public Result<RecoveryJudgementDTO> add(RecoveryJudgementRequest request) {
    if (null == request || request.unverified()) {
      String message = "审判信息（立案一审二审再审）新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    RecoveryJudgement recoveryJudgement = PlatformMapUtils.getInstance().map(request, RecoveryJudgement.class);
    handler.saveOrUpdate(recoveryJudgement);

    //  通用附件新增
    platformFileUtils.batchAddFile(request.getFileInfoList(),recoveryJudgement.getId(), BillTypeEnum.RECOVERY_JUDGEMENT.getKey());

    RecoveryJudgementDTO dto = new RecoveryJudgementDTO();
    dto.setId(recoveryJudgement.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<RecoveryJudgementDTO> update(RecoveryJudgementRequest request) {
    if (null == request) {
      String message = "审判信息（立案一审二审再审）修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    RecoveryJudgement recoveryJudgement = PlatformMapUtils.getInstance().map(request, RecoveryJudgement.class);
    handler.saveOrUpdate(recoveryJudgement);

    //  通用附件新增
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),recoveryJudgement.getId(), BillTypeEnum.RECOVERY_JUDGEMENT.getKey());

    RecoveryJudgementDTO dto = new RecoveryJudgementDTO();
    dto.setId(recoveryJudgement.getId());
    return Result.success(dto);
  }

  @Override
  public Result<Map<String, Object>> getExamineUser(Long id) {
    Map<String, Object> map = Maps.newHashMap();

    RecoveryJudgement recoveryJudgement = handler.getById(id);

    ProjectInfo projectInfo = projectInfoHandler.getById(recoveryJudgement.getProjectId());

    if (!ObjectUtils.isEmpty(projectInfo)) {
      map.put("manage", projectInfo.getManage());
    }

    return Result.success(map);
  }

  @Override
  public Result<RecoveryJudgementDTO> submit(RecoveryJudgementRequest request) {
    request.setFlowState(ProcessStatus.running);
    ProjectInfo projectInfo = projectInfoHandler.getById(request.getProjectId());
    if (StringUtils.isBlank(projectInfo.getManage())) {
      return Result.error("提交失败 保全经理为空");
    }
    Result<RecoveryJudgementDTO> updateResult = update(request);
    if (null == updateResult || !updateResult.succeedWithData()) {
      return Result.error("提交失败");
    }

    String flowTitle = getFlowTitle(request);
    log.info("立案审理流程标题 {}",flowTitle);

    HashMap<String, Object> variables = new HashMap<>(1);
    variables.put(PlatformConstant.DATA_OBJECT_ID, updateResult.getData().getId());
    variables.put("manage", projectInfo.getManage());
    variables.put(ProcessConstants.VARIABLE_OBJECT, flowTitle);
    processClient.startProcess(FlowBillEnum.RECOVERY_JUDGEMENT.getKey(), variables);

    return Result.success();
  }

  public String getFlowTitle(RecoveryJudgementRequest request){
    //  【立案】+项目名称+于+立案时间+提交+立案类型+申请，
    //  【审理】+项目名称+于+开庭时间+立案法院+类型+开庭，
    String registerFlowTitle = "【立案】%s";
    String noRegisterFlowTitle = "【审理】%s";

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");

    ProjectInfo projectInfo = projectInfoHandler.getById(request.getProjectId());
    if (LitigationTypeEnum.REGISTER.getKey().equals(request.getLitigationType().getKey())){
      return String.format(registerFlowTitle,projectInfo.getProjectName(),formatter.format(request.getFillingDate()),request.getRegisterType().getValue());
    }
    return String.format(noRegisterFlowTitle,
        projectInfo.getProjectName(),
        formatter.format(request.getCourtSessionDate()),
        request.getFilingCourtName(),
        request.getLitigationType().getValue());
  }

  @Override
  public Result<RecoveryJudgementDTO> writeBackProject(Long id) {
    RecoveryJudgement recoveryJudgement = handler.getById(id);
    if (null == recoveryJudgement) {
      String message = "审判信息（立案一审二审再审）单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }

    //  查询诉状信息
//    RecoveryLitigation recoveryLitigation = recoveryLitigationHandler.getById(recoveryJudgement.getLitigationId());
//    if (null == recoveryLitigation) {
//      String message = "诉状	单个查找 该记录不存在";
//      log.info("{} litigationId {}", message, recoveryJudgement.getLitigationId());
//      return Result.error(message);
//    }
    //  查询要修改的状态
    String code = LitigationTypeEnum.REGISTER.getKey().equals(recoveryJudgement.getLitigationType().getKey())
        ? DictEnum.PROJECT_STATE_04.getKey() : LitigationTypeEnum.FIRST_INSTANCE.getKey().equals(recoveryJudgement.getLitigationType().getKey())
        ? DictEnum.PROJECT_STATE_06.getKey() : LitigationTypeEnum.SECOND_INSTANCE.getKey().equals(recoveryJudgement.getLitigationType().getKey())
        ? DictEnum.PROJECT_STATE_07.getKey() : DictEnum.PROJECT_STATE_08.getKey();
    Long state = null;
    try {
      Result<SysDictionaryDTO> byCode = dictionaryClient.findByCode(DictEnum.PROJECT_STATE.getKey());
      if (byCode.getCode().equals(0)) {
        Optional<Long> first = byCode.getData().getItems()
            .stream()
            .filter(item -> code.equals(item.getItemCode()))
            .map(SysDictionaryItemDTO::getId)
            .findFirst();
//        first.isPresent() 这里不用判断因为try了
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
    RecoveryLitigationExt recoveryLitigationExt = recoveryLitigationExtHandler.getById(recoveryJudgement.getProjectId());
    if (recoveryLitigationExt == null){
      recoveryLitigationExt = new RecoveryLitigationExt();
    }

    recoveryLitigationExt.setFilingCourtName(recoveryJudgement.getFilingCourtName())
        .setJudgeName(recoveryJudgement.getJudgeName())
        .setFillingDate(recoveryJudgement.getFillingDate())
        .setLitigationType(recoveryJudgement.getLitigationType())
        .setFillingCode(recoveryJudgement.getFillingCode())
        .setJudgeDate(recoveryJudgement.getJudgeDate())
        .setCourtSessionDate(recoveryJudgement.getCourtSessionDate())
        .setId(recoveryJudgement.getLitigationId());
    recoveryLitigationExtHandler.saveOrUpdate(recoveryLitigationExt);

    //  修改项目状态
    projectInfoHandler.update(new LambdaUpdateWrapper<ProjectInfo>()
        .set(ProjectInfo::getProjectState, state)
        .eq(ProjectInfo::getId, recoveryJudgement.getProjectId()));


    return Result.success();
  }

  @Override
  public Result<RecoveryJudgementDTO> findById(Long id) {
    RecoveryJudgement recoveryJudgement = handler.getById(id);
    if (null == recoveryJudgement) {
      String message = "审判信息（立案一审二审再审）单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    RecoveryJudgementDTO dto = PlatformMapUtils.getInstance().map(recoveryJudgement, RecoveryJudgementDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<RecoveryJudgementDTO>> findByLitigationId(Long litigationId) {
    List<RecoveryJudgement> list = handler.list(new LambdaUpdateWrapper<RecoveryJudgement>()
        .eq(RecoveryJudgement::getLitigationId, litigationId));

    if (ObjectUtils.isEmpty(list)){
      Lists.newArrayList();
    }

    List<RecoveryJudgementDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, RecoveryJudgementDTO.class);

    return Result.success(dtoList);
  }

  @Override
  public Result<List<RecoveryJudgementDTO>> findAll(Integer current, Integer size, RecoveryJudgementPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<RecoveryJudgement> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<RecoveryJudgement> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<RecoveryJudgementDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, RecoveryJudgementDTO.class);
//    for (RecoveryJudgementDTO dto : dtoList) {
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