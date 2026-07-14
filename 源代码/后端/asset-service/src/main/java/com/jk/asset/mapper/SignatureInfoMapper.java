package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.SignatureInfo;
import com.jk.asset.model.request.page.SignatureInfoPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 签署方信息 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-06-20 17:49:00
 */
public interface SignatureInfoMapper extends BaseMapper<SignatureInfo> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-06-20 17:49:00
   */
  List<SignatureInfo> findAll(IPage<SignatureInfo> page, @Param("request") SignatureInfoPageRequest request);

}