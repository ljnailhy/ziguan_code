package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.HangNetworkInfo;
import com.jk.asset.model.request.page.HangNetworkInfoPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 挂网信息表 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-09 15:15:21
 */
public interface HangNetworkInfoMapper extends BaseMapper<HangNetworkInfo> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-09 15:15:21
   */
  List<HangNetworkInfo> findAll(IPage<HangNetworkInfo> page, @Param("request") HangNetworkInfoPageRequest request);

}