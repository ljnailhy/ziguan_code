package com.jk.asset.service.impl.property;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jk.asset.constant.AssertConstants;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.mapper.property.PropertyInfoMapper;
import com.jk.asset.model.dto.export.PropertyInfoExportDTO;
import com.jk.asset.model.dto.property.PropertyInfoDTO;
import com.jk.asset.model.entity.RevePropertyInfo;
import com.jk.asset.model.entity.property.PropertyBill;
import com.jk.asset.model.entity.property.PropertyInfo;
import com.jk.asset.model.entity.property.PropertyRightInfo;
import com.jk.asset.model.request.page.property.PropertyInfoPageRequest;
import com.jk.asset.model.request.property.ImportPropertyRequest;
import com.jk.asset.model.request.property.PropertyInfoRequest;
import com.jk.asset.model.request.property.PropertyRightInfoRequest;
import com.jk.asset.service.handler.RevePropertyInfoHandler;
import com.jk.asset.service.handler.property.PropertyBillHandler;
import com.jk.asset.service.handler.property.PropertyInfoHandler;
import com.jk.asset.service.handler.property.PropertyRightInfoHandler;
import com.jk.asset.service.property.PropertyInfoService;
import com.jk.asset.utils.excel.ExcelUtil;
import com.jk.asset.utils.limit.AssetUserLimitsUtils;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.infrastructure.model.dto.SysOrgDTO;
import com.jk.infrastructure.model.dto.SysRegionDTO;
import com.jk.infrastructure.model.dto.SysUserDTO;
import com.jk.infrastructure.model.request.sys.org.SysOrgPageRequest;
import com.jk.infrastructure.model.request.sys.region.SysRegionPageRequest;
import com.jk.infrastructure.model.request.sys.user.SysUserRequest;
import com.jk.service.client.SysDictionaryClient;
import com.jk.service.client.SysOrgClient;
import com.jk.service.client.SysRegionClient;
import com.jk.service.client.SysUserClient;
import com.jk.service.utils.PlatformFieldUtils;
import com.jk.service.utils.PlatformFileUtils;
import com.jk.service.utils.PlatformUserUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.jk.common.utils.PlatformStringUtils.REGEX_COMMA;

/**
 * 资产信息接口实现类
 *
 * @author wangshuai
 * @since 2024-07-10 09:47:37
 */
