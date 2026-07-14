package com.jk.asset.service.property;

import com.jk.asset.model.dto.property.PropertyInfoDTO;
import com.jk.asset.model.request.page.property.PropertyInfoPageRequest;
import com.jk.asset.model.request.property.PropertyInfoRequest;
import com.jk.common.model.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 资产信息接口
 *
 * @author wangshuai
 * @since 2024-07-10 09:47:37
 */
public interface PropertyInfoService {

  String CONTEXT = "/property/info";

  /**
   * 新增
   *
   * @param request 资产信息入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-10 09:47:37
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<PropertyInfoDTO> add(@RequestBody PropertyInfoRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-10 09:47:37
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 资产信息入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-10 09:47:37
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<PropertyInfoDTO> update(@RequestBody PropertyInfoRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-10 09:47:37
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<PropertyInfoDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 导入资产信息
   * @param file
   * @return com.jk.common.model.Result<java.lang.String>
   * @author wangshuai
   * @since 2024/7/15 9:26
   **/
  @PostMapping(value = CONTEXT + "/import")
  Result<String> importData(@RequestParam("file") MultipartFile file);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 资产信息分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-10 09:47:37
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<PropertyInfoDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody PropertyInfoPageRequest request);

  /**
   * 资产台账导出
   *
   * @param request 入参
   * @param response 响应
   * @author Yuqiang Wu
   * @since 2024/8/9 009 15:39
   */
  @ApiOperation(value = "资产台账导出", notes = "详细描述")
  @PostMapping(CONTEXT + "s/export")
  void export(@RequestBody PropertyInfoPageRequest request, HttpServletResponse response);

}