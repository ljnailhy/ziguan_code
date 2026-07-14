package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.PaymentCollectionTargetDetail;
import com.jk.asset.model.request.page.PaymentCollectionTargetDetailPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 回款目标明细 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-06-25 10:24:51
 */
public interface PaymentCollectionTargetDetailMapper extends BaseMapper<PaymentCollectionTargetDetail> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-06-25 10:24:51
   */
  List<PaymentCollectionTargetDetail> findAll(IPage<PaymentCollectionTargetDetail> page, @Param("request") PaymentCollectionTargetDetailPageRequest request);

}