package com.jk.asset.service.impl.proceeding;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.asset.mapper.proceeding.RecoveryLitigationExtMapper;
import com.jk.asset.model.dto.proceeding.RecoveryLitigationExtDTO;
import com.jk.asset.model.entity.proceeding.RecoveryLitigationExt;
import com.jk.asset.model.request.proceeding.RecoveryLitigationExtRequest;
import com.jk.asset.model.request.page.proceeding.RecoveryLitigationExtPageRequest;
import com.jk.asset.service.proceeding.RecoveryLitigationExtService;
import com.jk.asset.service.handler.proceeding.RecoveryLitigationExtHandler;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 诉讼反写信息扩展表接口实现类
 *
 * @author wangshuai
 * @since 2024-07-04 10:35:25
 */
@RestController
@Slf4j
@Api(tags = "诉讼反写信息扩展表接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecoveryLitigationExtServiceImpl implements RecoveryLitigationExtService {

  private final RecoveryLitigationExtHandler handler;
  private final RecoveryLitigationExtMapper mapper;

  @Override
  public Result<RecoveryLitigationExtDTO> add(RecoveryLitigationExtRequest request) {
    if (null == request || request.unverified()) {
      String message = "诉讼反写信息扩展表新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    RecoveryLitigationExt recoveryLitigationExt = PlatformMapUtils.getInstance().map(request, RecoveryLitigationExt.class);
    handler.save(recoveryLitigationExt);

    RecoveryLitigationExtDTO dto = new RecoveryLitigationExtDTO();
    dto.setId(recoveryLitigationExt.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<RecoveryLitigationExtDTO> update(RecoveryLitigationExtRequest request) {
    if (null == request || null == request.getId()) {
      String message = "诉讼反写信息扩展表修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    RecoveryLitigationExt recoveryLitigationExt = PlatformMapUtils.getInstance().map(request, RecoveryLitigationExt.class);
    handler.updateById(recoveryLitigationExt);

    return Result.success();
  }

  @Override
  public Result<RecoveryLitigationExtDTO> findById(Long id) {
    RecoveryLitigationExt recoveryLitigationExt = handler.getById(id);
    if (null == recoveryLitigationExt) {
      String message = "诉讼反写信息扩展表单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    RecoveryLitigationExtDTO dto = PlatformMapUtils.getInstance().map(recoveryLitigationExt, RecoveryLitigationExtDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<RecoveryLitigationExtDTO>> findAll(Integer current, Integer size, RecoveryLitigationExtPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<RecoveryLitigationExt> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<RecoveryLitigationExt> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<RecoveryLitigationExtDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, RecoveryLitigationExtDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}