package com.jk.asset.service.impl.property;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.model.dto.property.DocumentIntermediaryDTO;
import com.jk.asset.model.entity.property.DocumentIntermediary;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.asset.mapper.property.IntermediaryCustomerLeadMapper;
import com.jk.asset.model.dto.property.IntermediaryCustomerLeadDTO;
import com.jk.asset.model.entity.property.IntermediaryCustomerLead;
import com.jk.asset.model.request.property.IntermediaryCustomerLeadRequest;
import com.jk.asset.model.request.page.property.IntermediaryCustomerLeadPageRequest;
import com.jk.asset.service.property.IntermediaryCustomerLeadService;
import com.jk.asset.service.handler.property.IntermediaryCustomerLeadHandler;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 中介客户线索关联表接口实现类
 *
 * @author wangshuai
 * @since 2024-07-12 16:12:05
 */
@RestController
@Slf4j
@Api(tags = "中介客户线索关联表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IntermediaryCustomerLeadServiceImpl implements IntermediaryCustomerLeadService {

  private final IntermediaryCustomerLeadHandler handler;
  private final IntermediaryCustomerLeadMapper mapper;

  @Override
  public Result<IntermediaryCustomerLeadDTO> add(IntermediaryCustomerLeadRequest request) {
    if (null == request || request.unverified()) {
      String message = "中介客户线索关联表新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    IntermediaryCustomerLead intermediaryCustomerLead = PlatformMapUtils.getInstance().map(request, IntermediaryCustomerLead.class);
    handler.save(intermediaryCustomerLead);

    IntermediaryCustomerLeadDTO dto = new IntermediaryCustomerLeadDTO();
    dto.setId(intermediaryCustomerLead.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<IntermediaryCustomerLeadDTO> update(IntermediaryCustomerLeadRequest request) {
    if (null == request || null == request.getId()) {
      String message = "中介客户线索关联表修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    IntermediaryCustomerLead intermediaryCustomerLead = PlatformMapUtils.getInstance().map(request, IntermediaryCustomerLead.class);
    handler.updateById(intermediaryCustomerLead);

    return Result.success();
  }

  @Override
  public Result<IntermediaryCustomerLeadDTO> findById(Long id) {
    IntermediaryCustomerLead intermediaryCustomerLead = handler.getById(id);
    if (null == intermediaryCustomerLead) {
      String message = "中介客户线索关联表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    IntermediaryCustomerLeadDTO dto = PlatformMapUtils.getInstance().map(intermediaryCustomerLead, IntermediaryCustomerLeadDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<IntermediaryCustomerLeadDTO>> findIntermediaryCustomerLeadByDoId(Long doId, String doType) {
    List<IntermediaryCustomerLead> list = handler.list(new LambdaUpdateWrapper<IntermediaryCustomerLead>()
        .eq(IntermediaryCustomerLead::getDoId, doId)
        .eq(IntermediaryCustomerLead::getDoType, doType));

    if (ObjectUtils.isEmpty(list)){
      return Result.success(Lists.newArrayList());
    }

    List<IntermediaryCustomerLeadDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, IntermediaryCustomerLeadDTO.class);

    return Result.success(dtoList);
  }

  @Override
  public Result<List<IntermediaryCustomerLeadDTO>> findAll(Integer current, Integer size, IntermediaryCustomerLeadPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<IntermediaryCustomerLead> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<IntermediaryCustomerLead> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<IntermediaryCustomerLeadDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, IntermediaryCustomerLeadDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}