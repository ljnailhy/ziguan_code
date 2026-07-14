package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.ProjectTransfer;
import com.jk.asset.model.request.page.ProjectTransferPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 项目移交 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-17 18:27:55
 */
public interface ProjectTransferMapper extends BaseMapper<ProjectTransfer> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-17 18:27:55
   */
  List<ProjectTransfer> findAll(IPage<ProjectTransfer> page, @Param("request") ProjectTransferPageRequest request);

}