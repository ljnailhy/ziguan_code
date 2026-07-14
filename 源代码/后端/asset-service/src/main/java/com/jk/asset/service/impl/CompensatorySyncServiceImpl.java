package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.enums.IndustryPolicySupportEnum;
import com.jk.asset.mapper.CompensatorySyncMapper;
import com.jk.asset.model.dto.CompensatorySyncDTO;
import com.jk.asset.model.entity.CompensatorySync;
import com.jk.asset.model.request.page.CompensatorySyncPageRequest;
import com.jk.asset.service.CompensatorySyncService;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.dto.SysDictionaryDTO;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.service.client.SysDictionaryClient;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 项目同步接收表接口实现类
 *
 * @author wangtao
 * @since 2024-07-25 16:40:16
 */
@RestController
@Slf4j
@Api(tags = "项目同步接收表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompensatorySyncServiceImpl implements CompensatorySyncService {
  private final SysDictionaryClient sysDictionaryClient;
  private final CompensatorySyncMapper mapper;


  @Override
  public Result<List<CompensatorySyncDTO>> findCompensatorySyncAll(Integer current, Integer size, CompensatorySyncPageRequest request) {
    Instant startInstant = Instant.now();
    log.info("开始");
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    if (ObjectUtils.isEmpty(request.getSyncStatus())){
      request.setSyncStatus(false);
    }
    if (!ObjectUtils.isEmpty(request.getIndustryPolicySupport())){
      SysDictionaryItemDTO sysDictionaryItemDTO = sysDictionaryClient.findByCode(IndustryPolicySupportEnum.INDUSTRY_POLICY_SUPPORT.getKey()).getData().getItems().stream().filter(item -> item.getId().toString().equals(request.getIndustryPolicySupport())).findAny().get();
      request.setIndustryPolicySupport(sysDictionaryItemDTO.getItemCode());
    }
    List<SysDictionaryItemDTO> productItems = sysDictionaryClient.findByCode(DictEnum.ON_LINE.getKey()).getData().getItems();
    productItems.addAll(sysDictionaryClient.findByCode(DictEnum.OFF_LINE.getKey()).getData().getItems());
    productItems.addAll(sysDictionaryClient.findByCode(DictEnum.TRADITIONAL_PRODUCT.getKey()).getData().getItems());
    if (!ObjectUtils.isEmpty(request.getProductName())) {
      for (SysDictionaryItemDTO productItem : productItems) {
        if (request.getProductName().equals(productItem.getId().toString())) {
          request.setProductName(productItem.getItemCode());
        }
      }
    }
    // Step1：创建一个 Page 对象
    IPage<CompensatorySyncDTO> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<CompensatorySync> doList = mapper.findAll(request);
    log.info("耗时：" + Duration.between(startInstant, Instant.now()) + " 毫秒");
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // 按照身份证分组
    Map<String, List<CompensatorySync>> collect = doList.stream().collect(Collectors.groupingBy(CompensatorySync::getCredentialNo));
    List<CompensatorySyncDTO> resList = new ArrayList<>();
    Result<SysDictionaryDTO> support = sysDictionaryClient.findByCode(IndustryPolicySupportEnum.INDUSTRY_POLICY_SUPPORT.getKey());
    Result<SysDictionaryDTO> gxb = sysDictionaryClient.findByCode(DictEnum.INDUSTRY_GXB.getKey());
    for (Map.Entry<String, List<CompensatorySync>> stringListEntry : collect.entrySet()) {
      String key = stringListEntry.getKey();
      List<CompensatorySync> value = stringListEntry.getValue();
      CompensatorySyncDTO dto = new CompensatorySyncDTO();
      dto.setCredentialNo(key);
      dto.setCustomerName(value.get(0).getCustomerName());
      dto.setCustomerProperty(value.get(0).getCustomerProperty());
      dto.setCredentialType(value.get(0).getCredentialType());
      dto.setContact(value.get(0).getContact());
      dto.setTel(value.get(0).getTel());
      dto.setProvince(value.get(0).getProvince());
      dto.setCity(value.get(0).getCity());
      dto.setArea(value.get(0).getArea());
      String industryGxb = value.get(0).getIndustryGxb();
      if (!ObjectUtils.isEmpty(industryGxb)){
        if (gxb.succeed()){
          List<SysDictionaryItemDTO> items = gxb.getData().getItems();
          for (SysDictionaryItemDTO item : items) {
            if (item.getItemCode().equals(industryGxb)){
              dto.setIndustryGxb(item.getId());
              break;
            }
          }
        }
      }
      dto.setEnterpriseSize(value.get(0).getEnterpriseSize());
      String industryPolicySupport = value.get(0).getIndustryPolicySupport();
      if (!ObjectUtils.isEmpty(industryPolicySupport)){
        if (support.succeed()){
          List<SysDictionaryItemDTO> items = support.getData().getItems();
          String[] split = industryPolicySupport.split(",");
          String result = Arrays.stream(split)
                  .map(code -> items.stream()
                          .filter(item -> item.getItemCode().equals(code))
                          .findFirst()
                          .map(SysDictionaryItemDTO::getId)
                          .map(Object::toString)
                          .orElse(null))
                  .filter(Objects::nonNull)
                  .collect(Collectors.joining(","));
          dto.setIndustryPolicySupport(result);
        }
      }
      dto.setProjectFrom(value.get(0).getProjectFrom());
      // 统计总的代偿金额 最早代偿时间
      BigDecimal decimal = BigDecimal.ZERO;
      Optional<CompensatorySync> earliestRepayment = value.stream()
              .min(Comparator.comparing(CompensatorySync::getRepaymentDate));
        earliestRepayment.ifPresent(sync -> dto.setRepaymentDate(sync.getRepaymentDate()));
        StringBuilder productStr = new StringBuilder();
        StringBuilder businessStr = new StringBuilder();
      for (CompensatorySync comSync : value) {
        BigDecimal repaymentAmount = comSync.getRepaymentAmount();
        if (!ObjectUtils.isEmpty(repaymentAmount)) {
          decimal = decimal.add(repaymentAmount);
        }
        String productName = comSync.getProductName();
        for (SysDictionaryItemDTO productItem : productItems) {
          String itemCode = productItem.getItemCode();
          Long itemId = productItem.getId();
          if (itemCode.equals(productName)) {
            if (productStr.indexOf(itemId.toString()) == -1) {
              if (productStr.length() == 0) {
                productStr.append(itemId);
              } else {
                productStr.append(",").append(itemId);
              }
            }
          }
        }
        String businessType = comSync.getBusinessType();
        if (businessStr.indexOf(businessType) == -1) {
          if (ObjectUtils.isEmpty(businessStr)) {
            businessStr.append(businessType);
          } else {
            businessStr.append(",").append(businessType);
          }
        }
      }
      dto.setProductName(productStr.toString());
      dto.setBusinessType(businessStr.toString());
      dto.setRepaymentAmount(decimal);
      resList.add(dto);
    }
    log.info("耗时：" + Duration.between(startInstant, Instant.now()) + " 毫秒");
    // 空则直接返回
    if (CollectionUtils.isEmpty(resList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<CompensatorySyncDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(resList, CompensatorySyncDTO.class);
    page.setTotal(dtoList.size());
    page.setCurrent(current);
    page.setSize(size);
    int start = (current - 1) * size;
    int end = Math.min(start + size, dtoList.size());
    return Result.success(dtoList.subList(start, end), PageFactory.page(page));
  }

}