package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.dto.PayFeeDTO;
import com.jk.asset.model.entity.RecoveryPayment;
import com.jk.asset.model.request.page.RecoveryPaymentPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 付款表 Mapper 接口
 *
 * @author wangtao
 * @since 2024-07-08 11:35:36
 */
public interface RecoveryPaymentMapper extends BaseMapper<RecoveryPayment> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangtao
   * @since 2024-07-08 11:35:36
   */
  List<RecoveryPayment> findAll(IPage<RecoveryPayment> page, @Param("request") RecoveryPaymentPageRequest request);

    List<PayFeeDTO> findFeeByLawFirmId(@Param("status")Long status, @Param("lawyerIds") List<Long> collect1,@Param("payType") Long payType);
}