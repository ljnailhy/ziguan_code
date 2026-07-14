package com.jk.asset.mapper.proceeding;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.proceeding.RecoveryJudgement;
import com.jk.asset.model.request.page.proceeding.RecoveryJudgementPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 审判信息（立案一审二审再审） Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-02 09:56:48
 */
public interface RecoveryJudgementMapper extends BaseMapper<RecoveryJudgement> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-02 09:56:48
   */
  List<RecoveryJudgement> findAll(IPage<RecoveryJudgement> page, @Param("request") RecoveryJudgementPageRequest request);

}