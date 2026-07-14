package com.jk.asset.mapper.property;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.property.OperationInfo;
import com.jk.asset.model.request.page.property.OperationInfoPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 运营信息表 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-13 09:53:55
 */
public interface OperationInfoMapper extends BaseMapper<OperationInfo> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-13 09:53:55
   */
  List<OperationInfo> findAll(IPage<OperationInfo> page, @Param("request") OperationInfoPageRequest request);

}