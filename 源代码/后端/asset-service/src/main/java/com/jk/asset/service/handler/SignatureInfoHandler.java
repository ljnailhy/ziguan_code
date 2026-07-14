package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.mapper.SignatureInfoMapper;
import com.jk.asset.model.entity.SignatureInfo;
import com.jk.asset.model.request.SignatureInfoRequest;
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
 * 签署方信息接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-06-20 17:49:00
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SignatureInfoHandler extends ServiceImpl<SignatureInfoMapper, SignatureInfo> {

  @Autowired
  private PlatformFileUtils platformFileUtils;

  /**
   * 批量新增更新签约方信息
   * @param signatureInfoList
   * @param contractId
   * @return java.util.List<com.jk.asset.model.entity.SignatureInfo>
   * @author wangshuai
   * @since 2024/6/20 18:03
   **/
  public void batchAddOrUpdateSignatureInfo(List<SignatureInfoRequest> signatureInfoList,Long contractId){

    if (ObjectUtils.isEmpty(signatureInfoList)) {
      return;
    }

    for (SignatureInfoRequest signatureInfoRequest : signatureInfoList) {

      //  判断操作类型是否是需要删除的数据
      if (Objects.nonNull(signatureInfoRequest.getOperateType()) && signatureInfoRequest.getOperateType() == OperationTypeEnum.DELETE){
        this.removeById(signatureInfoRequest.getId());
        //  删除附件信息
        platformFileUtils.deleteFileByDoId(signatureInfoRequest.getId(), BillTypeEnum.SIGNATURE_INFO.getKey());
        continue;
      }

      //  关联合同ID
      signatureInfoRequest.setContractId(contractId);
      SignatureInfo signatureInfo = PlatformMapUtils.getInstance().map(signatureInfoRequest, SignatureInfo.class);
      //  新增或更新
      this.saveOrUpdate(signatureInfo);

      //  处理附件信息
      platformFileUtils.batchUpdateFile(signatureInfoRequest.getFileInfoList(),signatureInfo.getId(),BillTypeEnum.SIGNATURE_INFO.getKey());
    }
  }

  /**
   * 通过合同id删除签约信息和签约信息附件
   * @param contractId
   * @return void
   * @author wangshuai
   * @since 2024/6/20 19:22
   **/
  public void deleteSignatureInfoByContractId(Long contractId){
    LambdaQueryWrapper<SignatureInfo> eq = new LambdaQueryWrapper<SignatureInfo>()
        .eq(SignatureInfo::getContractId, contractId);

    List<SignatureInfo> list = this.list(eq);
    //  删除附件信息
    if (!ObjectUtils.isEmpty(list)) {
      List<Long> delIds = list.stream().map(item -> item.getId()).collect(Collectors.toList());
      platformFileUtils.deleteFileByDoIds(delIds,BillTypeEnum.SIGNATURE_INFO.getKey());
    }
    //  删除签约方信息
    this.remove(eq);
  }

}