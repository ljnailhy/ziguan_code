package com.jk.asset.service;

import com.jk.common.model.Result;
import com.jk.asset.model.dto.ProjectTransferDetailedDTO;
import com.jk.asset.model.request.ProjectTransferDetailedRequest;
import com.jk.asset.model.request.page.ProjectTransferDetailedPageRequest;
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
 * 项目移交明细接口
 *
 * @author wangshuai
 * @since 2024-07-17 18:27:56
 */
public interface ProjectTransferDetailedService {

  String CONTEXT = "/project/transfer/detailed";

  /**
   * 新增
   *
   * @param request 入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-17 18:27:56
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<ProjectTransferDetailedDTO> add(@RequestBody ProjectTransferDetailedRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-17 18:27:56
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-17 18:27:56
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<ProjectTransferDetailedDTO> update(@RequestBody ProjectTransferDetailedRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-17 18:27:56
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<ProjectTransferDetailedDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 根据项目移交id查询项目移交明细
   *
   * @param transferId 移交id
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-17 18:27:56
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/transferId/{transferId}")
  Result<List<ProjectTransferDetailedDTO>> findByTransferId(@ApiParam("主键") @PathVariable Long transferId);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-17 18:27:56
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<ProjectTransferDetailedDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody ProjectTransferDetailedPageRequest request);

}