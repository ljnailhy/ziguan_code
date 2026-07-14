package com.jk.asset.service.impl.property;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.model.dto.property.IntermediaryCustomerLeadDTO;
import com.jk.asset.model.entity.property.IntermediaryCustomerLead;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.asset.mapper.property.LeasePaymentCycleMapper;
import com.jk.asset.model.dto.property.LeasePaymentCycleDTO;
import com.jk.asset.model.entity.property.LeasePaymentCycle;
import com.jk.asset.model.request.property.LeasePaymentCycleRequest;
import com.jk.asset.model.request.page.property.LeasePaymentCyclePageRequest;
import com.jk.asset.service.property.LeasePaymentCycleService;
import com.jk.asset.service.handler.property.LeasePaymentCycleHandler;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 租赁缴费周期表接口实现类
 *
 * @author wangshuai
 * @since 2024-07-13 09:46:33
 */
@RestController
@Slf4j
@Api(tags = "租赁缴费周期表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LeasePaymentCycleServiceImpl implements LeasePaymentCycleService {

  private final LeasePaymentCycleHandler handler;
  private final LeasePaymentCycleMapper mapper;

  @Override
  public Result<LeasePaymentCycleDTO> add(LeasePaymentCycleRequest request) {
    if (null == request || request.unverified()) {
      String message = "租赁缴费周期表新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    LeasePaymentCycle leasePaymentCycle = PlatformMapUtils.getInstance().map(request, LeasePaymentCycle.class);
    handler.save(leasePaymentCycle);

    LeasePaymentCycleDTO dto = new LeasePaymentCycleDTO();
    dto.setId(leasePaymentCycle.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<LeasePaymentCycleDTO> update(LeasePaymentCycleRequest request) {
    if (null == request || null == request.getId()) {
      String message = "租赁缴费周期表修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    LeasePaymentCycle leasePaymentCycle = PlatformMapUtils.getInstance().map(request, LeasePaymentCycle.class);
    handler.updateById(leasePaymentCycle);

    return Result.success();
  }

  @Override
  public Result<LeasePaymentCycleDTO> findById(Long id) {
    LeasePaymentCycle leasePaymentCycle = handler.getById(id);
    if (null == leasePaymentCycle) {
      String message = "租赁缴费周期表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    LeasePaymentCycleDTO dto = PlatformMapUtils.getInstance().map(leasePaymentCycle, LeasePaymentCycleDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<LeasePaymentCycleDTO>> findLeasePaymentCycleByDoId(Long doId, String doType) {
    List<LeasePaymentCycle> list = handler.list(new LambdaUpdateWrapper<LeasePaymentCycle>()
        .eq(LeasePaymentCycle::getDoId, doId)
        .eq(LeasePaymentCycle::getDoType, doType));

    if (ObjectUtils.isEmpty(list)){
      return Result.success(Lists.newArrayList());
    }

    List<LeasePaymentCycleDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, LeasePaymentCycleDTO.class);

    return Result.success(dtoList);
  }

  @Override
  public Result<List<LeasePaymentCycleDTO>> findAll(Integer current, Integer size, LeasePaymentCyclePageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<LeasePaymentCycle> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<LeasePaymentCycle> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<LeasePaymentCycleDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, LeasePaymentCycleDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}