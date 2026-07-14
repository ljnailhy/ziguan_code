package com.jk.asset.service;

import com.jk.asset.model.dto.RecoveryPaymentDTO;
import com.jk.asset.model.request.RecoveryPaymentRequest;
import com.jk.asset.model.request.page.RecoveryPaymentPageRequest;
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
 * 付款表接口
 *
 * @author wangtao
 * @since 2024-07-08 11:35:36
 */
public interface RecoveryPaymentService {

  String CONTEXT = "/recovery/payment";

  /**
   * 新增
   *
   * @param request 付款表入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-07-08 11:35:36
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<RecoveryPaymentDTO> add(@RequestBody RecoveryPaymentRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-07-08 11:35:36
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);




  /**
   * 修改
   *
   * @param request 付款表入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-07-08 11:35:36
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<RecoveryPaymentDTO> update(@RequestBody RecoveryPaymentRequest request);


  /**
   * 提交
   *
   * @param request 付款表入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-20 18:53:02
   */
  @ApiOperation(value = "提交-submit", notes = "详细描述")
  @PutMapping(CONTEXT + "/submit")
  Result<RecoveryPaymentDTO> submit(@RequestBody RecoveryPaymentRequest request);
  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-07-08 11:35:36
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<RecoveryPaymentDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 付款表分页入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-07-08 11:35:36
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<RecoveryPaymentDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
                                           @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
                                           @RequestBody RecoveryPaymentPageRequest request);
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
  Result<RecoveryPaymentDTO> writeBackProject(@ApiParam("主键") @PathVariable Long id);
}