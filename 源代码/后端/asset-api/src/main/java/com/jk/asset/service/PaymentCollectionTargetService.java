package com.jk.asset.service;

import com.jk.asset.model.dto.PaymentCollectionTargetDTO;
import com.jk.asset.model.request.PaymentCollectionTargetRequest;
import com.jk.asset.model.request.page.PaymentCollectionTargetPageRequest;
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
 * 年度回款目标接口
 *
 * @author wangshuai
 * @since 2024-06-25 10:24:50
 */
public interface PaymentCollectionTargetService {

  String CONTEXT = "/payment/collection/target";

  /**
   * 新增
   *
   * @param request 年度回款目标入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-25 10:24:50
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<PaymentCollectionTargetDTO> add(@RequestBody PaymentCollectionTargetRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-25 10:24:50
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 年度回款目标入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-25 10:24:50
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<PaymentCollectionTargetDTO> update(@RequestBody PaymentCollectionTargetRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-25 10:24:50
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<PaymentCollectionTargetDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 年度回款目标分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-25 10:24:50
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<PaymentCollectionTargetDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody PaymentCollectionTargetPageRequest request);

}