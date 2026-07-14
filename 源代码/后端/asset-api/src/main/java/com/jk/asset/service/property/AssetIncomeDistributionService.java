package com.jk.asset.service.property;

import com.jk.asset.model.dto.property.LeasePaymentCycleDTO;
import com.jk.common.model.Result;
import com.jk.asset.model.dto.property.AssetIncomeDistributionDTO;
import com.jk.asset.model.request.property.AssetIncomeDistributionRequest;
import com.jk.asset.model.request.page.property.AssetIncomeDistributionPageRequest;
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
 * 资产收入分配接口
 *
 * @author wangshuai
 * @since 2024-07-13 09:48:45
 */
public interface AssetIncomeDistributionService {

  String CONTEXT = "/asset/income/distribution";

  /**
   * 新增
   *
   * @param request 资产收入分配入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-13 09:48:45
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<AssetIncomeDistributionDTO> add(@RequestBody AssetIncomeDistributionRequest request);

  /**
   * 根据数据对象id和数据对象type查询 资产收入分配
   * @param doType 数据对象类型
   * @param doId 数据对象id
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-11 18:03:58
   */
  @ApiOperation(value = "根据数据对象id和数据对象type资产收入分配-findLeasePaymentCycleByDoId", notes = "详细描述")
  @GetMapping(CONTEXT + "/{doType}/{doId}")
  Result<List<AssetIncomeDistributionDTO>> findLeasePaymentCycleByDoId(@ApiParam("对象iD") @PathVariable Long doId, @ApiParam("对象类型") @PathVariable String doType);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-13 09:48:45
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 资产收入分配入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-13 09:48:45
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<AssetIncomeDistributionDTO> update(@RequestBody AssetIncomeDistributionRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-13 09:48:45
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<AssetIncomeDistributionDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 资产收入分配分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-13 09:48:45
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<AssetIncomeDistributionDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody AssetIncomeDistributionPageRequest request);

}