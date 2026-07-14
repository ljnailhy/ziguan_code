package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.CustomerInfo;
import com.jk.asset.model.request.page.CustomerInfoPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 客户信息表 Mapper 接口
 *
 * @author wangtao
 * @since 2024-06-20 11:38:52
 */
public interface CustomerInfoMapper extends BaseMapper<CustomerInfo> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangtao
   * @since 2024-06-20 11:38:52
   */
  List<CustomerInfo> findAll(IPage<CustomerInfo> page, @Param("request") CustomerInfoPageRequest request);

}