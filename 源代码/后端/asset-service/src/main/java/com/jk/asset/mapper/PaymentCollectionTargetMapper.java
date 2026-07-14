package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.PaymentCollectionTarget;
import com.jk.asset.model.request.page.PaymentCollectionTargetPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 年度回款目标 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-06-25 10:24:50
 */
public interface PaymentCollectionTargetMapper extends BaseMapper<PaymentCollectionTarget> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-06-25 10:24:50
   */
  List<PaymentCollectionTarget> findAll(IPage<PaymentCollectionTarget> page, @Param("request") PaymentCollectionTargetPageRequest request);

}