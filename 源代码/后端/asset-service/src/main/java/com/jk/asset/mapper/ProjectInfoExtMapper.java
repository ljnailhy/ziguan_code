package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.dto.ProjectInfoExtDTO;
import com.jk.asset.model.entity.ProjectInfoExt;
import com.jk.asset.model.request.page.ProjectInfoExtPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 项目信息详细信息扩展表 Mapper 接口
 *
 * @author wangtao
 * @since 2024-06-24 09:41:55
 */
public interface ProjectInfoExtMapper extends BaseMapper<ProjectInfoExt> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangtao
   * @since 2024-06-24 09:41:55
   */
  List<ProjectInfoExt> findAll(IPage<ProjectInfoExt> page, @Param("request") ProjectInfoExtPageRequest request);

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangtao
   * @since 2024-06-24 09:41:55
   */
  List<ProjectInfoExtDTO> litigationLedger(IPage<ProjectInfoExt> page, @Param("request") ProjectInfoExtPageRequest request);

}