@RestController
@Slf4j
@Api(tags = "资产信息接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyInfoServiceImpl implements PropertyInfoService {

  private final PropertyInfoHandler handler;
  private final PropertyInfoMapper mapper;
  private final PlatformFileUtils platformFileUtils;
  private final SysDictionaryClient sysDictionaryClient;
  private final RevePropertyInfoHandler revePropertyInfoHandler;
  private final SysRegionClient sysRegionClient;
  private final PropertyBillHandler propertyBillHandler;
  private final PlatformFieldUtils platformFieldUtils;
  private final AssetUserLimitsUtils assetUserLimitsUtils;
  private final PlatformUserUtils platformUserUtils;
  private final PropertyRightInfoHandler propertyRightInfoHandler;
  private final SysUserClient sysUserClient;
  private final SysOrgClient sysOrgClient;

  @Override
  public Result<PropertyInfoDTO> add(PropertyInfoRequest request) {
    if (null == request || request.unverified()) {
      String message = "资产信息新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    PropertyInfo propertyInfo = PlatformMapUtils.getInstance().map(request, PropertyInfo.class);
    handler.save(propertyInfo);

    //  通用附件新增
    platformFileUtils.batchAddFile(request.getFileInfoList(),propertyInfo.getId(), BillTypeEnum.PROPERTY_INFO.getKey());
    platformFileUtils.batchAddFile(request.getPropertyPictureList(),propertyInfo.getId(), "PROPERTY_PICTURE");
    List<PropertyRightInfoRequest> propertyRightInfoList = request.getPropertyRightInfoList();
    if (CollectionUtils.isNotEmpty(propertyRightInfoList)){
//      propertyRightInfoList.forEach(item -> item.setPropertyInfoId(propertyInfo.getId()));
      propertyRightInfoList.forEach(item -> {
        item.setPropertyInfoId(propertyInfo.getId());
        item.setDoId(propertyInfo.getId());
        item.setDoType(BillTypeEnum.PROPERTY_INFO.getKey());
      });
      propertyRightInfoHandler.saveBatch(PlatformMapUtils.getInstance().mapAsList(propertyRightInfoList, PropertyRightInfo.class));
    }
    PropertyInfoDTO dto = new PropertyInfoDTO();
    dto.setId(propertyInfo.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {

    List<PropertyBill> propertyBills = propertyBillHandler.list(new LambdaQueryWrapper<PropertyBill>().eq(PropertyBill::getPropertyId, id));
    if (CollectionUtils.isNotEmpty(propertyBills)){
      List<String> billTypeList = propertyBills.stream().map(PropertyBill::getDoType).distinct().collect(Collectors.toList());
      if (billTypeList.contains(BillTypeEnum.OPERATION_INFO.getKey())){
        return Result.error("该资产已被资产运营单据引用，不能删除");
      }
      if (billTypeList.contains(BillTypeEnum.LEASE_INFO.getKey())){
        return Result.error("该资产已被资产租赁单据引用，不能删除");
      }
      if (billTypeList.contains(BillTypeEnum.ASSET_TRANSFER.getKey())){
        return Result.error("该资产已被资产转让单据引用，不能删除");
      }
    }

    // 资产没被引用 才删除
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<PropertyInfoDTO> update(PropertyInfoRequest request) {
    if (null == request || null == request.getId()) {
      String message = "资产信息修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    PropertyInfo propertyInfo = PlatformMapUtils.getInstance().map(request, PropertyInfo.class);
    handler.updateById(propertyInfo);
    List<PropertyRightInfoRequest> propertyRightInfoList = request.getPropertyRightInfoList();
    if (CollectionUtils.isNotEmpty(propertyRightInfoList)){
//      propertyRightInfoList.forEach(item -> item.setPropertyInfoId(propertyInfo.getId()));
      propertyRightInfoList.forEach(item -> {
        item.setPropertyInfoId(propertyInfo.getId());
        item.setDoId(propertyInfo.getId());
        item.setDoType(BillTypeEnum.PROPERTY_INFO.getKey());
      });
      propertyRightInfoHandler.saveOrUpdateBatch(PlatformMapUtils.getInstance().mapAsList(propertyRightInfoList, PropertyRightInfo.class));
      List<Long> deleteList =  propertyRightInfoList.stream()
              .filter(item -> item.getOperateType() != null)
              .filter(item -> item.getOperateType() == OperationTypeEnum.DELETE)
              .map(PropertyRightInfoRequest::getId).collect(Collectors.toList());
      if (CollectionUtils.isNotEmpty(deleteList)){
        propertyRightInfoHandler.removeByIds(deleteList);
      }
    }
    //  通用附件新增
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),propertyInfo.getId(), BillTypeEnum.PROPERTY_INFO.getKey());
    platformFileUtils.batchUpdateFile(request.getPropertyPictureList(),propertyInfo.getId(), "PROPERTY_PICTURE");

    return Result.success();
  }

  @Override
  public Result<PropertyInfoDTO> findById(Long id) {
    PropertyInfo propertyInfo = null;//handler.getById(id);
    IPage<PropertyInfo> page = new Page<>(1, 1);
    PropertyInfoPageRequest request = new PropertyInfoPageRequest();
    request.setId(id);
    List<PropertyInfo> propertyInfoList = mapper.findAll(page,request);
    if (ObjectUtils.isNotEmpty(propertyInfoList)) {
      propertyInfo = propertyInfoList.get(0);
    }
    if (null == propertyInfo) {
      String message = "资产信息单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    PropertyInfoDTO dto = PlatformMapUtils.getInstance().map(propertyInfo, PropertyInfoDTO.class);
    List<PropertyRightInfo> propertyRightInfos =
            propertyRightInfoHandler.list(new LambdaQueryWrapper<PropertyRightInfo>()
                    .eq(PropertyRightInfo::getDoId, id).eq(PropertyRightInfo::getDoType,BillTypeEnum.PROPERTY_INFO.getKey()));
    if (CollectionUtils.isNotEmpty(propertyRightInfos)) {
      dto.setPropertyRightInfoList(PlatformMapUtils.getInstance().mapAsList(propertyRightInfos, PropertyRightInfoRequest.class));
      BigDecimal totalArea = propertyRightInfos.stream()
              .map(PropertyRightInfo::getArea)
              .filter(ObjectUtils::isNotEmpty)
              .reduce(BigDecimal.ZERO, BigDecimal::add);
      dto.setTotalArea(totalArea);
    }
    //  只查询审批通过的数据
//    List<PropertyBill> propertyBillList = propertyBillHandler
//        .list(new LambdaUpdateWrapper<PropertyBill>().eq(PropertyBill::getPropertyId, id));
//
//    if (ObjectUtils.isEmpty(propertyBillList)) {
//      return Result.success(dto);
//    }
//    //  和这个资产相关的全部，运营，转让，租赁id
//    List<Long> billIds = propertyBillList.stream().map(item -> item.getDoId()).distinct().collect(Collectors.toList());
//
//    List<Long> newBillIds = Lists.newArrayList();
//    for (Long billId : billIds) {
//      Result<ProcessStatus> statusResult = processClient.getStatusByDoId(billId);
//      if (null != statusResult &&
//          statusResult.succeedWithData() &&
//          statusResult.getData().getKey().equals(ProcessStatus.completed.getKey())) {
//        newBillIds.add(billId);
//      }
//    }
//
//    if (ObjectUtils.isEmpty(newBillIds)) {
//      return Result.success(dto);
//    }
//
//    //  查询转让收入，运营费用，租赁收入
//    List<AssetIncomeDistribution> list = assetIncomeDistributionHandler
//        .list(new LambdaQueryWrapper<AssetIncomeDistribution>()
//            .in(AssetIncomeDistribution::getDoId, newBillIds));
//
//    BigDecimal transferenceIncome = BigDecimal.ZERO;
//    BigDecimal leaseIncome = BigDecimal.ZERO;
//    BigDecimal operateIncome = BigDecimal.ZERO;
//    for (AssetIncomeDistribution assetIncomeDistribution : list) {
//      BigDecimal propertyMoney = assetIncomeDistribution.getPropertyMoney() == null
//          ? BigDecimal.ZERO : assetIncomeDistribution.getPropertyMoney();
//      if (BillTypeEnum.LEASE_PAYMENT_CYCLE.getKey().equals(assetIncomeDistribution.getDoType())){
//        leaseIncome = leaseIncome.add( propertyMoney);
//      }else if (BillTypeEnum.ASSET_TRANSFER.getKey().equals(assetIncomeDistribution.getDoType())){
//        transferenceIncome = transferenceIncome.add( propertyMoney);
//      }else{
//        operateIncome = operateIncome.add( propertyMoney);
//      }
//    }
    dto.setTransferenceIncome(propertyInfo.getZrMoney()); // 转让收入
    dto.setLeaseIncome(propertyInfo.getZlMoney()); // 租赁收入
    dto.setOperateIncome(propertyInfo.getYyMoney());// 运营费用

    return Result.success(dto);
  }

  @Override
  public Result<String> importData(MultipartFile file) {
    List<ImportPropertyRequest> importPropertyRequests;
    try {
      importPropertyRequests = EasyExcel.read(file.getInputStream()).head(ImportPropertyRequest.class).autoCloseStream(false).sheet().headRowNumber(2).doReadSync();
    } catch (IOException e) {
      log.info("导入异常 ->{}",e.getMessage());
      throw new RuntimeException("导入数据格式错误");
    }
    String string = importPropertyRequests.get(0).toString();
    if (!string.equals(AssertConstants.IMPORT_ASSET)){
      return Result.error("模板错误请下载模板");
    }

    // 前三行不要
    if (importPropertyRequests.size() == 2){
      return Result.error("导入数据为空");
    }

    List<ImportPropertyRequest> filteredList = importPropertyRequests.stream()
        .skip(2)
        .collect(Collectors.toList());
    // 数据校验
    String check = checkImportData(filteredList);

    if (StringUtils.isNotBlank(check)) {
      return Result.error(check);
    }

    return saveProjectInfoList(filteredList);
  }

  @Override
  public Result<List<PropertyInfoDTO>> findAll(Integer current, Integer size, PropertyInfoPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<PropertyInfo> page = new Page<>(current, size);

    //增加权限过滤
    Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
    if (CollectionUtils.isNotEmpty(userLimitsOrg)) {
      // 不为全部权限 显示当前登录 跟进人
      SysUserDTO currentUser = platformUserUtils.getCurrentUser();
      Long userId = currentUser.getId();
      request.setFollowUpPerson(userId.toString());
    }
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<PropertyInfo> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<PropertyInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, PropertyInfoDTO.class);

    List<PropertyRightInfo> propertyRightInfos =
            propertyRightInfoHandler.list(new LambdaQueryWrapper<PropertyRightInfo>()
                    .in(PropertyRightInfo::getPropertyInfoId, dtoList.stream().map(PropertyInfoRequest::getId).collect(Collectors.toList())));
    if (CollectionUtils.isNotEmpty(propertyRightInfos)) {
      Map<Long, List<PropertyRightInfo>> propertyRightInfoMap = propertyRightInfos.stream().collect(Collectors.groupingBy(PropertyRightInfo::getPropertyInfoId));
      for (PropertyInfoDTO propertyInfoDTO : dtoList) {
        List<PropertyRightInfo> propertyRightInfos1 = propertyRightInfoMap.get(propertyInfoDTO.getId());
        if (CollectionUtils.isNotEmpty(propertyRightInfos1)) {
          propertyInfoDTO.setPropertyRightInfoList(PlatformMapUtils.getInstance().mapAsList(propertyRightInfos1, PropertyRightInfoRequest.class));
        }
      }
    }

    List<Long> reveIds = dtoList.stream().map(PropertyInfoRequest::getReveId).collect(Collectors.toList());

//    Map<Long, String> reveName = getReveName(reveIds);

    List<RevePropertyInfo> revePropertyInfoList = revePropertyInfoHandler.list(new LambdaUpdateWrapper<RevePropertyInfo>()
        .in(RevePropertyInfo::getId, reveIds));

    Map<Long, String> mapName = Maps.newHashMap();
    for (RevePropertyInfo revePropertyInfo : revePropertyInfoList) {
      if (BillTypeEnum.REVE.getKey().equals(revePropertyInfo.getBillType())) {
        mapName.put(revePropertyInfo.getId(),revePropertyInfo.getReveName());
      } else {
        mapName.put(revePropertyInfo.getId(),revePropertyInfo.getReveMeasure());
      }
    }

    //  处理反担保措施名称
    for (PropertyInfoDTO propertyInfoDTO : dtoList) {
      propertyInfoDTO.setReveName(mapName.get(propertyInfoDTO.getReveId()));
    }

    return Result.success(dtoList, PageFactory.page(page));
  }

  @Override
  public void export(PropertyInfoPageRequest request, HttpServletResponse response) {
    Result<List<PropertyInfoDTO>> result = findAll(1, 10000, request);
    if (null == result || !result.succeedWithData()) {
      return;
    }
    List<PropertyInfoExportDTO> dtoList = new ArrayList<>();
    for (PropertyInfoDTO datum : result.getData()) {
      if (CollectionUtils.isEmpty(datum.getPropertyRightInfoList()) || datum.getPropertyRightInfoList().size() == 1){
        dtoList.add(PlatformMapUtils.getInstance().map(datum, PropertyInfoExportDTO.class));
      }else {
        for (PropertyRightInfoRequest propertyRightInfo : datum.getPropertyRightInfoList()) {
          PropertyInfoExportDTO exportDTO = PlatformMapUtils.getInstance().map(datum, PropertyInfoExportDTO.class);
          exportDTO.setPropertyCode(propertyRightInfo.getPropertyCode());
          exportDTO.setAssetUnitState(propertyRightInfo.getAssetUnitState());
          exportDTO.setOriginalValueEntries(propertyRightInfo.getOriginalValue());
          exportDTO.setAreaEntries(propertyRightInfo.getArea());
          exportDTO.setPropertyOwner(propertyRightInfo.getPropertyOwner());
          exportDTO.setPropertyEndDate(propertyRightInfo.getPropertyEndDate());
          exportDTO.setPropertyTransferOwnership(propertyRightInfo.getPropertyTransferOwnership());
          exportDTO.setAssetUse(propertyRightInfo.getAssetUse());
          exportDTO.setAddressEntries(propertyRightInfo.getAddress());
          exportDTO.setRemark(propertyRightInfo.getRemark());
          dtoList.add(exportDTO);
        }
      }
    }
//
//    List<PropertyInfoExportDTO> dtoList = result.getData()
//            .stream()
//            .flatMap(propertyInfoDTO -> {
//              if (CollectionUtils.isEmpty(propertyInfoDTO.getPropertyRightInfoList())) {
//                // 如果为空或 null，直接创建一个对象
//                PropertyInfoExportDTO exportDTO = ;
//                return Lists.newArrayList(exportDTO).stream();
//              } else {
//                // 如果非空，平铺每个 SubData
//                List<PropertyInfoExportDTO> tempExportDTO = PlatformMapUtils.getInstance()
//                        .mapAsList(propertyInfoDTO.getPropertyRightInfoList(), PropertyInfoExportDTO.class);
//                return tempExportDTO.stream()
//                        .peek(item -> PlatformMapUtils.getInstance().map(propertyInfoDTO, item));
//              }
//            })
//            .collect(Collectors.toList());
    List<String> typeIds = dtoList.stream().filter(e -> null != e.getType()).map(e -> String.valueOf(e.getType())).distinct().collect(Collectors.toList());
    List<String> propertyTypeList = dtoList.stream().filter(e -> null != e.getPropertyType()).map(e -> String.valueOf(e.getPropertyType())).distinct().collect(Collectors.toList());
    List<String> sourceTypeList = dtoList.stream().filter(e -> null != e.getSourceType()).map(e -> String.valueOf(e.getSourceType())).distinct().collect(Collectors.toList());
    List<String> propertyTagList = dtoList.stream().filter(item -> item.getPropertyTag() != null && !item.getPropertyTag().contains("null")).map(PropertyInfoExportDTO::getPropertyTag).filter(PlatformStringUtils::isNotBlank).distinct().collect(Collectors.toList());
    List<String> accessWayList = dtoList.stream().filter(e -> null != e.getAccessWay()).map(e -> String.valueOf(e.getAccessWay())).distinct().collect(Collectors.toList());
    List<String> landUseNatureList = dtoList.stream().filter(e -> null != e.getLandUseNature()).map(e -> String.valueOf(e.getLandUseNature())).distinct().collect(Collectors.toList());
    List<String> assetUnitStateList = dtoList.stream().filter(e -> null != e.getAssetUnitState()).map(e -> String.valueOf(e.getAssetUnitState())).distinct().collect(Collectors.toList());

    Result<List<SysOrgDTO>> byIds = sysOrgClient.findByIds(dtoList.stream().map(PropertyInfoExportDTO::getAffiliatedUnit).collect(Collectors.toList()));
    List<String> dictionaryItemIds = Lists.newArrayList();
    dictionaryItemIds.addAll(typeIds);
    dictionaryItemIds.addAll(propertyTypeList);
    dictionaryItemIds.addAll(sourceTypeList);
    dictionaryItemIds.addAll(propertyTagList);
    dictionaryItemIds.addAll(accessWayList);
    dictionaryItemIds.addAll(landUseNatureList);
    dictionaryItemIds.addAll(assetUnitStateList);

    List<Long> provinceIds = dtoList.stream().map(PropertyInfoExportDTO::getProvince).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    List<Long> cityIds = dtoList.stream().map(PropertyInfoExportDTO::getCity).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    List<Long> districtIds = dtoList.stream().map(PropertyInfoExportDTO::getDistrict).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    List<Long> regionIds = Lists.newArrayList();
    regionIds.addAll(provinceIds);
    regionIds.addAll(cityIds);
    regionIds.addAll(districtIds);

    Map<Long, SysDictionaryItemDTO> dictionaryItemById = platformFieldUtils.getDictionaryItemNameById(dictionaryItemIds);
    Map<Long, String> regionNameById = platformFieldUtils.getRegionNameById(regionIds);

    dtoList.forEach(e -> {
      if (MapUtils.isNotEmpty(dictionaryItemById)) {
        e.setTypeName(Optional.ofNullable(dictionaryItemById.get(e.getType())).orElse(new SysDictionaryItemDTO()).getItemName());
        e.setPropertyTypeName(Optional.ofNullable(dictionaryItemById.get(e.getPropertyType())).orElse(new SysDictionaryItemDTO()).getItemName());
        e.setSourceTypeName(Optional.ofNullable(dictionaryItemById.get(e.getSourceType())).orElse(new SysDictionaryItemDTO()).getItemName());
        e.setAccessWayName(Optional.ofNullable(dictionaryItemById.get(e.getAccessWay())).orElse(new SysDictionaryItemDTO()).getItemName());
        e.setLandUseNatureName(Optional.ofNullable(dictionaryItemById.get(e.getLandUseNature())).orElse(new SysDictionaryItemDTO()).getItemName());
        e.setAssetUnitStateStr(Optional.ofNullable(dictionaryItemById.get(e.getAssetUnitState())).orElse(new SysDictionaryItemDTO()).getItemName());
        if (PlatformStringUtils.isNotBlank(e.getPropertyTag())) {
          List<String> nameList = Lists.newArrayList();
          for (String id : e.getPropertyTag().split(REGEX_COMMA)) {
            if (StringUtils.isBlank(id)) {
              continue;
            }
            if (id.contains("null")) {
              continue;
            }
            SysDictionaryItemDTO dictionaryItemDTO = dictionaryItemById.get(Long.valueOf(id));
            if (null == dictionaryItemDTO) {
              continue;
            }
            nameList.add(dictionaryItemDTO.getItemName());
          }
          e.setPropertyTagName(PlatformStringUtils.join(nameList, REGEX_COMMA));
        }
      }

      if (MapUtils.isNotEmpty(regionNameById)) {
        e.setRegionName(Lists.newArrayList(regionNameById.get(e.getProvince()), regionNameById.get(e.getCity()), regionNameById.get(e.getDistrict()))
            .stream()
            .filter(StringUtils::isNotBlank)
            .collect(Collectors.joining(" - ")));
      }

      if (ObjectUtils.isNotEmpty(e.getPropertyState())) {
        e.setPropertyStateName(e.getPropertyState().getValue());
      }
      if (ObjectUtils.isNotEmpty(e.getFollowUpPerson())){
        String[] split = e.getFollowUpPerson().split(REGEX_COMMA);
        List<SysUserDTO> userByIds = platformUserUtils.getUserByIds(Arrays.stream(split)
                .map(Long::parseLong)
                .collect(Collectors.toList()), false);
        String collect = userByIds.stream().map(SysUserRequest::getUsername).collect(Collectors.joining(","));
        e.setFollowUpPerson(collect);
      }
      if (ObjectUtils.isNotEmpty(e.getAffiliatedUnit())){
        byIds.getData().forEach(item -> {
          if (e.getAffiliatedUnit().equals(item.getId())){
            e.setAffiliatedUnitName(item.getOrgName());
          }
        });
      }

    });

    String fileName = "资产台账";
    ExcelUtil.exportExcel(dtoList, fileName, PropertyInfoExportDTO.class,true,response);
  }


  /**
   * 处理保存导入数据
   * @param filteredList  导入数据
   * @return void
   * @author wangshuai
   * @since 2024/7/15 10:00
   **/
  private Result<String> saveProjectInfoList(List<ImportPropertyRequest> filteredList) {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
    //  处理相关数据（如果资产重复先做更新处理）
    //  处理成功数据
    List<PropertyInfo> successDate = Lists.newArrayList();
    Map<String, List<PropertyRightInfo>> successRightInfo = new HashMap<>();
    //  处理字符串转id
    Map<String, List<ImportPropertyRequest>> groupList = filteredList.stream().collect(Collectors.groupingBy(ImportPropertyRequest::getPropertyName));
    List<SysDictionaryItemDTO> typeItems = getDictItems(DictEnum.PROPERTY_LARGE_CATEGORY.getKey());
    List<SysDictionaryItemDTO> propertyTypeItems = getDictItems(DictEnum.PROPERTY_TYPE.getKey());
    List<SysDictionaryItemDTO> sourceTypeItems = getDictItems(DictEnum.SOURCE_TYPE.getKey());
    List<SysDictionaryItemDTO> accessWayItems = getDictItems(DictEnum.ACCESS_WAY.getKey());
    List<SysDictionaryItemDTO> landUseNatureItems = getDictItems(DictEnum.LAND_USE_NATURE.getKey());
    List<SysDictionaryItemDTO> assetUnitStateItems = getDictItems(DictEnum.ASSET_UNIT_STATE.getKey());
    List<String> accountList = filteredList.stream().map(ImportPropertyRequest::getFollowUpPersonAccount).filter(PlatformStringUtils::isNotBlank).collect(Collectors.toList());
    Map<String, Long> userMap = new HashMap<>();
    if (CollectionUtils.isNotEmpty(accountList)) {
      Set<String> accountSet = new HashSet<>();
      for (String s : accountList) {
        if (s.contains(",")) {
          String[] split = s.split(REGEX_COMMA);
          List<String> list = Arrays.asList(split);
          accountSet.addAll(list);
        } else {
          accountSet.add(s);
        }
      }
      for (String s : accountSet) {
        Result<SysUserDTO> byAccount = sysUserClient.findByAccount(s);
        if (byAccount.succeedWithData()) {
          Long userId = byAccount.getData().getId();
          userMap.put(s, userId);
        }else {
          return Result.error("资产台账导入，用户账号:"+ s + "不存在");
        }
      }
    }
    Map<String, Long> locationMap = new HashMap<>();
    SysRegionPageRequest regionPageRequest = new SysRegionPageRequest();
    regionPageRequest.setName("湖南省");
    Result<List<SysRegionDTO>> all = sysRegionClient.findAll(1, 1, regionPageRequest);
    Long provinceId = all.getData().get(0).getId();
    List<Long> cityList = new ArrayList<>();
    cityList.add(provinceId);
    Map<String, Long> cityMap = platformFieldUtils.getRegionIdByParentIds(cityList);
    // 湖南省全部市
    List<Long> districtList = cityMap.values().stream().distinct().collect(Collectors.toList());
    Map<String, Long> districtMap = platformFieldUtils.getRegionIdByParentIds(districtList);
    locationMap.put("湖南省", provinceId);
    locationMap.putAll(cityMap);
    locationMap.putAll(districtMap);
    SysOrgPageRequest sysOrgPageRequest = new SysOrgPageRequest();
    sysOrgPageRequest.setOrgName("湖南省中小企业融资担保有限公司");
    Result<List<SysOrgDTO>> orgClientAll1 = sysOrgClient.findAll(1, 1, sysOrgPageRequest);
    sysOrgPageRequest.setOrgName("湖南省文化旅游融资担保有限公司");
    Result<List<SysOrgDTO>> orgClientAll2 = sysOrgClient.findAll(1, 1, sysOrgPageRequest);

    for (Map.Entry<String, List<ImportPropertyRequest>> stringListEntry : groupList.entrySet()) {
      PropertyInfo propertyInfo = new PropertyInfo();
      // 资产主信息
      ImportPropertyRequest request = stringListEntry.getValue().get(0);
      propertyInfo.setPropertyName(request.getPropertyName());
      // 大类
      typeItems.stream().filter(typeItem -> typeItem.getItemName().equals(request.getType())).findFirst()
              .ifPresent(typeItem -> propertyInfo.setType(typeItem.getId()));
      // 资产分类
      propertyTypeItems.stream().filter(item -> item.getItemName().equals(request.getPropertyType())).findFirst()
              .ifPresent(typeItem -> propertyInfo.setPropertyType(typeItem.getId()));
      // 来源方式
      sourceTypeItems.stream().filter(item -> item.getItemName().equals(request.getSourceType())).findFirst()
              .ifPresent(typeItem -> propertyInfo.setSourceType(typeItem.getId()));
      // 来源项目
      propertyInfo.setProjectName(request.getProjectId());
      // 省市区
      if (StringUtils.isNotBlank(request.getProvince()) && locationMap.containsKey(request.getProvince())){
        propertyInfo.setProvince(locationMap.get(request.getProvince()));
      }
      if (StringUtils.isNotBlank(request.getCity()) && locationMap.containsKey(request.getCity())){
        propertyInfo.setCity(locationMap.get(request.getCity()));
      }
      if (StringUtils.isNotBlank(request.getDistrict()) && locationMap.containsKey(request.getDistrict())){
        propertyInfo.setDistrict(locationMap.get(request.getDistrict()));
      }
      // 详细地址
      propertyInfo.setAddress(request.getAddress());
      // 资产取得方式
      accessWayItems.stream().filter(item -> item.getItemName().equals(request.getAccessWay())).findFirst()
              .ifPresent(typeItem -> propertyInfo.setAccessWay(typeItem.getId()));
      if (request.getFollowUpPersonAccount().contains(",")){
        String collect = Arrays.stream(request.getFollowUpPersonAccount().split(REGEX_COMMA))
                .map(account -> userMap.get(account).toString())
                .collect(Collectors.joining(","));
        propertyInfo.setFollowUpPerson(collect);
      }else if (StringUtils.isNotBlank(request.getFollowUpPersonAccount())){
        propertyInfo.setFollowUpPerson(userMap.get(request.getFollowUpPersonAccount()).toString());
      }
      // 用地性质
      landUseNatureItems.stream().filter(item -> item.getItemName().equals(request.getLandUseNature())).findFirst()
              .ifPresent(typeItem -> propertyInfo.setLandUseNature(typeItem.getId()));
      if (StringUtils.isNotBlank(request.getAssertTime())){
          try {

            propertyInfo.setAssertTime(sdf.parse(request.getAssertTime()));
          } catch (ParseException e) {
              throw new RuntimeException("资产获得时间 格式异常");
          }
      }
      if ("湖南省中小企业融资担保有限公司".equals(request.getAffiliatedUnit())){
        if (orgClientAll1.succeedWithData()){
          propertyInfo.setAffiliatedUnit(orgClientAll1.getData().get(0).getId());
        }
      }
      if ("湖南省文化旅游融资担保有限公司".equals(request.getAffiliatedUnit())){
          if (orgClientAll2.succeedWithData()){
            propertyInfo.setAffiliatedUnit(orgClientAll2.getData().get(0).getId());
          }
      }
      // 资产状态
        for (PropertyInfoRequest.PropertyStateEnum value : PropertyInfoRequest.PropertyStateEnum.values()) {
          if (value.getValue().equals(request.getPropertyState())){
            propertyInfo.setPropertyState(value);
            break;
          }
        }
//      Arrays.stream(PropertyInfoRequest.PropertyStateEnum.values())
//              .filter(value -> Objects.equals(value.getValue(), request.getPropertyState()))
//              .findFirst().ifPresent(propertyInfo::setPropertyState);
      // 资产描述
      propertyInfo.setPropertyDescribe(request.getPropertyDescribe());
      // 资产备注
      propertyInfo.setTransferOwnershipRemark(request.getTransferOwnershipRemark());
      // 资产抵债价格
      propertyInfo.setDebtRepaymentMoney(StringUtils.isBlank(request.getDebtRepaymentMoney()) ? null : new BigDecimal(request.getDebtRepaymentMoney()));
      // 资产原值
      propertyInfo.setOriginalValue(StringUtils.isBlank(request.getOriginalValue()) ? null : new BigDecimal(request.getOriginalValue()));
      // 资产净值
      propertyInfo.setNetWorth(StringUtils.isBlank(request.getNetWorth()) ? null : new BigDecimal(request.getNetWorth()));
      // 资产处置价格
      propertyInfo.setDisposalPrice(StringUtils.isBlank(request.getDisposalPrice()) ? null : new BigDecimal(request.getDisposalPrice()));
      // 获得资产支付税费
      propertyInfo.setTaxeFee(StringUtils.isBlank(request.getTaxeFee()) ? null : new BigDecimal(request.getTaxeFee()));
      // 处置资产支付税费
      propertyInfo.setDisposeFee(StringUtils.isBlank(request.getDisposeFee()) ? null : new BigDecimal(request.getDisposeFee()));
      // 获得资产支付的其他费用
      propertyInfo.setOriginalObligorFee(StringUtils.isBlank(request.getOriginalObligorFee()) ? null : new BigDecimal(request.getOriginalObligorFee()));
      // 资产盈亏
      propertyInfo.setProfitAndLoss(StringUtils.isBlank(request.getProfitAndLoss()) ? null : new BigDecimal(request.getProfitAndLoss()));
      List<ImportPropertyRequest> value = stringListEntry.getValue();
      BigDecimal reduce = value.stream()
              .map(item -> new BigDecimal(item.getArea()))
              .reduce(BigDecimal.ZERO, BigDecimal::add);
      propertyInfo.setArea(reduce);
      successDate.add(propertyInfo);
      List<PropertyRightInfo> propertyRightInfos = new ArrayList<>();
      for (ImportPropertyRequest rightRequest : value) {
        if ("".equals(rightRequest.getPropertyCode())){
          continue;
        }
        PropertyRightInfo propertyRightInfo = new PropertyRightInfo();
        propertyRightInfo.setDoType(BillTypeEnum.PROPERTY_INFO.getKey());
        // 权证号/编号
        propertyRightInfo.setPropertyCode(rightRequest.getPropertyCode());
        // 资产单元状态
        assetUnitStateItems.stream().filter(item -> item.getItemName().equals(rightRequest.getAssetUnitState())).findFirst()
                .ifPresent(typeItem -> propertyRightInfo.setAssetUnitState(typeItem.getId()));
        // 资产原值
        propertyRightInfo.setOriginalValue(StringUtils.isBlank(rightRequest.getOriginalValueEntries()) ? null : new BigDecimal(rightRequest.getOriginalValueEntries()));
        // 面积
        propertyRightInfo.setArea(StringUtils.isBlank(rightRequest.getArea()) ? null : new BigDecimal(rightRequest.getArea()));
        // 产权人名称
        propertyRightInfo.setPropertyOwner(StringUtils.isNotBlank(rightRequest.getPropertyOwner()) ? rightRequest.getPropertyOwner() : null);
        // 权证到期日
        if (StringUtils.isNotEmpty(rightRequest.getPropertyEndDate())){
          try {
            propertyRightInfo.setPropertyEndDate(sdf.parse(rightRequest.getPropertyEndDate()));
          } catch (ParseException e) {
            throw new RuntimeException("权证到期日 格式异常");
          }
        }
        // 资产过户日期
        if (StringUtils.isNotEmpty(rightRequest.getPropertyTransferOwnership())){
          try {
            propertyRightInfo.setPropertyTransferOwnership(sdf.parse(rightRequest.getPropertyTransferOwnership()));
          } catch (ParseException e) {
            throw new RuntimeException("资产登记日期 格式异常");
          }
        }
        // 资产用途
        propertyRightInfo.setAssetUse(rightRequest.getAssetUse());
        // 坐落
        propertyRightInfo.setAddress(rightRequest.getAddressEntries());
        // 备注
        propertyRightInfo.setRemark(rightRequest.getRemark());
        propertyRightInfos.add(propertyRightInfo);
      }
      if (!propertyRightInfos.isEmpty()){
        successRightInfo.put(propertyInfo.getPropertyName(), propertyRightInfos);
      }
    }
    handler.saveBatch(successDate);
    List<PropertyRightInfo> addList = new ArrayList<>();
    for (PropertyInfo propertyInfo : successDate) {
      String propertyName = propertyInfo.getPropertyName();
      if (successRightInfo.containsKey(propertyName)){
        List<PropertyRightInfo> propertyRightInfos = successRightInfo.get(propertyName);
        propertyRightInfos.forEach(item -> item.setPropertyInfoId(propertyInfo.getId()));
        propertyRightInfos.forEach(item -> item.setDoId(propertyInfo.getId()));
        addList.addAll(propertyRightInfos);
      }
    }
    propertyRightInfoHandler.saveBatch(addList);
    return Result.success("导入成功");
  }

  /**
   * 导入校验必填项
   * @param filteredList  导入数据
   * @return java.lang.String
   * @author wangshuai
   * @since 2024/7/15 9:59
   **/
  private String checkImportData(List<ImportPropertyRequest> filteredList) {
    int i = 5;
    for (ImportPropertyRequest importPropertyRequest : filteredList) {
      String message =  "第" + i + "行必填项";
      String emptyMessage =  "为空";
      if (StringUtils.isAnyBlank(importPropertyRequest.getPropertyName())){
        return message + "资产名称" + emptyMessage;
      }
      if (StringUtils.isAnyBlank(importPropertyRequest.getType())){
        return message + "大类" + emptyMessage;
      }
      if (StringUtils.isAnyBlank(importPropertyRequest.getPropertyType())){
        return message + "资产分类" + emptyMessage;
      }
      if (StringUtils.isAnyBlank(importPropertyRequest.getSourceType())){
        return message + "来源方式" + emptyMessage;
      }

      if (isNullOrZero(importPropertyRequest.getOriginalValue())){
        return message + "资产原值(元)" + emptyMessage;
      }
      if (isNullOrZero(importPropertyRequest.getNetWorth())){
        return message + "资产净值(元)" + emptyMessage;
      }
      if ((StringUtils.isNotEmpty(importPropertyRequest.getOriginalValueEntries())
              || !"0.00".equals(importPropertyRequest.getArea())
              || StringUtils.isNotEmpty(importPropertyRequest.getPropertyOwner())
              || StringUtils.isNotEmpty(importPropertyRequest.getPropertyEndDate())
              || StringUtils.isNotEmpty(importPropertyRequest.getPropertyTransferOwnership())
              || StringUtils.isNotEmpty(importPropertyRequest.getAssetUse())
              || StringUtils.isNotEmpty(importPropertyRequest.getAddressEntries())
              || StringUtils.isNotEmpty(importPropertyRequest.getRemark())) && StringUtils.isEmpty(importPropertyRequest.getPropertyCode())
      ) {
        return message + "权证号/编号" + emptyMessage;
      }
      if ((StringUtils.isNotEmpty(importPropertyRequest.getOriginalValueEntries())
              || !"0.00".equals(importPropertyRequest.getArea())
              || StringUtils.isNotEmpty(importPropertyRequest.getPropertyOwner())
              || StringUtils.isNotEmpty(importPropertyRequest.getPropertyEndDate())
              || StringUtils.isNotEmpty(importPropertyRequest.getPropertyTransferOwnership())
              || StringUtils.isNotEmpty(importPropertyRequest.getAssetUse())
              || StringUtils.isNotEmpty(importPropertyRequest.getAddressEntries())
              || StringUtils.isNotEmpty(importPropertyRequest.getRemark())) && StringUtils.isEmpty(importPropertyRequest.getAssetUnitState())
      ) {
        return message + "资产单元状态" + emptyMessage;
      }
      i++;
    }
    return "";
  }

  public static boolean isNullOrZero(String value) {
    if (StringUtils.isBlank(value)){
      return true;
    }
    try {
      new BigDecimal(value);
    }catch (Exception e){
      log.error("导入数据校验异常 ->{}",value);
      return true;
    }
    return false;
  }

  private List<SysDictionaryItemDTO> getDictItems(String dictCode){
     return  sysDictionaryClient.findByCode(dictCode)
            .getData()
            .getItems();
  }
}