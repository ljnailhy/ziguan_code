package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.RecoveryPaymentCollection;
import com.jk.asset.model.request.page.RecoveryPaymentCollectionPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 回款表 Mapper 接口
 *
 * @author wangtao
 * @since 2024-06-20 18:53:02
 */
public interface RecoveryPaymentCollectionMapper extends BaseMapper<RecoveryPaymentCollection> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangtao
   * @since 2024-06-20 18:53:02
   */
  List<RecoveryPaymentCollection> findAll(IPage<RecoveryPaymentCollection> page, @Param("request") RecoveryPaymentCollectionPageRequest request);

}