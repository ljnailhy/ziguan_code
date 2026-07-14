package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.RevePropertyInfoMapper;
import com.jk.asset.model.entity.HangNetworkInfo;
import com.jk.asset.model.entity.RevePropertyInfo;
import com.jk.asset.model.request.RevePropertyInfoRequest;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.service.utils.PlatformFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 * 反担保/其他财产线索信息接口实现相关处理
 *
 * @author wangtao
 * @since 2024-06-24 09:42:39
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RevePropertyInfoHandler extends ServiceImpl<RevePropertyInfoMapper, RevePropertyInfo> {

  @Autowired
  private PlatformFileUtils platformFileUtils;
  @Autowired
  private HangNetworkInfoHandler hangNetworkInfoHandler;

  public void batchAddOrUpdateAllocationDetail(List<RevePropertyInfoRequest> revePropertyInfoRequests,String billType, Long doId,String doType){

    if (ObjectUtils.isEmpty(revePropertyInfoRequests)) {
      return;
    }

    for (RevePropertyInfoRequest revePropertyInfoRequest : revePropertyInfoRequests) {

      //  判断操作类型是否是需要删除的数据
      if (Objects.nonNull(revePropertyInfoRequest.getOperateType()) && revePropertyInfoRequest.getOperateType() == OperationTypeEnum.DELETE){
        this.removeById(revePropertyInfoRequest.getId());
//        //  删除附件信息
//        platformFileUtils.deleteFileByDoId(revePropertyInfoRequest.getId(), billType);
        //  删除法拍过程
        hangNetworkInfoHandler.remove(new LambdaUpdateWrapper<HangNetworkInfo>()
            .eq(HangNetworkInfo::getDoId,revePropertyInfoRequest.getId())
            .eq(HangNetworkInfo::getDoType,billType));
        continue;
      }

      //  关联合同ID
      revePropertyInfoRequest.setBillType(billType);
      revePropertyInfoRequest.setDoId(doId);
      revePropertyInfoRequest.setDoType(doType);
      RevePropertyInfo revePropertyInfo = PlatformMapUtils.getInstance().map(revePropertyInfoRequest, RevePropertyInfo.class);
      //  新增或更新
      this.saveOrUpdate(revePropertyInfo);

      //  处理附件信息
      platformFileUtils.batchUpdateFile(revePropertyInfoRequest.getFileInfoList(),revePropertyInfo.getId(), billType);

      //  处理法拍过程
      hangNetworkInfoHandler.batchAddOrUpdateHangNetworkInfo(revePropertyInfoRequest.getHangNetworkInfoRequestList(),revePropertyInfo.getId(),billType);
    }
  }

}