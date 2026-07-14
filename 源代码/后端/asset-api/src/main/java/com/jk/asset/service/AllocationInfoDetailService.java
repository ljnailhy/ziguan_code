package com.jk.asset.service;

import com.jk.common.model.Result;
import com.jk.asset.model.dto.AllocationInfoDetailDTO;
import com.jk.asset.model.request.AllocationInfoDetailRequest;
import com.jk.asset.model.request.page.AllocationInfoDetailPageRequest;
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
 * 项目分配/变更明细接口
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
public interface AllocationInfoDetailService {

  String CONTEXT = "/allocation/info/detail";

  /**
   * 新增
   *
   * @param request 项目分配/变更明细入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 10:13:53
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<AllocationInfoDetailDTO> add(@RequestBody AllocationInfoDetailRequest request);

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
   * @param request 项目分配/变更明细入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 10:13:53
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<AllocationInfoDetailDTO> update(@RequestBody AllocationInfoDetailRequest request);

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
  Result<AllocationInfoDetailDTO> findById(@ApiParam("主键") @PathVariable Long id);


  /**
   * 根据分配id查询明细
   *
   * @param allocationId 分配id
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 10:13:53
   */
  @ApiOperation(value = "分配id查询明细-findByAllocationId", notes = "详细描述")
  @GetMapping(CONTEXT + "/allocation/{allocationId}")
  Result<List<AllocationInfoDetailDTO>> findByAllocationId(@ApiParam("分配id") @PathVariable Long allocationId);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 项目分配/变更明细分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 10:13:53
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<AllocationInfoDetailDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody AllocationInfoDetailPageRequest request);

}