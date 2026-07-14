package com.jk.asset.service;

import com.jk.common.model.Result;
import com.jk.asset.model.dto.RecoveryPaymentCollectionDetailDTO;
import com.jk.asset.model.request.RecoveryPaymentCollectionDetailRequest;
import com.jk.asset.model.request.page.RecoveryPaymentCollectionDetailPageRequest;
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
 * 回款明细接口
 *
 * @author wangtao
 * @since 2024-06-20 18:53:02
 */
public interface RecoveryPaymentCollectionDetailService {

  String CONTEXT = "/recovery/payment/collection/detail";

  /**
   * 新增
   *
   * @param request 回款明细入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-20 18:53:02
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<RecoveryPaymentCollectionDetailDTO> add(@RequestBody RecoveryPaymentCollectionDetailRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-20 18:53:02
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 回款明细入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-20 18:53:02
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<RecoveryPaymentCollectionDetailDTO> update(@RequestBody RecoveryPaymentCollectionDetailRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-20 18:53:02
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<RecoveryPaymentCollectionDetailDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 回款明细分页入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-20 18:53:02
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<RecoveryPaymentCollectionDetailDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody RecoveryPaymentCollectionDetailPageRequest request);

}