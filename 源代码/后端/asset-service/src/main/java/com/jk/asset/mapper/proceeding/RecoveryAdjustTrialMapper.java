package com.jk.asset.mapper.proceeding;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.proceeding.RecoveryAdjustTrial;
import com.jk.asset.model.request.page.proceeding.RecoveryAdjustTrialPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 调解或审判信息	 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-03 10:13:14
 */
public interface RecoveryAdjustTrialMapper extends BaseMapper<RecoveryAdjustTrial> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-03 10:13:14
   */
  List<RecoveryAdjustTrial> findAll(IPage<RecoveryAdjustTrial> page, @Param("request") RecoveryAdjustTrialPageRequest request);

}