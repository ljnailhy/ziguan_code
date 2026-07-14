package com.jk.asset.service;

import com.jk.asset.model.dto.AllocationInfoDTO;
import com.jk.asset.model.dto.proceeding.RecoveryAdjustTrialDTO;
import com.jk.asset.model.request.AllocationInfoRequest;
import com.jk.asset.model.request.page.AllocationInfoPageRequest;
import com.jk.common.model.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 分配/变更主表接口
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
public interface AllocationInfoService {

  String CONTEXT = "/allocation/info";

  /**
   * 新增
   *
   * @param request 分配/变更主表入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 10:13:53
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<AllocationInfoDTO> add(@RequestBody AllocationInfoRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 10:13:53
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 分配/变更主表入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 10:13:53
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<AllocationInfoDTO> update(@RequestBody AllocationInfoRequest request);

  /**
   * 提交
   *
   * @param request 分配/变更主表入参
   * @return com.jk.common.model.Result
   * @author Yuqiang Wu
   * @since 2024/6/29 029 17:52
   */
  @ApiOperation(value = "提交-submit", notes = "详细描述")
  @PutMapping(CONTEXT + "/submit")
  Result<AllocationInfoDTO> submit(@RequestBody AllocationInfoRequest request);

  /**
   * 反写相关信息
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-03 10:13:14
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/writeBack/{id}")
  Result<AllocationInfoDTO> writeBackProject(@ApiParam("主键") @PathVariable Long id);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 10:13:53
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<AllocationInfoDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 分配/变更主表分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 10:13:53
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<AllocationInfoDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody AllocationInfoPageRequest request);

}