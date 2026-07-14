package com.jk.asset.mapper.proceeding;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.proceeding.RecoveryLitigationDetails;
import com.jk.asset.model.request.page.proceeding.RecoveryLitigationDetailsPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-02 15:44:58
 */
public interface RecoveryLitigationDetailsMapper extends BaseMapper<RecoveryLitigationDetails> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-02 15:44:58
   */
  List<RecoveryLitigationDetails> findAll(IPage<RecoveryLitigationDetails> page, @Param("request") RecoveryLitigationDetailsPageRequest request);

}