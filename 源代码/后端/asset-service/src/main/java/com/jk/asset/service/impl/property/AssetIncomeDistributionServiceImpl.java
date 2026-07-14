package com.jk.asset.service.impl.property;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.mapper.property.AssetIncomeDistributionMapper;
import com.jk.asset.model.dto.property.AssetIncomeDistributionDTO;
import com.jk.asset.model.entity.property.AssetIncomeDistribution;
import com.jk.asset.model.entity.property.PropertyRightInfo;
import com.jk.asset.model.request.page.property.AssetIncomeDistributionPageRequest;
import com.jk.asset.model.request.property.AssetIncomeDistributionRequest;
import com.jk.asset.service.handler.property.AssetIncomeDistributionHandler;
import com.jk.asset.service.handler.property.PropertyRightInfoHandler;
import com.jk.asset.service.property.AssetIncomeDistributionService;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 资产收入分配接口实现类
 *
 * @author wangshuai
 * @since 2024-07-13 09:48:45
 */
@RestController
@Slf4j
@Api(tags = "资产收入分配接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AssetIncomeDistributionServiceImpl implements AssetIncomeDistributionService {

  private final AssetIncomeDistributionHandler handler;
  private final AssetIncomeDistributionMapper mapper;
  private final PropertyRightInfoHandler propertyRightInfoHandler;
  @Override
  public Result<AssetIncomeDistributionDTO> add(AssetIncomeDistributionRequest request) {
    if (null == request || request.unverified()) {
      String message = "资产收入分配新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    AssetIncomeDistribution assetIncomeDistribution = PlatformMapUtils.getInstance().map(request, AssetIncomeDistribution.class);
    handler.save(assetIncomeDistribution);

    AssetIncomeDistributionDTO dto = new AssetIncomeDistributionDTO();
    dto.setId(assetIncomeDistribution.getId());
    return Result.success(dto);
  }

  @Override
  public Result<List<AssetIncomeDistributionDTO>> findLeasePaymentCycleByDoId(Long doId, String doType) {
    List<AssetIncomeDistribution> list = handler.list(new LambdaUpdateWrapper<AssetIncomeDistribution>()
        .eq(AssetIncomeDistribution::getDoId, doId)
        .eq(AssetIncomeDistribution::getDoType, doType));

    if (ObjectUtils.isEmpty(list)){
      return Result.success(Lists.newArrayList());
    }

    List<AssetIncomeDistributionDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, AssetIncomeDistributionDTO.class);
    List<Long> rightInfoList = dtoList.stream().map(AssetIncomeDistributionRequest::getPropertyRightInfoId).filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
    if (CollectionUtils.isNotEmpty(rightInfoList)){
      List<PropertyRightInfo> propertyRightInfos = propertyRightInfoHandler.listByIds(rightInfoList);
      if (CollectionUtils.isNotEmpty(propertyRightInfos)){
        Map<Long, List<PropertyRightInfo>> collect = propertyRightInfos.stream().collect(Collectors.groupingBy(PropertyRightInfo::getId));
        for (AssetIncomeDistributionDTO assetIncome : dtoList) {
          Long propertyRightInfoId = assetIncome.getPropertyRightInfoId();
          if (ObjectUtils.isNotEmpty(propertyRightInfoId)){
            if (collect.get(propertyRightInfoId) != null){
              assetIncome.setPropertyCode(collect.get(propertyRightInfoId).get(0).getPropertyCode());
            }

          }
        }
      }
    }
    return Result.success(dtoList);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<AssetIncomeDistributionDTO> update(AssetIncomeDistributionRequest request) {
    if (null == request || null == request.getId()) {
      String message = "资产收入分配修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    AssetIncomeDistribution assetIncomeDistribution = PlatformMapUtils.getInstance().map(request, AssetIncomeDistribution.class);
    handler.updateById(assetIncomeDistribution);

    return Result.success();
  }

  @Override
  public Result<AssetIncomeDistributionDTO> findById(Long id) {
    AssetIncomeDistribution assetIncomeDistribution = handler.getById(id);
    if (null == assetIncomeDistribution) {
      String message = "资产收入分配单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    AssetIncomeDistributionDTO dto = PlatformMapUtils.getInstance().map(assetIncomeDistribution, AssetIncomeDistributionDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<AssetIncomeDistributionDTO>> findAll(Integer current, Integer size, AssetIncomeDistributionPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<AssetIncomeDistribution> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<AssetIncomeDistribution> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<AssetIncomeDistributionDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, AssetIncomeDistributionDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}