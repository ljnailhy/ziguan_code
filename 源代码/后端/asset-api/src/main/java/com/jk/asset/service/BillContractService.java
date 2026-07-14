package com.jk.asset.service;

import com.jk.asset.model.dto.BillLawyerDTO;
import com.jk.common.model.Result;
import com.jk.asset.model.dto.BillContractDTO;
import com.jk.asset.model.request.BillContractRequest;
import com.jk.asset.model.request.page.BillContractPageRequest;
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
 * 单据合同关联表接口
 *
 * @author wangshuai
 * @since 2024-06-28 11:49:32
 */
public interface BillContractService {

  String CONTEXT = "/bill/contract";

  /**
   * 新增
   *
   * @param request 单据合同关联表入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 11:49:32
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<BillContractDTO> add(@RequestBody BillContractRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 11:49:32
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 单据合同关联表入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 11:49:32
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<BillContractDTO> update(@RequestBody BillContractRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 11:49:32
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<BillContractDTO> findById(@ApiParam("主键") @PathVariable Long id);

  @ApiOperation(value = "根据doType和doId查询合同-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{doType}/{doId}")
  Result<List<BillContractDTO>> findHistoryByDoId(@ApiParam("对象iD") @PathVariable Long doId, @ApiParam("对象类型") @PathVariable String doType);
  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 单据合同关联表分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 11:49:32
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<BillContractDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody BillContractPageRequest request);

}