package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.asset.mapper.RecoveryPaymentCollectionDetailMapper;
import com.jk.asset.model.dto.RecoveryPaymentCollectionDetailDTO;
import com.jk.asset.model.entity.RecoveryPaymentCollectionDetail;
import com.jk.asset.model.request.RecoveryPaymentCollectionDetailRequest;
import com.jk.asset.model.request.page.RecoveryPaymentCollectionDetailPageRequest;
import com.jk.asset.service.RecoveryPaymentCollectionDetailService;
import com.jk.asset.service.handler.RecoveryPaymentCollectionDetailHandler;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 回款明细接口实现类
 *
 * @author wangtao
 * @since 2024-06-20 18:53:02
 */
@RestController
@Slf4j
@Api(tags = "回款明细接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecoveryPaymentCollectionDetailServiceImpl implements RecoveryPaymentCollectionDetailService {

  private final RecoveryPaymentCollectionDetailHandler handler;
  private final RecoveryPaymentCollectionDetailMapper mapper;

  @Override
  public Result<RecoveryPaymentCollectionDetailDTO> add(RecoveryPaymentCollectionDetailRequest request) {
    if (null == request || request.unverified()) {
      String message = "回款明细新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    RecoveryPaymentCollectionDetail recoveryPaymentCollectionDetail = PlatformMapUtils.getInstance().map(request, RecoveryPaymentCollectionDetail.class);
    handler.save(recoveryPaymentCollectionDetail);

    RecoveryPaymentCollectionDetailDTO dto = new RecoveryPaymentCollectionDetailDTO();
    dto.setId(recoveryPaymentCollectionDetail.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<RecoveryPaymentCollectionDetailDTO> update(RecoveryPaymentCollectionDetailRequest request) {
    if (null == request || null == request.getId()) {
      String message = "回款明细修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    RecoveryPaymentCollectionDetail recoveryPaymentCollectionDetail = PlatformMapUtils.getInstance().map(request, RecoveryPaymentCollectionDetail.class);
    handler.updateById(recoveryPaymentCollectionDetail);

    return Result.success();
  }

  @Override
  public Result<RecoveryPaymentCollectionDetailDTO> findById(Long id) {
    RecoveryPaymentCollectionDetail recoveryPaymentCollectionDetail = handler.getById(id);
    if (null == recoveryPaymentCollectionDetail) {
      String message = "回款明细单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    RecoveryPaymentCollectionDetailDTO dto = PlatformMapUtils.getInstance().map(recoveryPaymentCollectionDetail, RecoveryPaymentCollectionDetailDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<RecoveryPaymentCollectionDetailDTO>> findAll(Integer current, Integer size, RecoveryPaymentCollectionDetailPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<RecoveryPaymentCollectionDetail> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<RecoveryPaymentCollectionDetail> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<RecoveryPaymentCollectionDetailDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, RecoveryPaymentCollectionDetailDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}