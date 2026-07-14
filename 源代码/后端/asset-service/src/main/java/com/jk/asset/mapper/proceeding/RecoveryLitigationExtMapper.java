package com.jk.asset.mapper.proceeding;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.proceeding.RecoveryLitigationExt;
import com.jk.asset.model.request.page.proceeding.RecoveryLitigationExtPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 诉讼反写信息扩展表 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-04 10:35:25
 */
public interface RecoveryLitigationExtMapper extends BaseMapper<RecoveryLitigationExt> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-04 10:35:25
   */
  List<RecoveryLitigationExt> findAll(IPage<RecoveryLitigationExt> page, @Param("request") RecoveryLitigationExtPageRequest request);

}