package com.jk.asset.mapper.property;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.property.AssetIncomeDistribution;
import com.jk.asset.model.request.page.property.AssetIncomeDistributionPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 资产收入分配 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-13 09:48:45
 */
public interface AssetIncomeDistributionMapper extends BaseMapper<AssetIncomeDistribution> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-13 09:48:45
   */
  List<AssetIncomeDistribution> findAll(IPage<AssetIncomeDistribution> page, @Param("request") AssetIncomeDistributionPageRequest request);

}