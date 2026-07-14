package com.jk.asset.mapper.property;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.dto.OperationInfoRemindDTO;
import com.jk.asset.model.entity.property.PropertyBill;
import com.jk.asset.model.request.page.property.PropertyBillPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资产和单据关联表 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-11 18:03:58
 */
public interface PropertyBillMapper extends BaseMapper<PropertyBill> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-11 18:03:58
   */
  List<PropertyBill> findAll(IPage<PropertyBill> page, @Param("request") PropertyBillPageRequest request);

  List<OperationInfoRemindDTO> findOperation();
}