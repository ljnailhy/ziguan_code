package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.mapper.ProjectTransferDetailedMapper;
import com.jk.asset.model.entity.ProjectTransferDetailed;
import com.jk.asset.model.entity.property.DocumentIntermediary;
import com.jk.asset.model.request.ProjectTransferDetailedRequest;
import com.jk.asset.model.request.property.DocumentIntermediaryRequest;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.utils.PlatformMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 * 项目移交明细接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-07-17 18:27:56
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectTransferDetailedHandler extends ServiceImpl<ProjectTransferDetailedMapper, ProjectTransferDetailed> {


  public void batchAddOrUpdateProjectTransferDetailed(List<ProjectTransferDetailedRequest> projectTransferDetailedRequestList, Long transferId){

    if (ObjectUtils.isEmpty(projectTransferDetailedRequestList)) {
      return;
    }

    for (ProjectTransferDetailedRequest projectTransferDetailedRequest : projectTransferDetailedRequestList) {

      //  判断操作类型是否是需要删除的数据
      if (Objects.nonNull(projectTransferDetailedRequest.getOperateType()) && projectTransferDetailedRequest.getOperateType() == OperationTypeEnum.DELETE){
        this.removeById(projectTransferDetailedRequest.getId());
        continue;
      }

      //  关联合同ID
      projectTransferDetailedRequest.setTransferId(transferId);
      ProjectTransferDetailed projectTransferDetailed = PlatformMapUtils.getInstance().map(projectTransferDetailedRequest, ProjectTransferDetailed.class);
      //  新增或更新
      this.saveOrUpdate(projectTransferDetailed);
    }

  }

}