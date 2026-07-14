package com.jk.asset.mapper.property;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.property.LeasePaymentCycle;
import com.jk.asset.model.request.page.property.LeasePaymentCyclePageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 租赁缴费周期表 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-13 09:46:33
 */
public interface LeasePaymentCycleMapper extends BaseMapper<LeasePaymentCycle> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-13 09:46:33
   */
  List<LeasePaymentCycle> findAll(IPage<LeasePaymentCycle> page, @Param("request") LeasePaymentCyclePageRequest request);

}