package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.mapper.ManageHistoryRecordMapper;
import com.jk.asset.model.dto.ManageHistoryRecordDTO;
import com.jk.asset.model.entity.ManageHistoryRecord;
import com.jk.asset.model.request.ManageHistoryRecordRequest;
import com.jk.asset.model.request.page.ManageHistoryRecordPageRequest;
import com.jk.asset.service.ManageHistoryRecordService;
import com.jk.asset.service.handler.ManageHistoryRecordHandler;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 保全经理历史变更记录接口实现类
 *
 * @author wangshuai
 * @since 2024-07-24 14:24:35
 */
@RestController
@Slf4j
@Api(tags = "保全经理历史变更记录接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ManageHistoryRecordServiceImpl implements ManageHistoryRecordService {

  private final ManageHistoryRecordHandler handler;
  private final ManageHistoryRecordMapper mapper;

  @Override
  public Result<ManageHistoryRecordDTO> add(ManageHistoryRecordRequest request) {
    if (null == request || request.unverified() || null == request.getProjectId() || StringUtils.isEmpty(request.getManageStr())) {
      String message = "保全经理历史变更记录新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    String[] split = request.getManageStr().split(",");
    List<Long> newManage = Arrays.asList(split).stream().map(item -> Long.parseLong(item)).collect(Collectors.toList());

    List<Long> list = handler.list(new LambdaUpdateWrapper<ManageHistoryRecord>()
        .eq(ManageHistoryRecord::getProjectId, request.getProjectId())
        .isNull(ManageHistoryRecord::getEndDate))
        .stream()
        .map(item -> item.getManage())
        .collect(Collectors.toList());;

    List<Long> duplicates = list.stream()
        .filter(newManage::contains)
        .collect(Collectors.toList());

    LambdaUpdateWrapper<ManageHistoryRecord> eq = new LambdaUpdateWrapper<ManageHistoryRecord>()
        .set(ManageHistoryRecord::getEndDate, new Date())
        .eq(ManageHistoryRecord::getProjectId, request.getProjectId())
        .isNull(ManageHistoryRecord::getEndDate);
    if (!ObjectUtils.isEmpty(duplicates)){
      eq.notIn(ManageHistoryRecord::getManage, duplicates);
    }

    handler.update(eq);

    List<ManageHistoryRecord> billLawyerList = Lists.newArrayList();
    for (Long manage : newManage) {
      if (list.contains(manage)) {
        continue;
      }
      ManageHistoryRecord billLawyer = new ManageHistoryRecord()
          .setProjectId(request.getProjectId())
          .setStartDate(new Date())
          .setManage(manage);
      billLawyerList.add(billLawyer);
    }
    handler.saveBatch(billLawyerList);
    return Result.success();
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<ManageHistoryRecordDTO> update(ManageHistoryRecordRequest request) {
    if (null == request || null == request.getId()) {
      String message = "保全经理历史变更记录修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    ManageHistoryRecord manageHistoryRecord = PlatformMapUtils.getInstance().map(request, ManageHistoryRecord.class);
    handler.updateById(manageHistoryRecord);

    return Result.success();
  }

  @Override
  public Result<ManageHistoryRecordDTO> findById(Long id) {
    ManageHistoryRecord manageHistoryRecord = handler.getById(id);
    if (null == manageHistoryRecord) {
      String message = "保全经理历史变更记录单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    ManageHistoryRecordDTO dto = PlatformMapUtils.getInstance().map(manageHistoryRecord, ManageHistoryRecordDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<ManageHistoryRecordDTO>> findByProjectId(Long projectId) {
    List<ManageHistoryRecord> list = handler.list(new LambdaUpdateWrapper<ManageHistoryRecord>()
        .eq(ManageHistoryRecord::getProjectId, projectId)
        .isNotNull(ManageHistoryRecord::getEndDate)
        .orderByDesc(ManageHistoryRecord::getCreateStamp));

    if (ObjectUtils.isEmpty(list)) {
      return Result.success();
    }
    List<ManageHistoryRecordDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(list, ManageHistoryRecordDTO.class);

    return Result.success(dtoList);
  }

  @Override
  public Result<List<ManageHistoryRecordDTO>> findAll(Integer current, Integer size, ManageHistoryRecordPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<ManageHistoryRecord> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<ManageHistoryRecord> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<ManageHistoryRecordDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, ManageHistoryRecordDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}