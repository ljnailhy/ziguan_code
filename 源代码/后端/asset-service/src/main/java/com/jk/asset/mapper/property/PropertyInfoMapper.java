package com.jk.asset.mapper.property;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.dto.PropertyInfoRemindDTO;
import com.jk.asset.model.dto.property.AnalysePropertyInfoDTO;
import com.jk.asset.model.entity.property.PropertyInfo;
import com.jk.asset.model.request.page.property.PropertyInfoPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资产信息 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-10 09:47:37
 */
public interface PropertyInfoMapper extends BaseMapper<PropertyInfo> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-10 09:47:37
   */
  List<PropertyInfo> findAll(IPage<PropertyInfo> page, @Param("request") PropertyInfoPageRequest request);

  /**
   * 首页资产分析饼图统计
   *
   * @param request 入参
   * @return java.util.List<com.jk.asset.model.dto.property.AnalysePropertyInfoDTO>
   * @author Minpei Yuan
   * @since 2024/8/22 16:16
   */
  List<AnalysePropertyInfoDTO> analysePie(@Param("request") PropertyInfoPageRequest request);

  /**
   * 首页资产分析柱状图统计
   *
   * @param request 入参
   * @return java.util.List<com.jk.asset.model.dto.property.AnalysePropertyInfoDTO>
   * @author Minpei Yuan
   * @since 2024/8/22 16:16
   */
  List<AnalysePropertyInfoDTO> analyseHistogram(@Param("request") PropertyInfoPageRequest request);

    List<PropertyInfoRemindDTO> findUnOperation();


}