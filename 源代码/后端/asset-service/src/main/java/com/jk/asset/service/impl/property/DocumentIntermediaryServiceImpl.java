package com.jk.asset.service.impl.property;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.model.dto.property.PropertyBillDTO;
import com.jk.asset.model.entity.property.PropertyBill;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.asset.mapper.property.DocumentIntermediaryMapper;
import com.jk.asset.model.dto.property.DocumentIntermediaryDTO;
import com.jk.asset.model.entity.property.DocumentIntermediary;
import com.jk.asset.model.request.property.DocumentIntermediaryRequest;
import com.jk.asset.model.request.page.property.DocumentIntermediaryPageRequest;
import com.jk.asset.service.property.DocumentIntermediaryService;
import com.jk.asset.service.handler.property.DocumentIntermediaryHandler;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 单据和中介关联表接口实现类
 *
 * @author wangshuai
 * @since 2024-07-12 16:11:38
 */
@RestController
@Slf4j
@Api(tags = "单据和中介关联表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentIntermediaryServiceImpl implements DocumentIntermediaryService {

  private final DocumentIntermediaryHandler handler;
  private final DocumentIntermediaryMapper mapper;

  @Override
  public Result<DocumentIntermediaryDTO> add(DocumentIntermediaryRequest request) {
    if (null == request || request.unverified()) {
      String message = "单据和中介关联表新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    DocumentIntermediary documentIntermediary = PlatformMapUtils.getInstance().map(request, DocumentIntermediary.class);
    handler.save(documentIntermediary);

    DocumentIntermediaryDTO dto = new DocumentIntermediaryDTO();
    dto.setId(documentIntermediary.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<DocumentIntermediaryDTO> update(DocumentIntermediaryRequest request) {
    if (null == request || null == request.getId()) {
      String message = "单据和中介关联表修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    DocumentIntermediary documentIntermediary = PlatformMapUtils.getInstance().map(request, DocumentIntermediary.class);
    handler.updateById(documentIntermediary);

    return Result.success();
  }

  @Override
  public Result<DocumentIntermediaryDTO> findById(Long id) {
    DocumentIntermediary documentIntermediary = handler.getById(id);
    if (null == documentIntermediary) {
      String message = "单据和中介关联表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    DocumentIntermediaryDTO dto = PlatformMapUtils.getInstance().map(documentIntermediary, DocumentIntermediaryDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<DocumentIntermediaryDTO>> findDocumentIntermediaryByDoId(Long doId, String doType) {
    List<DocumentIntermediary> list = handler.list(new LambdaUpdateWrapper<DocumentIntermediary>()
        .eq(DocumentIntermediary::getDoId, doId)
        .eq(DocumentIntermediary::getDoType, doType));

    if (ObjectUtils.isEmpty(list)){
      return Result.success(Lists.newArrayList());
    }

    List<DocumentIntermediaryDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, DocumentIntermediaryDTO.class);

    return Result.success(dtoList);
  }

  @Override
  public Result<List<DocumentIntermediaryDTO>> findAll(Integer current, Integer size, DocumentIntermediaryPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<DocumentIntermediary> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<DocumentIntermediary> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<DocumentIntermediaryDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, DocumentIntermediaryDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}