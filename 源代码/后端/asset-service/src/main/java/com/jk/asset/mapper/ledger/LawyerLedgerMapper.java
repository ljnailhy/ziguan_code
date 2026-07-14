package com.jk.asset.mapper.ledger;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.dto.LawyerLedgerDTO;
import com.jk.asset.model.request.page.LawFirmInfoPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 律所台账 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
public interface LawyerLedgerMapper {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-06-28 10:13:53
   */
  List<LawyerLedgerDTO> findAll(IPage<LawyerLedgerDTO> page, @Param("request") LawFirmInfoPageRequest request);

}