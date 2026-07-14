package com.jk.asset.service;

import com.jk.asset.model.dto.RecoveryPaymentCollectionDTO;
import com.jk.asset.model.request.RecoveryPaymentCollectionRequest;
import com.jk.asset.model.request.page.RecoveryPaymentCollectionPageRequest;
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
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 回款表接口
 *
 * @author wangtao
 * @since 2024-06-20 18:53:02
 */
public interface RecoveryPaymentCollectionService {

  String CONTEXT = "/recovery/payment/collection";

  /**
   * 新增
   *
   * @param request 回款表入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-20 18:53:02
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<RecoveryPaymentCollectionDTO> add(@RequestBody RecoveryPaymentCollectionRequest request);

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
   * @param request 回款表入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-20 18:53:02
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<RecoveryPaymentCollectionDTO> update(@RequestBody RecoveryPaymentCollectionRequest request);


  /**
   * 提交
   *
   * @param request 回款表入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-20 18:53:02
   */
  @ApiOperation(value = "提交-submit", notes = "详细描述")
  @PutMapping(CONTEXT + "/submit")
  Result<RecoveryPaymentCollectionDTO> submit(@RequestBody RecoveryPaymentCollectionRequest request);

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
  Result<RecoveryPaymentCollectionDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 根据项目ids查询累计回款金额
   *
   * @param projectIds 项目ids
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-20 18:53:02
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @PostMapping(CONTEXT + "/batch/projectIds")
  Result<Map<Long, BigDecimal>> findByProjectId(@RequestBody List<Long> projectIds);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 回款表分页入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-20 18:53:02
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<RecoveryPaymentCollectionDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody RecoveryPaymentCollectionPageRequest request);

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
  Result<RecoveryPaymentCollectionDTO> writeBackProject(@ApiParam("主键") @PathVariable Long id);

  /**
   * 回款 导入
   */
  @PostMapping(value = CONTEXT + "/import/recovery/paymentCollection")
  Result<String> importData(@RequestParam("file") MultipartFile file) throws ParseException;

}