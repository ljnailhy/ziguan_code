package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jk.asset.model.entity.CompensatorySync;
import com.jk.asset.model.request.page.CompensatorySyncPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目同步接收表 Mapper 接口
 *
 * @author wangtao
 * @since 2024-07-25 16:40:16
 */
public interface CompensatorySyncMapper extends BaseMapper<CompensatorySync> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangtao
   * @since 2024-07-25 16:40:16
   */
  List<CompensatorySync> findAll(@Param("request") CompensatorySyncPageRequest request);

}