package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.RecoveryPaymentDetail;
import com.jk.asset.model.request.page.RecoveryPaymentDetailPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 付款明细 Mapper 接口
 *
 * @author wangtao
 * @since 2024-07-08 11:35:36
 */
public interface RecoveryPaymentDetailMapper extends BaseMapper<RecoveryPaymentDetail> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangtao
   * @since 2024-07-08 11:35:36
   */
  List<RecoveryPaymentDetail> findAll(IPage<RecoveryPaymentDetail> page, @Param("request") RecoveryPaymentDetailPageRequest request);

}