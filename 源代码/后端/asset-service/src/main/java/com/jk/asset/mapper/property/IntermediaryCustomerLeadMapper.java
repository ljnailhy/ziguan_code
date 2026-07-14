package com.jk.asset.mapper.property;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.property.IntermediaryCustomerLead;
import com.jk.asset.model.request.page.property.IntermediaryCustomerLeadPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 中介客户线索关联表 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-12 16:12:05
 */
public interface IntermediaryCustomerLeadMapper extends BaseMapper<IntermediaryCustomerLead> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-12 16:12:05
   */
  List<IntermediaryCustomerLead> findAll(IPage<IntermediaryCustomerLead> page, @Param("request") IntermediaryCustomerLeadPageRequest request);

}