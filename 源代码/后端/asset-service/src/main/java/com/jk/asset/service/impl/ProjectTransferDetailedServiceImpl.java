package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.asset.mapper.ProjectTransferDetailedMapper;
import com.jk.asset.model.dto.ProjectTransferDetailedDTO;
import com.jk.asset.model.entity.ProjectTransferDetailed;
import com.jk.asset.model.request.ProjectTransferDetailedRequest;
import com.jk.asset.model.request.page.ProjectTransferDetailedPageRequest;
import com.jk.asset.service.ProjectTransferDetailedService;
import com.jk.asset.service.handler.ProjectTransferDetailedHandler;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目移交明细接口实现类
 *
 * @author wangshuai
 * @since 2024-07-17 18:27:56
 */
@RestController
@Slf4j
@Api(tags = "接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectTransferDetailedServiceImpl implements ProjectTransferDetailedService {

  private final ProjectTransferDetailedHandler handler;
  private final ProjectTransferDetailedMapper mapper;

  @Override
  public Result<ProjectTransferDetailedDTO> add(ProjectTransferDetailedRequest request) {
    if (null == request || request.unverified()) {
      String message = "新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    ProjectTransferDetailed projectTransferDetailed = PlatformMapUtils.getInstance().map(request, ProjectTransferDetailed.class);
    handler.save(projectTransferDetailed);

    ProjectTransferDetailedDTO dto = new ProjectTransferDetailedDTO();
    dto.setId(projectTransferDetailed.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<ProjectTransferDetailedDTO> update(ProjectTransferDetailedRequest request) {
    if (null == request || null == request.getId()) {
      String message = "修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    ProjectTransferDetailed projectTransferDetailed = PlatformMapUtils.getInstance().map(request, ProjectTransferDetailed.class);
    handler.updateById(projectTransferDetailed);

    return Result.success();
  }

  @Override
  public Result<ProjectTransferDetailedDTO> findById(Long id) {
    ProjectTransferDetailed projectTransferDetailed = handler.getById(id);
    if (null == projectTransferDetailed) {
      String message = "单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    ProjectTransferDetailedDTO dto = PlatformMapUtils.getInstance().map(projectTransferDetailed, ProjectTransferDetailedDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<ProjectTransferDetailedDTO>> findByTransferId(Long transferId) {

    List<ProjectTransferDetailed> list = handler.list(new LambdaQueryWrapper<ProjectTransferDetailed>().eq(ProjectTransferDetailed::getTransferId, transferId));

    if (ObjectUtils.isEmpty(list)) {
      return Result.success(Lists.newArrayList());
    }
    List<ProjectTransferDetailedDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, ProjectTransferDetailedDTO.class);
    return Result.success(dtoList);
  }

  @Override
  public Result<List<ProjectTransferDetailedDTO>> findAll(Integer current, Integer size, ProjectTransferDetailedPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<ProjectTransferDetailed> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<ProjectTransferDetailed> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<ProjectTransferDetailedDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, ProjectTransferDetailedDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}