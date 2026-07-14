package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.enums.FlowBillEnum;
import com.jk.asset.mapper.ProjectTransferMapper;
import com.jk.asset.model.dto.ProjectTransferDTO;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.entity.ProjectTransfer;
import com.jk.asset.model.entity.ProjectTransferDetailed;
import com.jk.asset.model.request.ProjectTransferRequest;
import com.jk.asset.model.request.page.ProjectTransferPageRequest;
import com.jk.asset.service.ProjectTransferService;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.ProjectTransferDetailedHandler;
import com.jk.asset.service.handler.ProjectTransferHandler;
import com.jk.asset.utils.limit.AssetUserLimitsUtils;
import com.jk.common.constant.PlatformConstant;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.service.client.ProcessClient;
import com.jk.service.utils.PlatformFileUtils;
import com.jk.workflow.constant.ProcessConstants;
import com.jk.workflow.enums.ProcessStatus;
import io.swagger.annotations.Api;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目移交接口实现类
 *
 * @author wangshuai
 * @since 2024-07-17 18:27:55
 */
@RestController
@Slf4j
@Api(tags = "项目移交接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectTransferServiceImpl implements ProjectTransferService {

  private final ProjectTransferHandler handler;
  private final ProjectTransferMapper mapper;
  private final ProjectTransferDetailedHandler projectTransferDetailedHandler;
  private final PlatformFileUtils platformFileUtils;
  private final ProcessClient processClient;
  private final ProjectInfoHandler projectInfoHandler;
  private final AssetUserLimitsUtils assetUserLimitsUtils;

  @Override
  public Result<ProjectTransferDTO> add(ProjectTransferRequest request) {
    if (null == request || request.unverified() || ObjectUtils.isEmpty(request.getProjectTransferDetailedRequestList())) {
      String message = "项目移交新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    ProjectTransfer projectTransfer = PlatformMapUtils.getInstance().map(request, ProjectTransfer.class);
    handler.save(projectTransfer);

    //  处理项目移交明细
    projectTransferDetailedHandler.batchAddOrUpdateProjectTransferDetailed(request.getProjectTransferDetailedRequestList(),projectTransfer.getId());

    //  通用附件新增
    platformFileUtils.batchAddFile(request.getFileInfoList(),projectTransfer.getId(), BillTypeEnum.PROJECT_TRANSFER.getKey());

    ProjectTransferDTO dto = new ProjectTransferDTO();
    dto.setId(projectTransfer.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<ProjectTransferDTO> update(ProjectTransferRequest request) {
    if (null == request || ObjectUtils.isEmpty(request.getProjectTransferDetailedRequestList())) {
      String message = "项目移交修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    ProjectTransfer projectTransfer = PlatformMapUtils.getInstance().map(request, ProjectTransfer.class);
    handler.saveOrUpdate(projectTransfer);

    //  处理项目移交明细
    projectTransferDetailedHandler.batchAddOrUpdateProjectTransferDetailed(request.getProjectTransferDetailedRequestList(),projectTransfer.getId());

    //  通用附件新增
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),projectTransfer.getId(), BillTypeEnum.PROJECT_TRANSFER.getKey());

    ProjectTransferDTO dto = new ProjectTransferDTO();
    dto.setId(projectTransfer.getId());
    return Result.success(dto);
  }

  @Override
  public Result<ProjectTransferDTO> findById(Long id) {
    ProjectTransfer projectTransfer = handler.getById(id);
    if (null == projectTransfer) {
      String message = "项目移交单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    ProjectTransferDTO dto = PlatformMapUtils.getInstance().map(projectTransfer, ProjectTransferDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<ProjectTransferDTO> submit(ProjectTransferRequest request) {
    request.setFlowState(ProcessStatus.running);
    Result<ProjectTransferDTO> updateResult = update(request);
    if (null == updateResult || !updateResult.succeedWithData()) {
      return Result.error("提交失败");
    }

    //  【项目移交】+标题名称
    String flowTitle = "【项目移交】%s";

    HashMap<String, Object> variables = new HashMap<>(1);
    variables.put(PlatformConstant.DATA_OBJECT_ID, updateResult.getData().getId());
    variables.put(ProcessConstants.VARIABLE_OBJECT,String.format(flowTitle,request.getTitle()));
    processClient.startProcess(FlowBillEnum.PROJECT_TRANSFER.getKey(), variables);

    return Result.success();
  }

  @Override
  public Result<ProjectTransferDTO> writeBackProject(Long id) {

    List<ProjectTransferDetailed> list = projectTransferDetailedHandler
        .list(new LambdaQueryWrapper<ProjectTransferDetailed>().eq(ProjectTransferDetailed::getTransferId, id));

    if (ObjectUtils.isEmpty(list)) {
      return Result.success();
    }
    for (ProjectTransferDetailed projectTransferDetailed : list) {

      //  修改项目归属方
      projectInfoHandler.update(new LambdaUpdateWrapper<ProjectInfo>()
          .set(ProjectInfo::getAffiliatedOrg, projectTransferDetailed.getAffiliatedOrg())
          .set(ProjectInfo::getIsTransfer, Boolean.TRUE)
          .set(ProjectInfo::getPrimaryAffiliatedOrg, projectTransferDetailed.getPrimaryAffiliatedOrg())
          .eq(ProjectInfo::getId, projectTransferDetailed.getProjectId()));
    }

    return Result.success();
  }

  @Override
  public Result<List<ProjectTransferDTO>> findAll(Integer current, Integer size, ProjectTransferPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<ProjectTransfer> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
    request.setOrgUserIds(userLimitsOrg);
    List<ProjectTransfer> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<ProjectTransferDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, ProjectTransferDTO.class);
//    for (ProjectTransferDTO dto : dtoList) {
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