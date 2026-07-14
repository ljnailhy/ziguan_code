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
import com.jk.asset.mapper.proceeding.RecoveryExecuteMapper;
import com.jk.asset.model.dto.proceeding.RecoveryExecuteDTO;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.entity.RevePropertyInfo;
import com.jk.asset.model.entity.proceeding.RecoveryExecute;
import com.jk.asset.model.entity.proceeding.RecoveryLitigationExt;
import com.jk.asset.model.request.page.proceeding.RecoveryExecutePageRequest;
import com.jk.asset.model.request.proceeding.RecoveryExecuteRequest;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.RevePropertyInfoHandler;
import com.jk.asset.service.handler.proceeding.RecoveryExecuteHandler;
import com.jk.asset.service.handler.proceeding.RecoveryLitigationExtHandler;
import com.jk.asset.service.proceeding.RecoveryExecuteService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 执行信息	接口实现类
 *
 * @author wangshuai
 * @since 2024-07-05 09:52:54
 */
@RestController
@Slf4j
@Api(tags = "执行信息	接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecoveryExecuteServiceImpl implements RecoveryExecuteService {

  private final RecoveryExecuteHandler handler;
  private final RecoveryExecuteMapper mapper;
  private final PlatformFileUtils platformFileUtils;
  private final RevePropertyInfoHandler revePropertyInfoHandler;
  private final ProcessClient processClient;
  private final RecoveryLitigationExtHandler recoveryLitigationExtHandler;
  private final SysDictionaryClient dictionaryClient;
  private final ProjectInfoHandler projectInfoHandler;

  @Override
  public Result<RecoveryExecuteDTO> add(RecoveryExecuteRequest request) {
    if (null == request || request.unverified()) {
      String message = "执行信息	新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    RecoveryExecute recoveryExecute = PlatformMapUtils.getInstance().map(request, RecoveryExecute.class);
    handler.saveOrUpdate(recoveryExecute);

    //  处理反担保信息
    revePropertyInfoHandler.batchAddOrUpdateAllocationDetail(
        request.getReveInfoRequest(),
        BillTypeEnum.REVE.getKey(),
        recoveryExecute.getId(),
        BillTypeEnum.RECOVERY_EXECUTE.getKey()
    );
    //  处理其他财产信息
    revePropertyInfoHandler.batchAddOrUpdateAllocationDetail(
        request.getPropertyInfoRequest(),
        BillTypeEnum.PROPERTY.getKey(),
        recoveryExecute.getId(),
        BillTypeEnum.RECOVERY_EXECUTE.getKey()
    );

    //  通用附件新增
    platformFileUtils.batchAddFile(request.getFileInfoList(),recoveryExecute.getId(), BillTypeEnum.RECOVERY_EXECUTE.getKey());

    RecoveryExecuteDTO dto = new RecoveryExecuteDTO();
    dto.setId(recoveryExecute.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<RecoveryExecuteDTO> update(RecoveryExecuteRequest request) {
    if (null == request) {
      String message = "执行信息	修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    RecoveryExecute recoveryExecute = PlatformMapUtils.getInstance().map(request, RecoveryExecute.class);
    handler.saveOrUpdate(recoveryExecute);

    //  处理反担保信息
    revePropertyInfoHandler.batchAddOrUpdateAllocationDetail(
        request.getReveInfoRequest(),
        BillTypeEnum.REVE.getKey(),
        recoveryExecute.getId(),
        BillTypeEnum.RECOVERY_EXECUTE.getKey()
    );
    //  处理其他财产信息
    revePropertyInfoHandler.batchAddOrUpdateAllocationDetail(
        request.getPropertyInfoRequest(),
        BillTypeEnum.PROPERTY.getKey(),
        recoveryExecute.getId(),
        BillTypeEnum.RECOVERY_EXECUTE.getKey()
    );

    //  通用附件新增
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),recoveryExecute.getId(), BillTypeEnum.RECOVERY_EXECUTE.getKey());

    RecoveryExecuteDTO dto = new RecoveryExecuteDTO();
    dto.setId(recoveryExecute.getId());
    return Result.success(dto);
  }

  @Override
  public Result<RecoveryExecuteDTO> submit(RecoveryExecuteRequest request) {
    request.setFlowState(ProcessStatus.running);
    ProjectInfo projectInfo = projectInfoHandler.getById(request.getProjectId());
    if (StringUtils.isBlank(projectInfo.getManage())) {
      return Result.error("提交失败 保全经理为空");
    }
    Result<RecoveryExecuteDTO> updateResult = update(request);
    if (null == updateResult || !updateResult.succeedWithData()) {
      return Result.error("提交失败");
    }



    //  【执行】+项目名称+于+申请执行时间+提交执行申请，
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
    String flowTitle = "【执行】%s";

    HashMap<String, Object> variables = new HashMap<>(1);
    variables.put(PlatformConstant.DATA_OBJECT_ID, updateResult.getData().getId());
    variables.put("manage", projectInfo.getManage());
    variables.put(ProcessConstants.VARIABLE_OBJECT, String.format(flowTitle,projectInfo.getProjectName(),formatter.format(request.getApplyExecuteDate())));
    processClient.startProcess(FlowBillEnum.RECOVERY_EXECUTE.getKey(), variables);

    return Result.success();
  }

  @Override
  public Result<Map<String, Object>> getExamineUser(Long id) {
    Map<String, Object> map = Maps.newHashMap();

    RecoveryExecute recoveryExecute = handler.getById(id);

    ProjectInfo projectInfo = projectInfoHandler.getById(recoveryExecute.getProjectId());

    if (!ObjectUtils.isEmpty(projectInfo)) {
      map.put("manage", projectInfo.getManage());
    }

    return Result.success(map);
  }

  @Override
  public Result<RecoveryExecuteDTO> writeBackProject(Long id) {
    RecoveryExecute recoveryExecute = handler.getById(id);
    if (null == recoveryExecute) {
      String message = "执行信息	单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }

    //  反写诉讼信息
    //  查询诉讼扩展表
    RecoveryLitigationExt recoveryLitigationExt = recoveryLitigationExtHandler.getById(recoveryExecute.getProjectId());
    if (recoveryLitigationExt == null){
      recoveryLitigationExt = new RecoveryLitigationExt();
    }
    recoveryLitigationExt.setId(recoveryExecute.getProjectId());

    recoveryLitigationExt.setExecuteType(recoveryExecute.getExecuteType())
            .setExecuteCode(recoveryExecute.getExecuteCode())
            .setExecuteCourt(recoveryExecute.getExecuteCourt())
            .setApplyExecuteDate(recoveryExecute.getApplyExecuteDate())
            .setExecuter(recoveryExecute.getExecuter())
            .setExecuterTelphone(recoveryExecute.getExecuterTelphone())
            .setExecuteRulingIssuanceTime(recoveryExecute.getExecuteRulingIssuanceTime());

    recoveryLitigationExtHandler.saveOrUpdate(recoveryLitigationExt);

    //  查询担保信息
    List<RevePropertyInfo> list = revePropertyInfoHandler.list(new LambdaUpdateWrapper<RevePropertyInfo>()
        .eq(RevePropertyInfo::getDoType, BillTypeEnum.RECOVERY_EXECUTE.getKey())
        .eq(RevePropertyInfo::getDoId, recoveryExecute.getId()));

    for (RevePropertyInfo revePropertyInfo : list) {
      revePropertyInfo.setId(revePropertyInfo.getSourceId());
      revePropertyInfo.setDoType(BillTypeEnum.PROJECT_INFO.getKey());
      revePropertyInfo.setDoId(recoveryExecute.getProjectId());
    }
    revePropertyInfoHandler.saveOrUpdateBatch(list);

    Long state = null;
    try {
      Result<SysDictionaryDTO> byCode = dictionaryClient.findByCode(DictEnum.PROJECT_STATE.getKey());
      if (byCode.getCode().equals(0)) {
        Optional<Long> first = byCode.getData().getItems()
            .stream()
            .filter(item -> DictEnum.PROJECT_STATE_13.getKey().equals(item.getItemCode()))
            .map(SysDictionaryItemDTO::getId)
            .findFirst();
        state = first.get();
      }
    }catch (Exception e){
      String message = "状态失败，请联系管理员";
      log.info("{} code {}", message, DictEnum.PROJECT_STATE_13.getKey());
      return Result.error(message);
    }
    //  修改项目状态
    projectInfoHandler.update(new LambdaUpdateWrapper<ProjectInfo>()
        .set(ProjectInfo::getProjectState, state)
        .eq(ProjectInfo::getId, recoveryExecute.getProjectId()));

    return Result.success();
  }

  @Override
  public Result<RecoveryExecuteDTO> findById(Long id) {
    RecoveryExecute recoveryExecute = handler.getById(id);
    if (null == recoveryExecute) {
      String message = "执行信息	单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    RecoveryExecuteDTO dto = PlatformMapUtils.getInstance().map(recoveryExecute, RecoveryExecuteDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<RecoveryExecuteDTO>> findAll(Integer current, Integer size, RecoveryExecutePageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<RecoveryExecute> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<RecoveryExecute> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<RecoveryExecuteDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, RecoveryExecuteDTO.class);
//    for (RecoveryExecuteDTO dto : dtoList) {
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