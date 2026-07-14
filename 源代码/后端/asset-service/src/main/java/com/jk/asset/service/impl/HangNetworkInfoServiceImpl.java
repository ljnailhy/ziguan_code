package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.model.dto.LawyerInfoDTO;
import com.jk.asset.model.entity.LawyerInfo;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.asset.mapper.HangNetworkInfoMapper;
import com.jk.asset.model.dto.HangNetworkInfoDTO;
import com.jk.asset.model.entity.HangNetworkInfo;
import com.jk.asset.model.request.HangNetworkInfoRequest;
import com.jk.asset.model.request.page.HangNetworkInfoPageRequest;
import com.jk.asset.service.HangNetworkInfoService;
import com.jk.asset.service.handler.HangNetworkInfoHandler;
import io.swagger.annotations.Api;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * 挂网信息表接口实现类
 *
 * @author wangshuai
 * @since 2024-07-09 15:15:21
 */
@RestController
@Slf4j
@Api(tags = "挂网信息表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HangNetworkInfoServiceImpl implements HangNetworkInfoService {

  private final HangNetworkInfoHandler handler;
  private final HangNetworkInfoMapper mapper;

  @Override
  public Result<HangNetworkInfoDTO> add(HangNetworkInfoRequest request) {
    if (null == request || request.unverified()) {
      String message = "挂网信息表新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    HangNetworkInfo hangNetworkInfo = PlatformMapUtils.getInstance().map(request, HangNetworkInfo.class);
    handler.save(hangNetworkInfo);

    HangNetworkInfoDTO dto = new HangNetworkInfoDTO();
    dto.setId(hangNetworkInfo.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<HangNetworkInfoDTO> update(HangNetworkInfoRequest request) {
    if (null == request || null == request.getId()) {
      String message = "挂网信息表修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    HangNetworkInfo hangNetworkInfo = PlatformMapUtils.getInstance().map(request, HangNetworkInfo.class);
    handler.updateById(hangNetworkInfo);

    return Result.success();
  }

  @Override
  public Result<HangNetworkInfoDTO> findById(Long id) {
    HangNetworkInfo hangNetworkInfo = handler.getById(id);
    if (null == hangNetworkInfo) {
      String message = "挂网信息表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    HangNetworkInfoDTO dto = PlatformMapUtils.getInstance().map(hangNetworkInfo, HangNetworkInfoDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<HangNetworkInfoDTO>> findByDoId(Long doId, String doType) {
    List<HangNetworkInfo> list = handler.list(new LambdaQueryWrapper<HangNetworkInfo>()
        .eq(HangNetworkInfo::getDoId, doId)
        .eq(HangNetworkInfo::getDoType,doType));

    if (ObjectUtils.isEmpty(list)) {
      return Result.success(new ArrayList<>());
    }

    List<HangNetworkInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, HangNetworkInfoDTO.class);
    return Result.success(dtoList);
  }

  @Override
  public Result<List<HangNetworkInfoDTO>> findAll(Integer current, Integer size, HangNetworkInfoPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<HangNetworkInfo> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<HangNetworkInfo> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<HangNetworkInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, HangNetworkInfoDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}