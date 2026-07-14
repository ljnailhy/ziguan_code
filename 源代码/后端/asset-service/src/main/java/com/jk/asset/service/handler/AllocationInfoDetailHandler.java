package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.mapper.AllocationInfoDetailMapper;
import com.jk.asset.model.entity.AllocationInfoDetail;
import com.jk.asset.model.entity.BillLawyer;
import com.jk.asset.model.entity.LawyerInfo;
import com.jk.asset.model.request.AllocationInfoDetailRequest;
import com.jk.asset.model.request.LawyerInfoRequest;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.service.utils.PlatformFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 项目分配/变更明细接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AllocationInfoDetailHandler extends ServiceImpl<AllocationInfoDetailMapper, AllocationInfoDetail> {

  @Autowired
  private PlatformFileUtils platformFileUtils;
  @Autowired
  private BillLawyerHandler billLawyerHandler;

  public void batchAddOrUpdateAllocationDetail(List<AllocationInfoDetailRequest> allocationInfoDetailRequestList, Long allocationId){

    if (ObjectUtils.isEmpty(allocationInfoDetailRequestList)) {
      return;
    }

    for (AllocationInfoDetailRequest allocationInfoDetailRequest : allocationInfoDetailRequestList) {

      //  判断操作类型是否是需要删除的数据
      if (Objects.nonNull(allocationInfoDetailRequest.getOperateType()) && allocationInfoDetailRequest.getOperateType() == OperationTypeEnum.DELETE){
        this.removeById(allocationInfoDetailRequest.getId());
        //  删除附件信息
        platformFileUtils.deleteFileByDoId(allocationInfoDetailRequest.getId(), BillTypeEnum.LAWYER_INFO.getKey());
        continue;
      }

      //  关联合同ID
      allocationInfoDetailRequest.setAllocationId(allocationId);
      AllocationInfoDetail allocationInfoDetail = PlatformMapUtils.getInstance().map(allocationInfoDetailRequest, AllocationInfoDetail.class);
      //  新增或更新
      this.saveOrUpdate(allocationInfoDetail);

      //  处理律师
//      billLawyerHandler.remove(new LambdaUpdateWrapper<BillLawyer>()
//          .eq(BillLawyer::getDoId,allocationInfoDetail.getId())
//          .eq(BillLawyer::getDoType,BillTypeEnum.ALLOCATION_INFO_DETAIL.getKey()));

      if (StringUtils.isNotBlank(allocationInfoDetailRequest.getLawyers())) {
        String[] split = allocationInfoDetailRequest.getLawyers().split(",");
        List<Long> longList = Arrays.stream(split)
            .map(Long::parseLong)
            .collect(Collectors.toList());
        billLawyerHandler.batchAddBillLawyer(allocationInfoDetail.getId(),BillTypeEnum.ALLOCATION_INFO_DETAIL.getKey(),longList);
//        for (String lawyerId : split) {
//          BillLawyer billLawyer = new BillLawyer()
//              .setDoId(allocationInfoDetail.getId())
//              .setDoType(BillTypeEnum.ALLOCATION_INFO_DETAIL.getKey())
//              .setLawyer(Long.valueOf(lawyerId));
//        }
      }

      //  处理附件信息
      platformFileUtils.batchUpdateFile(allocationInfoDetailRequest.getFileInfoList(),allocationInfoDetail.getId(), BillTypeEnum.ALLOCATION_INFO_DETAIL.getKey());
    }
  }

}