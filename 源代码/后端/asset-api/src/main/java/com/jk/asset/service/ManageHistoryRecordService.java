package com.jk.asset.service;

import com.jk.asset.model.request.ManageHistoryRecordRequest;
import com.jk.common.model.Result;
import com.jk.asset.model.dto.ManageHistoryRecordDTO;
import com.jk.asset.model.request.page.ManageHistoryRecordPageRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 保全经理历史变更记录接口
 *
 * @author wangshuai
 * @since 2024-07-24 14:24:35
 */
public interface ManageHistoryRecordService {

  String CONTEXT = "/manage/history/record";

  /**
   * 新增
   *
   * @param request 保全经理历史变更记录入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-24 14:24:35
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<ManageHistoryRecordDTO> add(@RequestBody ManageHistoryRecordRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-24 14:24:35
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 保全经理历史变更记录入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-24 14:24:35
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<ManageHistoryRecordDTO> update(@RequestBody ManageHistoryRecordRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-24 14:24:35
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<ManageHistoryRecordDTO> findById(@ApiParam("主键") @PathVariable Long id);


  /**
   * 根据项目查询历史记录
   *
   * @param projectId 项目id
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-24 14:24:35
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/project/{projectId}")
  Result<List<ManageHistoryRecordDTO>> findByProjectId(@ApiParam("项目id") @PathVariable Long projectId);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 保全经理历史变更记录分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-24 14:24:35
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<ManageHistoryRecordDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody ManageHistoryRecordPageRequest request);

}