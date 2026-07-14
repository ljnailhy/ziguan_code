package com.jk.asset.mapper.proceeding;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.proceeding.RecoveryExecute;
import com.jk.asset.model.request.page.proceeding.RecoveryExecutePageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 执行信息	 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-05 09:52:54
 */
public interface RecoveryExecuteMapper extends BaseMapper<RecoveryExecute> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-05 09:52:54
   */
  List<RecoveryExecute> findAll(IPage<RecoveryExecute> page, @Param("request") RecoveryExecutePageRequest request);

}