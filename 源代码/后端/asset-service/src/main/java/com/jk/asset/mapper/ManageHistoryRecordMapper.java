package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.ManageHistoryRecord;
import com.jk.asset.model.request.page.ManageHistoryRecordPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 保全经理历史变更记录 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-24 14:24:35
 */
public interface ManageHistoryRecordMapper extends BaseMapper<ManageHistoryRecord> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-24 14:24:35
   */
  List<ManageHistoryRecord> findAll(IPage<ManageHistoryRecord> page, @Param("request") ManageHistoryRecordPageRequest request);

}