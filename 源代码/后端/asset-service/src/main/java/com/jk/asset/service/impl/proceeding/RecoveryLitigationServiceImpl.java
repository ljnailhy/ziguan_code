package com.jk.asset.service.impl.proceeding;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.enums.DictEnum;
import com.jk.asset.mapper.proceeding.RecoveryLitigationMapper;
import com.jk.asset.model.dto.proceeding.RecoveryLitigationDTO;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.entity.proceeding.RecoveryLitigation;
import com.jk.asset.model.request.page.proceeding.RecoveryLitigationPageRequest;
import com.jk.asset.model.request.proceeding.RecoveryLitigationRequest;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.proceeding.RecoveryLitigationHandler;
import com.jk.asset.service.proceeding.RecoveryLitigationService;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.dto.SysDictionaryDTO;
import com.jk.infrastructure.model.dto.SysDictionaryItemDTO;
import com.jk.service.client.SysDictionaryClient;
import com.jk.service.utils.PlatformFileUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 诉讼接口实现类
 *
 * @author wangshuai
 * @since 2024-07-01 16:38:08
 */
@RestController
@Slf4j
@Api(tags = "诉讼接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecoveryLitigationServiceImpl implements RecoveryLitigationService {

  private final RecoveryLitigationHandler handler;
  private final RecoveryLitigationMapper mapper;
  private final PlatformFileUtils platformFileUtils;
  private final ProjectInfoHandler projectInfoHandler;
  private final SysDictionaryClient dictionaryClient;

  @Override
  public Result<RecoveryLitigationDTO> add(RecoveryLitigationRequest request) {
    if (null == request || request.unverified()) {
      String message = "诉讼新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    RecoveryLitigation recoveryLitigation = PlatformMapUtils.getInstance().map(request, RecoveryLitigation.class);
    handler.saveOrUpdate(recoveryLitigation);


    //  通用附件新增
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),recoveryLitigation.getId(), BillTypeEnum.RECOVERY_LITIGATION.getKey());

    RecoveryLitigationDTO dto = new RecoveryLitigationDTO();
    dto.setId(recoveryLitigation.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<RecoveryLitigationDTO> update(RecoveryLitigationRequest request) {
    if (null == request ) {
      String message = "诉讼修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    RecoveryLitigation recoveryLitigation = PlatformMapUtils.getInstance().map(request, RecoveryLitigation.class);
    handler.saveOrUpdate(recoveryLitigation);

    //  通用附件新增
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),recoveryLitigation.getId(), BillTypeEnum.RECOVERY_LITIGATION.getKey());

    RecoveryLitigationDTO dto = new RecoveryLitigationDTO();
    dto.setId(recoveryLitigation.getId());
    return Result.success(dto);
  }

  @Override
  public Result<RecoveryLitigationDTO> submit(RecoveryLitigationRequest request) {
    request.setIsSubmit(Boolean.TRUE);
    Result<RecoveryLitigationDTO> updateResult = update(request);
    if (null == updateResult || !updateResult.succeedWithData()) {
      return Result.error("提交失败");
    }
    Long state = null;
    try {
      Result<SysDictionaryDTO> byCode = dictionaryClient.findByCode(DictEnum.PROJECT_STATE.getKey());
      if (byCode.getCode().equals(0)) {
        Optional<Long> first = byCode.getData().getItems()
            .stream()
            .filter(item -> DictEnum.PROJECT_STATE_03.getKey().equals(item.getItemCode()))
            .map(SysDictionaryItemDTO::getId)
            .findFirst();
        state = first.get();
      }
    }catch (Exception e){
      String message = "状态失败，请联系管理员";
      log.info("{} code {}", message, DictEnum.PROJECT_STATE_03.getKey());
      return Result.error(message);
    }
    //  修改项目状态
    projectInfoHandler.update(new LambdaUpdateWrapper<ProjectInfo>()
        .set(ProjectInfo::getProjectState, state)
        .eq(ProjectInfo::getId, request.getProjectId()));
    return Result.success();
  }

  @Override
  public Result<RecoveryLitigationDTO> findById(Long id) {
    RecoveryLitigation recoveryLitigation = handler.getById(id);
    if (null == recoveryLitigation) {
      String message = "诉讼单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    RecoveryLitigationDTO dto = PlatformMapUtils.getInstance().map(recoveryLitigation, RecoveryLitigationDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<RecoveryLitigationDTO> findByProjectId(Long projectId) {

    List<RecoveryLitigation> list = handler.list(new LambdaQueryWrapper<RecoveryLitigation>()
        .eq(RecoveryLitigation::getProjectId, projectId)
        .eq(RecoveryLitigation::getIsSubmit, Boolean.TRUE)
        .orderByDesc(RecoveryLitigation::getCreateStamp));

    if (ObjectUtils.isEmpty(list)){
      return Result.success();
    }

    // 对象拷贝：DO->DTO
    RecoveryLitigationDTO dto = PlatformMapUtils.getInstance().map(list.get(0), RecoveryLitigationDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<RecoveryLitigationDTO>> findAll(Integer current, Integer size, RecoveryLitigationPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<RecoveryLitigation> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<RecoveryLitigation> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<RecoveryLitigationDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, RecoveryLitigationDTO.class);

    List<Long> projectIds = dtoList.stream().map(item -> item.getProjectId()).collect(Collectors.toList());

    Map<Long, String> projectMap = projectInfoHandler.list(new LambdaUpdateWrapper<ProjectInfo>()
        .in(ProjectInfo::getId, projectIds)).stream().collect(Collectors.toMap(ProjectInfo::getId,ProjectInfo::getProjectName));

    for (RecoveryLitigationDTO recoveryLitigationDTO : dtoList) {
      recoveryLitigationDTO.setProjectName(projectMap.get(recoveryLitigationDTO.getProjectId()));
    }

    //  查询项目名称显示
    return Result.success(dtoList, PageFactory.page(page));
  }

}