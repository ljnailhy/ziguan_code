package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.mapper.RecoveryPaymentDetailMapper;
import com.jk.asset.model.dto.RecoveryPaymentDetailDTO;
import com.jk.asset.model.entity.RecoveryPaymentDetail;
import com.jk.asset.model.request.RecoveryPaymentDetailRequest;
import com.jk.asset.model.request.page.RecoveryPaymentDetailPageRequest;
import com.jk.asset.service.RecoveryPaymentDetailService;
import com.jk.asset.service.handler.RecoveryPaymentDetailHandler;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 付款明细接口实现类
 *
 * @author wangtao
 * @since 2024-07-08 14:23:29
 */
@RestController
@Slf4j
@Api(tags = "付款明细接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecoveryPaymentDetailServiceImpl implements RecoveryPaymentDetailService {

  private final RecoveryPaymentDetailHandler handler;
  private final RecoveryPaymentDetailMapper mapper;

  @Override
  public Result<RecoveryPaymentDetailDTO> add(RecoveryPaymentDetailRequest request) {
    if (null == request || request.unverified()) {
      String message = "付款明细新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    RecoveryPaymentDetail recoveryPaymentDetail = PlatformMapUtils.getInstance().map(request, RecoveryPaymentDetail.class);
    handler.save(recoveryPaymentDetail);

    RecoveryPaymentDetailDTO dto = new RecoveryPaymentDetailDTO();
    dto.setId(recoveryPaymentDetail.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<RecoveryPaymentDetailDTO> update(RecoveryPaymentDetailRequest request) {
    if (null == request || null == request.getId()) {
      String message = "付款明细修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    RecoveryPaymentDetail recoveryPaymentDetail = PlatformMapUtils.getInstance().map(request, RecoveryPaymentDetail.class);
    handler.updateById(recoveryPaymentDetail);

    return Result.success();
  }

  @Override
  public Result<RecoveryPaymentDetailDTO> findById(Long id) {
    RecoveryPaymentDetail recoveryPaymentDetail = handler.getById(id);
    if (null == recoveryPaymentDetail) {
      String message = "付款明细单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    RecoveryPaymentDetailDTO dto = PlatformMapUtils.getInstance().map(recoveryPaymentDetail, RecoveryPaymentDetailDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<RecoveryPaymentDetailDTO>> findAll(Integer current, Integer size, RecoveryPaymentDetailPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<RecoveryPaymentDetail> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<RecoveryPaymentDetail> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<RecoveryPaymentDetailDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, RecoveryPaymentDetailDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}