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
import com.jk.asset.mapper.proceeding.RecoveryLitigationDetailsMapper;
import com.jk.asset.model.dto.proceeding.RecoveryLitigationDetailsDTO;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.entity.RevePropertyInfo;
import com.jk.asset.model.entity.proceeding.RecoveryLitigationDetails;
import com.jk.asset.model.entity.proceeding.RecoveryLitigationExt;
import com.jk.asset.model.request.page.proceeding.RecoveryLitigationDetailsPageRequest;
import com.jk.asset.model.request.proceeding.RecoveryLitigationDetailsRequest;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.RevePropertyInfoHandler;
import com.jk.asset.service.handler.proceeding.RecoveryLitigationDetailsHandler;
import com.jk.asset.service.handler.proceeding.RecoveryLitigationExtHandler;
import com.jk.asset.service.handler.proceeding.RecoveryLitigationHandler;
import com.jk.asset.service.proceeding.RecoveryLitigationDetailsService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息接口实现类
 *
 * @author wangshuai
 * @since 2024-07-02 15:44:58
 */
@RestController
@Slf4j
@Api(tags = "终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecoveryLitigationDetailsServiceImpl implements RecoveryLitigationDetailsService {

  private final RecoveryLitigationDetailsHandler handler;
  private final RecoveryLitigationDetailsMapper mapper;
  private final ProcessClient processClient;
  private final RevePropertyInfoHandler revePropertyInfoHandler;
  private final SysDictionaryClient dictionaryClient;
  private final RecoveryLitigationHandler recoveryLitigationHandler;
  private final ProjectInfoHandler projectInfoHandler;
  private final RecoveryLitigationExtHandler recoveryLitigationExtHandler;
  private final PlatformFileUtils platformFileUtils;

  @Override
  public Result<RecoveryLitigationDetailsDTO> add(RecoveryLitigationDetailsRequest request) {
    if (null == request || request.unverified()) {
      String message = "终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    RecoveryLitigationDetails recoveryLitigationDetails = handler.addOrUpdate(request);

    RecoveryLitigationDetailsDTO dto = new RecoveryLitigationDetailsDTO();
    dto.setId(recoveryLitigationDetails.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<RecoveryLitigationDetailsDTO> update(RecoveryLitigationDetailsRequest request) {
    if (null == request) {
      String message = "终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    RecoveryLitigationDetails recoveryLitigationDetails = handler.addOrUpdate(request);
    RecoveryLitigationDetailsDTO dto = new RecoveryLitigationDetailsDTO();
    dto.setId(recoveryLitigationDetails.getId());
    return Result.success(dto);
  }

  @Override
  public Result<RecoveryLitigationDetailsDTO> submit(RecoveryLitigationDetailsRequest request) {
    request.setFlowState(ProcessStatus.running);
    ProjectInfo projectInfo = projectInfoHandler.getById(request.getProjectId());
    if (StringUtils.isBlank(projectInfo.getManage())) {
      return Result.error("提交失败 保全经理为空");
    }
    Result<RecoveryLitigationDetailsDTO> updateResult = update(request);
    if (null == updateResult || !updateResult.succeedWithData()) {
      return Result.error("提交失败");
    }

    String flowTitle = getFlowTitle(request);


    HashMap<String, Object> variables = new HashMap<>(1);
    variables.put(PlatformConstant.DATA_OBJECT_ID, updateResult.getData().getId());
    variables.put("manage", projectInfo.getManage());
    variables.put(ProcessConstants.VARIABLE_OBJECT, flowTitle);
    processClient.startProcess(FlowBillEnum.PRESERVATION.getKey(), variables);

    return Result.success();
  }

  public String getFlowTitle(RecoveryLitigationDetailsRequest request){
    //  【撤诉】+项目名称+于+撤诉时间+提交撤诉申请，
    //  【保全】+标题名称，
    //  【终本】+标题名称
    //  【其他】+标题名称，
    //  【结案】+标题名称，

    //  前端处理了
//    if (BillTypeEnum.DROP_LAWSUIT.getKey().equals(request.getLitigationType().getKey())){
//      SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
//      ProjectInfo projectInfo = projectInfoHandler.getById(request.getProjectId());
//      String flowTitle = "【撤诉】%s于%s提交撤诉申请";
//
//      return String.format(flowTitle,projectInfo.getProjectName(),formatter.format(request.getDetailsDate()));
//    }
    return "【"+request.getLitigationType().getValue()+"】"+request.getTitle();
  }

  @Override
  public Result<RecoveryLitigationDetailsDTO> writeBackProject(Long id) {
    RecoveryLitigationDetails recoveryLitigationDetails = handler.getById(id);
    if (null == recoveryLitigationDetails) {
      String message = "终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }

    //  反写诉讼信息
    //  查询诉讼扩展表
    RecoveryLitigationExt recoveryLitigationExt = recoveryLitigationExtHandler.getById(recoveryLitigationDetails.getProjectId());
    if (recoveryLitigationExt == null){
      recoveryLitigationExt = new RecoveryLitigationExt();
    }
    recoveryLitigationExt.setId(recoveryLitigationDetails.getProjectId());

    //  保全信息
    if (BillTypeEnum.PRESERVATION.getKey().equals(recoveryLitigationDetails.getLitigationType().getKey())){

      //  查询担保信息
      List<RevePropertyInfo> list = revePropertyInfoHandler.list(new LambdaUpdateWrapper<RevePropertyInfo>()
          .eq(RevePropertyInfo::getDoType, BillTypeEnum.PRESERVATION.getKey())
          .eq(RevePropertyInfo::getDoId, recoveryLitigationDetails.getId()));

      for (RevePropertyInfo revePropertyInfo : list) {
        revePropertyInfo.setId(revePropertyInfo.getSourceId());
        revePropertyInfo.setDoType(BillTypeEnum.PROJECT_INFO.getKey());
        revePropertyInfo.setDoId(recoveryLitigationDetails.getProjectId());
      }
      revePropertyInfoHandler.saveOrUpdateBatch(list);
    }
    //  撤诉
    if (BillTypeEnum.DROP_LAWSUIT.getKey().equals(recoveryLitigationDetails.getLitigationType().getKey())){
      recoveryLitigationExt.setWithdrawLawsuitDate(recoveryLitigationDetails.getDetailsDate())
          .setWithdrawLawsuitDescription(recoveryLitigationDetails.getDetailsDescription());
      recoveryLitigationExtHandler.saveOrUpdate(recoveryLitigationExt);
    }
    //  终本
    if (BillTypeEnum.FINAL.getKey().equals(recoveryLitigationDetails.getLitigationType().getKey())){
      recoveryLitigationExt.setFinalVersionDate(recoveryLitigationDetails.getDetailsDate())
          .setFinalVersionDescription(recoveryLitigationDetails.getDetailsDescription());
      recoveryLitigationExtHandler.saveOrUpdate(recoveryLitigationExt);
    }
    //  结案
    if (BillTypeEnum.CLOSE_CASE.getKey().equals(recoveryLitigationDetails.getLitigationType().getKey())){
      recoveryLitigationExt.setCloseCaseDate(recoveryLitigationDetails.getDetailsDate())
          .setCloseCaseDescription(recoveryLitigationDetails.getDetailsDescription());
      recoveryLitigationExtHandler.saveOrUpdate(recoveryLitigationExt);
    }
    //  其他
    if (BillTypeEnum.OTHER.getKey().equals(recoveryLitigationDetails.getLitigationType().getKey())){
      recoveryLitigationExt.setOtherDate(recoveryLitigationDetails.getDetailsDate())
          .setOtherDescription(recoveryLitigationDetails.getDetailsDescription());
      recoveryLitigationExtHandler.saveOrUpdate(recoveryLitigationExt);
    }

    //  查询诉状信息
//    RecoveryLitigation recoveryLitigation = recoveryLitigationHandler.getById(recoveryLitigationDetails.getLitigationId());
//    if (null == recoveryLitigation) {
//      String message = "诉状	单个查找 该记录不存在";
//      log.info("{} litigationId {}", message, recoveryLitigationDetails.getLitigationId());
//      return Result.error(message);
//    }
    //  查询要修改的状态
    String code = BillTypeEnum.PRESERVATION.getKey().equals(recoveryLitigationDetails.getLitigationType().getKey())
        ? DictEnum.PROJECT_STATE_05.getKey() : BillTypeEnum.DROP_LAWSUIT.getKey().equals(recoveryLitigationDetails.getLitigationType().getKey())
        ? DictEnum.PROJECT_STATE_09.getKey() : BillTypeEnum.CLOSE_CASE.getKey().equals(recoveryLitigationDetails.getLitigationType().getKey())
        ? DictEnum.PROJECT_STATE_15.getKey() : BillTypeEnum.FINAL.getKey().equals(recoveryLitigationDetails.getLitigationType().getKey())
        ? DictEnum.PROJECT_STATE_14.getKey() : DictEnum.PROJECT_STATE_16.getKey();
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

    //  修改项目状态
    projectInfoHandler.update(new LambdaUpdateWrapper<ProjectInfo>()
        .set(ProjectInfo::getProjectState, state)
        .eq(ProjectInfo::getId, recoveryLitigationDetails.getProjectId()));

    return Result.success();
  }

  @Override
  public Result<Map<String, Object>> getExamineUser(Long id) {
    Map<String, Object> map = Maps.newHashMap();

    RecoveryLitigationDetails recoveryLitigationDetails = handler.getById(id);

    ProjectInfo projectInfo = projectInfoHandler.getById(recoveryLitigationDetails.getProjectId());

    if (!ObjectUtils.isEmpty(projectInfo)) {
      map.put("manage", projectInfo.getManage());
    }

    return Result.success(map);
  }

  @Override
  public Result<RecoveryLitigationDetailsDTO> findById(Long id) {
    RecoveryLitigationDetails recoveryLitigationDetails = handler.getById(id);
    if (null == recoveryLitigationDetails) {
      String message = "终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    RecoveryLitigationDetailsDTO dto = PlatformMapUtils.getInstance().map(recoveryLitigationDetails, RecoveryLitigationDetailsDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<RecoveryLitigationDetailsDTO>> findAll(Integer current, Integer size, RecoveryLitigationDetailsPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<RecoveryLitigationDetails> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<RecoveryLitigationDetails> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<RecoveryLitigationDetailsDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, RecoveryLitigationDetailsDTO.class);
//    for (RecoveryLitigationDetailsDTO dto : dtoList) {
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