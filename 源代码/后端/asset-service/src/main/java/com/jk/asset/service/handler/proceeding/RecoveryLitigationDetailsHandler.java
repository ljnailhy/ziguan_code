package com.jk.asset.service.handler.proceeding;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.mapper.proceeding.RecoveryLitigationDetailsMapper;
import com.jk.asset.model.entity.proceeding.RecoveryLitigationDetails;
import com.jk.asset.model.request.proceeding.RecoveryLitigationDetailsRequest;
import com.jk.asset.service.handler.RevePropertyInfoHandler;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.service.utils.PlatformFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 终本信息 诉讼其他信息 撤诉信息 结案信息 保全信息接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-07-02 15:44:58
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecoveryLitigationDetailsHandler extends ServiceImpl<RecoveryLitigationDetailsMapper, RecoveryLitigationDetails> {


  @Autowired
  private PlatformFileUtils platformFileUtils;
  @Autowired
  private RevePropertyInfoHandler revePropertyInfoHandler;

  public RecoveryLitigationDetails addOrUpdate(RecoveryLitigationDetailsRequest request){

    // 对象拷贝：request->DO
    RecoveryLitigationDetails recoveryLitigationDetails = PlatformMapUtils.getInstance().map(request, RecoveryLitigationDetails.class);
    this.saveOrUpdate(recoveryLitigationDetails);

    //  保全处理反担保措施和其他财产信息
    if (BillTypeEnum.PRESERVATION.getKey().equals(request.getLitigationType().getKey())) {
      //  处理反担保信息
      revePropertyInfoHandler.batchAddOrUpdateAllocationDetail(
          request.getReveInfoRequest(),
          BillTypeEnum.REVE.getKey(),
          recoveryLitigationDetails.getId(),
          request.getLitigationType().getKey()
      );
      //  处理其他财产信息
      revePropertyInfoHandler.batchAddOrUpdateAllocationDetail(
          request.getPropertyInfoRequest(),
          BillTypeEnum.PROPERTY.getKey(),
          recoveryLitigationDetails.getId(),
          request.getLitigationType().getKey()
      );
    }

    //  处理附件信息
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),recoveryLitigationDetails.getId(),request.getLitigationType().getKey());
    return recoveryLitigationDetails;
  }

}