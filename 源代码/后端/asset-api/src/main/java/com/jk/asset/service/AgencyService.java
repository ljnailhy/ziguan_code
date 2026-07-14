package com.jk.asset.service;

import com.jk.common.model.Result;
import com.jk.asset.model.dto.AgencyDTO;
import com.jk.asset.model.request.AgencyRequest;
import com.jk.asset.model.request.page.AgencyPageRequest;
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
 * 代理机构接口
 *
 * @author wangtao
 * @since 2024-06-19 18:14:46
 */
public interface AgencyService {

  String CONTEXT = "/agency";

  /**
   * 新增
   *
   * @param request 代理机构入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-19 18:14:46
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<AgencyDTO> add(@RequestBody AgencyRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-19 18:14:46
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 代理机构入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-19 18:14:46
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<AgencyDTO> update(@RequestBody AgencyRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-19 18:14:46
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<AgencyDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 代理机构分页入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-19 18:14:46
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<AgencyDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody AgencyPageRequest request);

}