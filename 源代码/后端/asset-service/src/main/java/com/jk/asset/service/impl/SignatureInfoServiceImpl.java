package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.mapper.SignatureInfoMapper;
import com.jk.asset.model.dto.SignatureInfoDTO;
import com.jk.asset.model.entity.SignatureInfo;
import com.jk.asset.model.request.SignatureInfoRequest;
import com.jk.asset.model.request.page.SignatureInfoPageRequest;
import com.jk.asset.service.SignatureInfoService;
import com.jk.asset.service.handler.SignatureInfoHandler;
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
 * 签署方信息接口实现类
 *
 * @author wangshuai
 * @since 2024-06-20 17:49:00
 */
@RestController
@Slf4j
@Api(tags = "签署方信息接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SignatureInfoServiceImpl implements SignatureInfoService {

  private final SignatureInfoHandler handler;
  private final SignatureInfoMapper mapper;

  @Override
  public Result<SignatureInfoDTO> add(SignatureInfoRequest request) {
    if (null == request || request.unverified()) {
      String message = "签署方信息新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    SignatureInfo signatureInfo = PlatformMapUtils.getInstance().map(request, SignatureInfo.class);
    handler.save(signatureInfo);

    SignatureInfoDTO dto = new SignatureInfoDTO();
    dto.setId(signatureInfo.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<SignatureInfoDTO> update(SignatureInfoRequest request) {
    if (null == request || null == request.getId()) {
      String message = "签署方信息修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    SignatureInfo signatureInfo = PlatformMapUtils.getInstance().map(request, SignatureInfo.class);
    handler.updateById(signatureInfo);

    return Result.success();
  }

  @Override
  public Result<SignatureInfoDTO> findById(Long id) {
    SignatureInfo signatureInfo = handler.getById(id);
    if (null == signatureInfo) {
      String message = "签署方信息单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    SignatureInfoDTO dto = PlatformMapUtils.getInstance().map(signatureInfo, SignatureInfoDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<SignatureInfoDTO>> findByContractId(Long contractId) {

    List<SignatureInfo> list = handler.list(new LambdaQueryWrapper<SignatureInfo>().eq(SignatureInfo::getContractId, contractId));

    if (ObjectUtils.isEmpty(list)) {
      return Result.success(new ArrayList<>());
    }
    List<SignatureInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, SignatureInfoDTO.class);
    return Result.success(dtoList);
  }

  @Override
  public Result<List<SignatureInfoDTO>> findAll(Integer current, Integer size, SignatureInfoPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<SignatureInfo> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<SignatureInfo> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<SignatureInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, SignatureInfoDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}