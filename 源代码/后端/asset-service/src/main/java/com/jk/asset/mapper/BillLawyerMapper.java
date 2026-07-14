package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.BillLawyer;
import com.jk.asset.model.request.page.BillLawyerPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 单据与律师关系表 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-06-28 11:49:45
 */
public interface BillLawyerMapper extends BaseMapper<BillLawyer> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-06-28 11:49:45
   */
  List<BillLawyer> findAll(IPage<BillLawyer> page, @Param("request") BillLawyerPageRequest request);

}