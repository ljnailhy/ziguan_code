package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.LawyerInfo;
import com.jk.asset.model.request.page.LawyerInfoPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 律师团队 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-06-24 11:09:49
 */
public interface LawyerInfoMapper extends BaseMapper<LawyerInfo> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-06-24 11:09:49
   */
  List<LawyerInfo> findAll(IPage<LawyerInfo> page, @Param("request") LawyerInfoPageRequest request);

}