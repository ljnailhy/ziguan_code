package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.RecoveryPaymentCollectionDetail;
import com.jk.asset.model.request.page.RecoveryPaymentCollectionDetailPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 回款明细 Mapper 接口
 *
 * @author wangtao
 * @since 2024-06-20 18:53:02
 */
public interface RecoveryPaymentCollectionDetailMapper extends BaseMapper<RecoveryPaymentCollectionDetail> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangtao
   * @since 2024-06-20 18:53:02
   */
  List<RecoveryPaymentCollectionDetail> findAll(IPage<RecoveryPaymentCollectionDetail> page, @Param("request") RecoveryPaymentCollectionDetailPageRequest request);

}