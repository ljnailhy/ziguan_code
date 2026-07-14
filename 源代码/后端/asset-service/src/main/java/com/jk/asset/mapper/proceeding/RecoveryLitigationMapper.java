package com.jk.asset.mapper.proceeding;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.proceeding.RecoveryLitigation;
import com.jk.asset.model.request.page.proceeding.RecoveryLitigationPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 诉讼 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-01 16:38:08
 */
public interface RecoveryLitigationMapper extends BaseMapper<RecoveryLitigation> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-01 16:38:08
   */
  List<RecoveryLitigation> findAll(IPage<RecoveryLitigation> page, @Param("request") RecoveryLitigationPageRequest request);

}