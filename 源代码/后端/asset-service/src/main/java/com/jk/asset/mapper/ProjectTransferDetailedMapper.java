package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.ProjectTransferDetailed;
import com.jk.asset.model.request.page.ProjectTransferDetailedPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *  项目移交明细 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-17 18:27:56
 */
public interface ProjectTransferDetailedMapper extends BaseMapper<ProjectTransferDetailed> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-17 18:27:56
   */
  List<ProjectTransferDetailed> findAll(IPage<ProjectTransferDetailed> page, @Param("request") ProjectTransferDetailedPageRequest request);

}