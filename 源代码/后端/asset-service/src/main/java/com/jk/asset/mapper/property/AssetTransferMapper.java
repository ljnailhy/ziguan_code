package com.jk.asset.mapper.property;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.property.AssetTransfer;
import com.jk.asset.model.request.page.property.AssetTransferPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 资产转让 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-13 09:53:13
 */
public interface AssetTransferMapper extends BaseMapper<AssetTransfer> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-13 09:53:13
   */
  List<AssetTransfer> findAll(IPage<AssetTransfer> page, @Param("request") AssetTransferPageRequest request);

}