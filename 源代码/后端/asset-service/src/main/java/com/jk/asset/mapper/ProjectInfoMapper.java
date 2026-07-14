package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.dto.PaymentCollectionPageDTO;
import com.jk.asset.model.dto.ProjectInfoDTO;
import com.jk.asset.model.dto.ProjectLedgerDTO;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.request.page.PaymentCollectionLedgerPageRequest;
import com.jk.asset.model.request.page.ProjectInfoPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目信息表 Mapper 接口
 *
 * @author wangtao
 * @since 2024-06-24 09:41:55
 */
public interface ProjectInfoMapper extends BaseMapper<ProjectInfo> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangtao
   * @since 2024-06-24 09:41:55
   */
  List<ProjectInfoDTO> findAll(IPage<ProjectInfoDTO> page, @Param("request") ProjectInfoPageRequest request);

  ProjectInfoDTO findInfoById(@Param("id") Long id);

  List<PaymentCollectionPageDTO> findPaymentCollectionAll(IPage<PaymentCollectionPageDTO> page ,@Param("request") PaymentCollectionLedgerPageRequest request);

  List<ProjectLedgerDTO> findProjectLedgerAll(IPage<ProjectLedgerDTO> page,@Param("request") ProjectInfoPageRequest request);

  List<ProjectLedgerDTO> findLawyerInfo(@Param("projectIds") List<Long> projectIds);

  List<ProjectInfoDTO> findCompensatoryCountByYear();
}