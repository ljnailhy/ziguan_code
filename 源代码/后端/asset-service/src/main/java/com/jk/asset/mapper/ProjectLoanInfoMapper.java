package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.ProjectLoanInfo;
import com.jk.asset.model.request.page.ProjectLoanInfoPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 借款信息表 Mapper 接口
 *
 * @author wangtao
 * @since 2024-07-26 18:51:24
 */
public interface ProjectLoanInfoMapper extends BaseMapper<ProjectLoanInfo> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangtao
   * @since 2024-07-26 18:51:24
   */
  List<ProjectLoanInfo> findAll(IPage<ProjectLoanInfo> page, @Param("request") ProjectLoanInfoPageRequest request);

}