package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.LawFirmInfo;
import com.jk.asset.model.request.page.LawFirmInfoPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 律所信息 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-06-24 11:09:30
 */
public interface LawFirmInfoMapper extends BaseMapper<LawFirmInfo> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-06-24 11:09:30
   */
  List<LawFirmInfo> findAll(IPage<LawFirmInfo> page, @Param("request") LawFirmInfoPageRequest request);

}