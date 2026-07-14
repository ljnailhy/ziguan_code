package com.jk.asset.service;

import com.jk.asset.model.dto.ProjectInfoDTO;
import com.jk.asset.model.dto.RevePropertyInfoDTO;
import com.jk.asset.model.request.RevePropertyInfoRequest;
import com.jk.asset.model.request.page.RevePropertyInfoPageRequest;
import com.jk.common.model.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 反担保/其他财产线索信息接口
 *
 * @author wangtao
 * @since 2024-06-24 09:42:39
 */
public interface RevePropertyInfoService {

  String CONTEXT = "/reve/property/info";

  /**
   * 新增
   *
   * @param request 反担保/其他财产线索信息入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:42:39
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<RevePropertyInfoDTO> add(@RequestBody RevePropertyInfoRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:42:39
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 批量查询反担保信息
   * @param idList
   * @return com.jk.common.model.Result<java.util.List<com.jk.asset.model.dto.RevePropertyInfoDTO>>
   * @author wangshuai
   * @since 2024/7/10 17:13
   **/
  @ApiOperation(value = "批量查询反担保信息-findByIds", notes = "详细描述")
  @PostMapping(CONTEXT + "/batch")
  Result<List<RevePropertyInfoDTO>>  findByIds(@RequestBody List<Long> idList);

  /**
   * 修改
   *
   * @param request 反担保/其他财产线索信息入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:42:39
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<RevePropertyInfoDTO> update(@RequestBody RevePropertyInfoRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:42:39
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<RevePropertyInfoDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 根据doType,doId，billType查询信息
   *
   * @param doId 对象id
   * @param doType 对象类型
   * @param billType 单据类型
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:42:39
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{doId}/{doType}/{billType}")
  Result<List<RevePropertyInfoDTO>> findByDoId(@ApiParam("对象id") @PathVariable Long doId,
                                         @ApiParam("对象类型") @PathVariable String doType,
                                         @ApiParam("单据类型") @PathVariable String billType);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 反担保/其他财产线索信息分页入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:42:39
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<RevePropertyInfoDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody RevePropertyInfoPageRequest request);

}