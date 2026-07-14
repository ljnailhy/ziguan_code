package com.jk.asset.service;

import com.jk.asset.model.dto.ProjectInfoDTO;
import com.jk.common.model.Result;
import com.jk.asset.model.dto.CustomerInfoDTO;
import com.jk.asset.model.request.CustomerInfoRequest;
import com.jk.asset.model.request.page.CustomerInfoPageRequest;
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
 * 客户信息表接口
 *
 * @author wangtao
 * @since 2024-06-20 11:38:52
 */
public interface CustomerInfoService {

  String CONTEXT = "/customer/info";

  /**
   * 新增
   *
   * @param request 客户信息表入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-20 11:38:52
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<CustomerInfoDTO> add(@RequestBody CustomerInfoRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-20 11:38:52
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 客户信息表入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-20 11:38:52
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<CustomerInfoDTO> update(@RequestBody CustomerInfoRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-20 11:38:52
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<CustomerInfoDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 批量查询客户信息
   * @param idList
   * @return com.jk.common.model.Result<java.util.List<com.jk.asset.model.dto.LawyerInfoDTO>>
   * @author wangshuai
   * @since 2024/7/10 17:11
   **/
  @ApiOperation(value = "批量查询客户信息-findByIds", notes = "详细描述")
  @PostMapping(CONTEXT + "/batch")
  Result<List<CustomerInfoDTO>> findByIds(@RequestBody List<Long> idList);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 客户信息表分页入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-20 11:38:52
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<CustomerInfoDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody CustomerInfoPageRequest request);

}