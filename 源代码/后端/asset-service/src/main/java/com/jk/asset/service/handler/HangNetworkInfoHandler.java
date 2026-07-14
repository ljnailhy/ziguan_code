package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.HangNetworkInfoMapper;
import com.jk.asset.model.entity.HangNetworkInfo;
import com.jk.asset.model.entity.RevePropertyInfo;
import com.jk.asset.model.request.HangNetworkInfoRequest;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.utils.PlatformMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 * 挂网信息表接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-07-09 15:15:21
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HangNetworkInfoHandler extends ServiceImpl<HangNetworkInfoMapper, HangNetworkInfo> {

  public void batchAddOrUpdateHangNetworkInfo(List<HangNetworkInfoRequest> hangNetworkInfoRequestList,Long doId,String doType){

    if (ObjectUtils.isEmpty(hangNetworkInfoRequestList)) {
      return;
    }

    for (HangNetworkInfoRequest hangNetworkInfoRequest : hangNetworkInfoRequestList) {

      if (Objects.nonNull(hangNetworkInfoRequest.getOperateType()) && hangNetworkInfoRequest.getOperateType() == OperationTypeEnum.DELETE){
        this.removeById(hangNetworkInfoRequest.getId());
        continue;
      }

      hangNetworkInfoRequest.setDoId(doId);
      hangNetworkInfoRequest.setDoType(doType);
      HangNetworkInfo hangNetworkInfo = PlatformMapUtils.getInstance().map(hangNetworkInfoRequest, HangNetworkInfo.class);
      this.saveOrUpdate(hangNetworkInfo);
    }
  }

}