package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.BillLawyerMapper;
import com.jk.asset.model.dto.BillLawyerDTO;
import com.jk.asset.model.entity.BillLawyer;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.request.AllocationInfoDetailRequest;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.google.common.collect.Lists;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 单据与律师关系表接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-06-28 11:49:45
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BillLawyerHandler extends ServiceImpl<BillLawyerMapper, BillLawyer> {

  public void batchAddBillLawyer(Long doId, String doType, List<Long> idList){
    //  更新律师
    //  判断新律师和老律师是否有重复的，如果有重复的重复的不动
    List<Long> list = this.list(new LambdaUpdateWrapper<BillLawyer>()
        .eq(BillLawyer::getDoId, doId)
        .eq(BillLawyer::getIsEffective, Boolean.TRUE)
        .eq(BillLawyer::getDoType, doType))
        .stream()
        .map(item -> item.getLawyer())
        .collect(Collectors.toList());
    List<Long> duplicates = list.stream()
        .filter(idList::contains)
        .collect(Collectors.toList());

    LambdaUpdateWrapper<BillLawyer> eq = new LambdaUpdateWrapper<BillLawyer>()
        .set(BillLawyer::getIsEffective, Boolean.FALSE)
        .set(BillLawyer::getEndDate, new Date())
        .eq(BillLawyer::getDoId, doId)
        .eq(BillLawyer::getDoType, doType)
        .eq(BillLawyer::getIsEffective, Boolean.TRUE);
    if (!ObjectUtils.isEmpty(duplicates)){
      eq.notIn(BillLawyer::getLawyer, duplicates);
    }

    this.update(eq);

    List<BillLawyer> billLawyerList = Lists.newArrayList();
    for (Long lawyerId : idList) {
      if (list.contains(lawyerId)) {
        continue;
      }
      BillLawyer billLawyer = new BillLawyer()
          .setDoId(doId)
          .setDoType(doType)
          .setIsEffective(Boolean.TRUE)
          .setLawyer(lawyerId);
      billLawyerList.add(billLawyer);
    }
    this.saveBatch(billLawyerList);
  }

}