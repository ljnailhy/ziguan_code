package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.mapper.LawyerInfoMapper;
import com.jk.asset.model.entity.LawyerInfo;
import com.jk.asset.model.request.LawyerInfoRequest;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.service.utils.PlatformFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 律师团队接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-06-24 11:09:49
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LawyerInfoHandler extends ServiceImpl<LawyerInfoMapper, LawyerInfo> {

  @Autowired
  private PlatformFileUtils platformFileUtils;

  /**
   * 批量新增更新签约方信息
   * @param lawyerInfoRequestList 律师团队
   * @param lawFirmId 律师id
   * @return java.util.List<com.jk.asset.model.entity.SignatureInfo>
   * @author wangshuai
   * @since 2024/6/20 18:03
   **/
  public void batchAddOrUpdateLawyerInfo(List<LawyerInfoRequest> lawyerInfoRequestList, Long lawFirmId){

    if (ObjectUtils.isEmpty(lawyerInfoRequestList)) {
      return;
    }

    for (LawyerInfoRequest lawyerInfoRequest : lawyerInfoRequestList) {

      //  判断操作类型是否是需要删除的数据
      if (Objects.nonNull(lawyerInfoRequest.getOperateType()) && lawyerInfoRequest.getOperateType() == OperationTypeEnum.DELETE){
        this.removeById(lawyerInfoRequest.getId());
        //  删除附件信息
        platformFileUtils.deleteFileByDoId(lawyerInfoRequest.getId(), BillTypeEnum.LAWYER_INFO.getKey());
        continue;
      }

      //  关联合同ID
      lawyerInfoRequest.setLawFirmId(lawFirmId);
      LawyerInfo lawyerInfo = PlatformMapUtils.getInstance().map(lawyerInfoRequest, LawyerInfo.class);
      //  新增或更新
      this.saveOrUpdate(lawyerInfo);

      //  处理附件信息
      platformFileUtils.batchUpdateFile(lawyerInfoRequest.getFileInfoList(),lawyerInfo.getId(), BillTypeEnum.LAWYER_INFO.getKey());
    }
  }

  /**
   * 通过合同id删除签约信息和签约信息附件
   * @param lawFirmId 律所id
   * @return void
   * @author wangshuai
   * @since 2024/6/20 19:22
   **/
  public void deleteLawyerInfoByContractId(Long lawFirmId){
    LambdaQueryWrapper<LawyerInfo> eq = new LambdaQueryWrapper<LawyerInfo>()
        .eq(LawyerInfo::getLawFirmId, lawFirmId);

    List<LawyerInfo> list = this.list(eq);
    //  删除附件信息
    if (!ObjectUtils.isEmpty(list)) {
      List<Long> delIds = list.stream().map(item -> item.getId()).collect(Collectors.toList());
      platformFileUtils.deleteFileByDoIds(delIds,BillTypeEnum.LAWYER_INFO.getKey());
    }
    //  删除签约方信息
    this.remove(eq);
  }

}