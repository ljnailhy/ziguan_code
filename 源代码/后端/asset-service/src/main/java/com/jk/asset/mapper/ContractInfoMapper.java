package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.dto.LawFirmAgreementDTO;
import com.jk.asset.model.entity.ContractInfo;
import com.jk.asset.model.request.page.ContractInfoPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 合同信息 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-06-20 17:46:10
 */
public interface ContractInfoMapper extends BaseMapper<ContractInfo> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-06-20 17:46:10
   */
  List<ContractInfo> findAll(IPage<ContractInfo> page, @Param("request") ContractInfoPageRequest request);

    List<LawFirmAgreementDTO> lawFirmAgreementEndAll();
}