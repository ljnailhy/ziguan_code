package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.AllocationInfo;
import com.jk.asset.model.request.page.AllocationInfoPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 分配/变更主表 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
public interface AllocationInfoMapper extends BaseMapper<AllocationInfo> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-06-28 10:13:53
   */
  List<AllocationInfo> findAll(IPage<AllocationInfo> page, @Param("request") AllocationInfoPageRequest request);

}