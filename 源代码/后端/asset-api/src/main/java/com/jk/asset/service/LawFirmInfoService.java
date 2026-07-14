package com.jk.asset.service;

import com.jk.asset.model.dto.LawFirmInfoDTO;
import com.jk.asset.model.request.LawFirmInfoRequest;
import com.jk.asset.model.request.page.LawFirmInfoPageRequest;
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
 * 律所信息接口
 *
 * @author wangshuai
 * @since 2024-06-24 11:09:30
 */
public interface LawFirmInfoService {

  String CONTEXT = "/law/firm/info";

  /**
   * 新增
   *
   * @param request 律所信息入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-24 11:09:30
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<LawFirmInfoDTO> add(@RequestBody LawFirmInfoRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-24 11:09:30
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 律所信息入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-24 11:09:30
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<LawFirmInfoDTO> update(@RequestBody LawFirmInfoRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-24 11:09:30
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<LawFirmInfoDTO> findById(@ApiParam("主键") @PathVariable Long id);


  /**
   * 启用禁用律所
   * @param id  律所id
   * @param enabled 是否禁用
   * @return com.jk.common.model.Result<?>
   * @author wangshuai
   * @since 2024/6/27 18:20
   **/
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/enable/{id}/{enabled}")
  Result<?> isEnable(@ApiParam("主键") @PathVariable Long id,@ApiParam("是否启用") @PathVariable Boolean enabled);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 律所信息分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-24 11:09:30
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<LawFirmInfoDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody LawFirmInfoPageRequest request);

  /**
   * 律师组件统计
   * @param current 当前页码
   * @param size 每页大小
   * @param request 律所信息分页入参
   * @return com.jk.common.model.Result
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "/assembly")
  Result<List<LawFirmInfoDTO>> findAssemblyList(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
                                       @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
                                       @RequestBody LawFirmInfoPageRequest request);

}