package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.ProjectBusinessInfo;
import com.jk.asset.model.request.page.ProjectBusinessInfoPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 业务信息表 Mapper 接口
 *
 * @author wangtao
 * @since 2024-07-26 18:51:12
 */
public interface ProjectBusinessInfoMapper extends BaseMapper<ProjectBusinessInfo> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangtao
   * @since 2024-07-26 18:51:12
   */
  List<ProjectBusinessInfo> findAll(IPage<ProjectBusinessInfo> page, @Param("request") ProjectBusinessInfoPageRequest request);

}