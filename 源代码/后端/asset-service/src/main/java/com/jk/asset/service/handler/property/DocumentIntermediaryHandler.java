package com.jk.asset.service.handler.property;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.property.DocumentIntermediaryMapper;
import com.jk.asset.model.entity.property.DocumentIntermediary;
import com.jk.asset.model.request.property.DocumentIntermediaryRequest;
import com.jk.common.enums.OperationTypeEnum;
import com.jk.common.utils.PlatformMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;

/**
 * 单据和中介关联表接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-07-12 16:11:38
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentIntermediaryHandler extends ServiceImpl<DocumentIntermediaryMapper, DocumentIntermediary> {


//  @Autowired
//  private IntermediaryCustomerLeadHandler intermediaryCustomerLeadHandler;

  public void batchAddOrUpdateDocumentIntermediary(List<DocumentIntermediaryRequest> documentIntermediaryRequestList, Long doId, String doType){

    if (ObjectUtils.isEmpty(documentIntermediaryRequestList)) {
      return;
    }

    for (DocumentIntermediaryRequest documentIntermediaryRequest : documentIntermediaryRequestList) {

      //  判断操作类型是否是需要删除的数据
      if (Objects.nonNull(documentIntermediaryRequest.getOperateType()) && documentIntermediaryRequest.getOperateType() == OperationTypeEnum.DELETE){
        this.removeById(documentIntermediaryRequest.getId());
        continue;
      }

      //  关联合同ID
      documentIntermediaryRequest.setDoId(doId);
      documentIntermediaryRequest.setDoType(doType);
      DocumentIntermediary documentIntermediary = PlatformMapUtils.getInstance().map(documentIntermediaryRequest, DocumentIntermediary.class);
      //  新增或更新
      this.saveOrUpdate(documentIntermediary);

//      //  处理客户与中介关系
//      intermediaryCustomerLeadHandler.batchAddOrUpdateIntermediaryCustomerLead(
//          documentIntermediaryRequest.getIntermediaryCustomerLeadRequestList(),
//          documentIntermediary.getId(),
//          BillTypeEnum.DOCUMENT_INTERMEDIARY.getKey()
//      );
    }

  }

}