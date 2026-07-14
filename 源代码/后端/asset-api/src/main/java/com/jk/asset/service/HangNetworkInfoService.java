package com.jk.asset.service;

import com.jk.common.model.Result;
import com.jk.asset.model.dto.HangNetworkInfoDTO;
import com.jk.asset.model.request.HangNetworkInfoRequest;
import com.jk.asset.model.request.page.HangNetworkInfoPageRequest;
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
 * 挂网信息表接口
 *
 * @author wangshuai
 * @since 2024-07-09 15:15:21
 */
public interface HangNetworkInfoService {

  String CONTEXT = "/hang/network/info";

  /**
   * 新增
   *
   * @param request 挂网信息表入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-09 15:15:21
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<HangNetworkInfoDTO> add(@RequestBody HangNetworkInfoRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-09 15:15:21
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 挂网信息表入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-09 15:15:21
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<HangNetworkInfoDTO> update(@RequestBody HangNetworkInfoRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-09 15:15:21
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<HangNetworkInfoDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 根据doid和dotype查询法拍记录
   * @param doId
   * @param doType
   * @return com.jk.common.model.Result<java.util.List<com.jk.asset.model.dto.HangNetworkInfoDTO>>
   * @author wangshuai
   * @since 2024/7/9 18:33
   **/
  @ApiOperation(value = "查询法拍记录-findByDoId", notes = "详细描述")
  @GetMapping(CONTEXT + "/{doId}/{doType}")
  Result<List<HangNetworkInfoDTO>> findByDoId(@ApiParam("数据对象id") @PathVariable Long doId,@ApiParam("数据对象类型") @PathVariable String doType);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 挂网信息表分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-09 15:15:21
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<HangNetworkInfoDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody HangNetworkInfoPageRequest request);

}