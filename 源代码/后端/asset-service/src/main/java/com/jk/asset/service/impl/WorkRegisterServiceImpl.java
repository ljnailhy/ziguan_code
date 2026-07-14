package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.asset.mapper.WorkRegisterMapper;
import com.jk.asset.model.dto.WorkRegisterDTO;
import com.jk.asset.model.entity.WorkRegister;
import com.jk.asset.model.request.WorkRegisterRequest;
import com.jk.asset.model.request.page.WorkRegisterPageRequest;
import com.jk.asset.service.WorkRegisterService;
import com.jk.asset.service.handler.WorkRegisterHandler;
import com.jk.service.utils.PlatformFileUtils;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工作登记接口实现类
 *
 * @author wangshuai
 * @since 2024-06-28 17:23:16
 */
@RestController
@Slf4j
@Api(tags = "工作登记接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkRegisterServiceImpl implements WorkRegisterService {

  private final WorkRegisterHandler handler;
  private final WorkRegisterMapper mapper;
  private final PlatformFileUtils platformFileUtils;

  @Override
  public Result<WorkRegisterDTO> add(WorkRegisterRequest request) {
    if (null == request || request.unverified()) {
      String message = "工作登记新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    WorkRegister workRegister = PlatformMapUtils.getInstance().map(request, WorkRegister.class);
    handler.save(workRegister);

    //  通用附件新增
    platformFileUtils.batchAddFile(request.getFileInfoList(),workRegister.getId(), BillTypeEnum.WORK_REGISTER.getKey());

    WorkRegisterDTO dto = new WorkRegisterDTO();
    dto.setId(workRegister.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<WorkRegisterDTO> update(WorkRegisterRequest request) {
    if (null == request || null == request.getId()) {
      String message = "工作登记修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    WorkRegister workRegister = PlatformMapUtils.getInstance().map(request, WorkRegister.class);
    handler.updateById(workRegister);

    //  通用附件修改
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),workRegister.getId(), BillTypeEnum.WORK_REGISTER.getKey());

    return Result.success();
  }

  @Override
  public Result<WorkRegisterDTO> findById(Long id) {
    WorkRegister workRegister = handler.getById(id);
    if (null == workRegister) {
      String message = "工作登记单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    WorkRegisterDTO dto = PlatformMapUtils.getInstance().map(workRegister, WorkRegisterDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<WorkRegisterDTO>> findAll(Integer current, Integer size, WorkRegisterPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<WorkRegister> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<WorkRegisterDTO> dtoList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(dtoList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
//    List<WorkRegisterDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, WorkRegisterDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}