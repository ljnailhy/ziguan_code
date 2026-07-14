package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.AllocationInfoDetail;
import com.jk.asset.model.request.page.AllocationInfoDetailPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 项目分配/变更明细 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
public interface AllocationInfoDetailMapper extends BaseMapper<AllocationInfoDetail> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-06-28 10:13:53
   */
  List<AllocationInfoDetail> findAll(IPage<AllocationInfoDetail> page, @Param("request") AllocationInfoDetailPageRequest request);

}