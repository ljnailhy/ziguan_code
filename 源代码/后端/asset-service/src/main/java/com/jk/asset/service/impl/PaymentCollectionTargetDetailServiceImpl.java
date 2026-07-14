package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.mapper.PaymentCollectionTargetDetailMapper;
import com.jk.asset.model.dto.LawyerInfoDTO;
import com.jk.asset.model.dto.PaymentCollectionTargetDetailDTO;
import com.jk.asset.model.entity.LawyerInfo;
import com.jk.asset.model.entity.PaymentCollectionTargetDetail;
import com.jk.asset.model.request.PaymentCollectionTargetDetailRequest;
import com.jk.asset.model.request.page.PaymentCollectionTargetDetailPageRequest;
import com.jk.asset.service.PaymentCollectionTargetDetailService;
import com.jk.asset.service.handler.PaymentCollectionTargetDetailHandler;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 回款目标明细接口实现类
 *
 * @author wangshuai
 * @since 2024-06-25 10:24:51
 */
@RestController
@Slf4j
@Api(tags = "回款目标明细接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentCollectionTargetDetailServiceImpl implements PaymentCollectionTargetDetailService {

  private final PaymentCollectionTargetDetailHandler handler;
  private final PaymentCollectionTargetDetailMapper mapper;

  @Override
  public Result<PaymentCollectionTargetDetailDTO> add(PaymentCollectionTargetDetailRequest request) {
    if (null == request || request.unverified()) {
      String message = "回款目标明细新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    PaymentCollectionTargetDetail paymentCollectionTargetDetail = PlatformMapUtils.getInstance().map(request, PaymentCollectionTargetDetail.class);
    handler.save(paymentCollectionTargetDetail);

    PaymentCollectionTargetDetailDTO dto = new PaymentCollectionTargetDetailDTO();
    dto.setId(paymentCollectionTargetDetail.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<PaymentCollectionTargetDetailDTO> update(PaymentCollectionTargetDetailRequest request) {
    if (null == request || null == request.getId()) {
      String message = "回款目标明细修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    PaymentCollectionTargetDetail paymentCollectionTargetDetail = PlatformMapUtils.getInstance().map(request, PaymentCollectionTargetDetail.class);
    handler.updateById(paymentCollectionTargetDetail);

    return Result.success();
  }

  @Override
  public Result<PaymentCollectionTargetDetailDTO> findById(Long id) {
    PaymentCollectionTargetDetail paymentCollectionTargetDetail = handler.getById(id);
    if (null == paymentCollectionTargetDetail) {
      String message = "回款目标明细单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    PaymentCollectionTargetDetailDTO dto = PlatformMapUtils.getInstance().map(paymentCollectionTargetDetail, PaymentCollectionTargetDetailDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<PaymentCollectionTargetDetailDTO>> findByTargetId(Long targetId) {

    List<PaymentCollectionTargetDetail> list = handler.list(new LambdaQueryWrapper<PaymentCollectionTargetDetail>()
        .eq(PaymentCollectionTargetDetail::getTargetId, targetId));

    if (ObjectUtils.isEmpty(list)) {
      return Result.success(new ArrayList<>());
    }

    List<PaymentCollectionTargetDetailDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, PaymentCollectionTargetDetailDTO.class);

    return Result.success(dtoList);
  }

  @Override
  public Result<List<PaymentCollectionTargetDetailDTO>> findAll(Integer current, Integer size, PaymentCollectionTargetDetailPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<PaymentCollectionTargetDetail> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<PaymentCollectionTargetDetail> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<PaymentCollectionTargetDetailDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, PaymentCollectionTargetDetailDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}