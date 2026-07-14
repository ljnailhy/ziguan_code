package com.jk.asset.service.impl.ledger;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.mapper.property.PropertyRightInfoMapper;
import com.jk.asset.model.dto.AssetDetailLedgerDTO;
import com.jk.asset.model.dto.AssetDetailLedgerExportDTO;
import com.jk.asset.model.entity.property.PropertyRightInfo;
import com.jk.asset.model.request.page.property.PropertyRightInfoPageRequest;
import com.jk.asset.service.ledger.AssetDetailLedgerService;
import com.jk.asset.utils.excel.ExcelUtil;
import com.jk.asset.utils.limit.AssetUserLimitsUtils;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.infrastructure.model.dto.SysOrgDTO;
import com.jk.infrastructure.model.dto.SysUserDTO;
import com.jk.service.client.SysDictionaryClient;
import com.jk.service.client.SysOrgClient;
import com.jk.service.utils.PlatformUserUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wangTao
 * date2024/8/26 16:10
 **/
@RestController
@Slf4j
@Api(tags = "律所台账接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AssetDetailLedgerServiceImpl implements AssetDetailLedgerService {

    private final PropertyRightInfoMapper propertyRightInfoMapper;
    private final AssetUserLimitsUtils assetUserLimitsUtils;
    private final PlatformUserUtils platformUserUtils;
    private final SysDictionaryClient sysDictionaryClient;
    private final SysOrgClient sysOrgClient;
    @Override
    public Result<List<AssetDetailLedgerDTO>> findAll(Integer current, Integer size, PropertyRightInfoPageRequest request) {

        IPage<PropertyRightInfo> page = new Page<>(current, size);
        request.setDoType(BillTypeEnum.PROPERTY_INFO.getKey());
        Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
        if (!userLimitsOrg.isEmpty()){
            SysUserDTO currentUser = platformUserUtils.getCurrentUser();
            request.setFollowUpPerson(currentUser.getId().toString());
        }
        List<AssetDetailLedgerDTO> dtoList = propertyRightInfoMapper.findAssetDetailLedger(page, request);
        if (dtoList.isEmpty()) {
            return Result.success(Lists.newArrayList(), PageFactory.page(page));
        }
        return Result.success(PlatformMapUtils.getInstance().mapAsList(dtoList, AssetDetailLedgerDTO.class), PageFactory.page(page));
    }

    @Override
    public void exportProjectLedger(PropertyRightInfoPageRequest request, HttpServletResponse response) {
        Result<List<AssetDetailLedgerDTO>> result = findAll(1, 10000, request);
        if (null == result || !result.succeedWithData()) {
            return;
        }
        List<AssetDetailLedgerExportDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(result.getData(), AssetDetailLedgerExportDTO.class);
        List<SysDictionaryItemDTO> landUseNatureItems = sysDictionaryClient.findByCode("LAND_USE_NATURE").getData().getItems();
        List<SysDictionaryItemDTO> assetUnitStateItems = sysDictionaryClient.findByCode("ASSET_UNIT_STATE").getData().getItems();
        Result<List<SysOrgDTO>> byIds = sysOrgClient.findByIds(dtoList.stream().map(AssetDetailLedgerExportDTO::getAffiliatedUnit).filter(Objects::nonNull).collect(Collectors.toList()));
        for (AssetDetailLedgerExportDTO exportDTO : dtoList) {
            Long landUseNature = exportDTO.getLandUseNature();
            if (landUseNature != null){
                landUseNatureItems.stream().filter(item -> item.getId().equals(landUseNature)).findFirst().ifPresent(item -> exportDTO.setLandUseNatureStr(item.getItemName()));
            }
            Long assetUnitState = exportDTO.getAssetUnitState();
            if (assetUnitState != null){
                assetUnitStateItems.stream().filter(item -> item.getId().equals(assetUnitState)).findFirst().ifPresent(item -> exportDTO.setAssetUnitStateStr(item.getItemName()));
            }
        }
        if (byIds.succeedWithData()) {
            List<SysOrgDTO> data = byIds.getData();
            for (AssetDetailLedgerExportDTO exportDTO : dtoList) {
                Long affiliatedUnit = exportDTO.getAffiliatedUnit();
                if (affiliatedUnit != null) {
                    data.stream().filter(item -> item.getId().equals(affiliatedUnit)).findFirst().ifPresent(item -> exportDTO.setAffiliatedUnitStr(item.getOrgName()));
                }
            }
        }
        String fileName = "回款明细台账";
        ExcelUtil.exportExcel(dtoList, fileName, AssetDetailLedgerExportDTO.class, response);
    }

}
