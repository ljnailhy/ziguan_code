package com.jk.asset.mapper.property;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.dto.AssetDetailLedgerDTO;
import com.jk.asset.model.entity.property.PropertyRightInfo;
import com.jk.asset.model.request.page.property.PropertyRightInfoPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产权信息表 Mapper 接口
 *
 * @author wangtao
 * @since 2024-08-17 14:44:27
 */
public interface PropertyRightInfoMapper extends BaseMapper<PropertyRightInfo> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangtao
   * @since 2024-08-17 14:44:27
   */
  List<PropertyRightInfo> findAll(IPage<PropertyRightInfo> page, @Param("request") PropertyRightInfoPageRequest request);

  List<AssetDetailLedgerDTO> findAssetDetailLedger(IPage<PropertyRightInfo> page, PropertyRightInfoPageRequest request);
}