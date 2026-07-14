package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.mapper.PaymentCollectionTargetMapper;
import com.jk.asset.model.dto.PaymentCollectionTargetDTO;
import com.jk.asset.model.entity.PaymentCollectionTarget;
import com.jk.asset.model.entity.PaymentCollectionTargetDetail;
import com.jk.asset.model.request.PaymentCollectionTargetRequest;
import com.jk.asset.model.request.page.PaymentCollectionTargetPageRequest;
import com.jk.asset.service.PaymentCollectionTargetService;
import com.jk.asset.service.handler.PaymentCollectionTargetDetailHandler;
import com.jk.asset.service.handler.PaymentCollectionTargetHandler;
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

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 年度回款目标接口实现类
 *
 * @author wangshuai
 * @since 2024-06-25 10:24:50
 */
@RestController
@Slf4j
@Api(tags = "年度回款目标接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentCollectionTargetServiceImpl implements PaymentCollectionTargetService {

  private final PaymentCollectionTargetHandler handler;
  private final PaymentCollectionTargetMapper mapper;
  private final PaymentCollectionTargetDetailHandler paymentCollectionTargetDetailHandler;

  @Override
  public Result<PaymentCollectionTargetDTO> add(PaymentCollectionTargetRequest request) {
    if (null == request || request.unverified()) {
      String message = "年度回款目标新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    LambdaQueryWrapper<PaymentCollectionTarget> wrapper = Wrappers.lambdaQuery();
    wrapper.apply("YEAR(year) = {0}", sdf.format(request.getYear()));
    List<PaymentCollectionTarget> list = handler.list(wrapper);

    if (!ObjectUtils.isEmpty(list)){
      String message = "该年度回款目标已存在 请检查！";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    PaymentCollectionTarget paymentCollectionTarget = PlatformMapUtils.getInstance().map(request, PaymentCollectionTarget.class);
    handler.save(paymentCollectionTarget);

    //  批量保存年度回款明细
    paymentCollectionTargetDetailHandler.batchAddPaymentCollectionTargetDetail(request.getPaymentCollectionTargetDetailRequests(),paymentCollectionTarget.getId());

    PaymentCollectionTargetDTO dto = new PaymentCollectionTargetDTO();
    dto.setId(paymentCollectionTarget.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    paymentCollectionTargetDetailHandler.remove(new LambdaQueryWrapper<PaymentCollectionTargetDetail>()
        .eq(PaymentCollectionTargetDetail::getTargetId,id));
    return Result.success();
  }

  @Override
  public Result<PaymentCollectionTargetDTO> update(PaymentCollectionTargetRequest request) {
    if (null == request || null == request.getId()) {
      String message = "年度回款目标修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    LambdaQueryWrapper<PaymentCollectionTarget> wrapper = Wrappers.lambdaQuery();
    wrapper.apply("YEAR(year) = {0}", sdf.format(request.getYear()));
    List<PaymentCollectionTarget> list = handler.list(wrapper);

    if (!ObjectUtils.isEmpty(list) && !list.get(0).getId().equals(request.getId())){
      String message = "该年度回款目标已存在 请检查！";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    PaymentCollectionTarget paymentCollectionTarget = PlatformMapUtils.getInstance().map(request, PaymentCollectionTarget.class);
    handler.updateById(paymentCollectionTarget);
    //  批量保存年度回款明细
    paymentCollectionTargetDetailHandler.batchAddPaymentCollectionTargetDetail(request.getPaymentCollectionTargetDetailRequests(),paymentCollectionTarget.getId());
    return Result.success();
  }

  @Override
  public Result<PaymentCollectionTargetDTO> findById(Long id) {
    PaymentCollectionTarget paymentCollectionTarget = handler.getById(id);
    if (null == paymentCollectionTarget) {
      String message = "年度回款目标单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    PaymentCollectionTargetDTO dto = PlatformMapUtils.getInstance().map(paymentCollectionTarget, PaymentCollectionTargetDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<PaymentCollectionTargetDTO>> findAll(Integer current, Integer size, PaymentCollectionTargetPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<PaymentCollectionTarget> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<PaymentCollectionTarget> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<PaymentCollectionTargetDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, PaymentCollectionTargetDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}