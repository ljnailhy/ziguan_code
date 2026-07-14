package com.jk.asset.service;

import com.jk.asset.model.dto.ProjectInfoExtDTO;
import com.jk.asset.model.request.ProjectInfoExtRequest;
import com.jk.asset.model.request.page.ProjectInfoExtPageRequest;
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

/**
 * 项目信息详细信息扩展表接口
 *
 * @author wangtao
 * @since 2024-06-24 09:41:55
 */
public interface ProjectInfoExtService {

  String CONTEXT = "/project/info/ext";

  /**
   * 新增
   *
   * @param request 项目信息详细信息扩展表入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:41:55
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<ProjectInfoExtDTO> add(@RequestBody ProjectInfoExtRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:41:55
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 项目信息详细信息扩展表入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:41:55
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<ProjectInfoExtDTO> update(@RequestBody ProjectInfoExtRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:41:55
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<ProjectInfoExtDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 项目信息详细信息扩展表分页入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:41:55
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<ProjectInfoExtDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody ProjectInfoExtPageRequest request);

  /**
   * 诉讼台账
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 项目信息详细信息扩展表分页入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:41:55
   */
  @ApiOperation(value = "诉讼台账", notes = "详细描述")
  @PostMapping(CONTEXT + "/litigation/ledger")
  Result<List<ProjectInfoExtDTO>> litigationLedger(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
                                          @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
                                          @RequestBody ProjectInfoExtPageRequest request);

  /**
   * 诉讼台账导出
   *
   * @param request 入参
   * @param response 响应
   * @author Yuqiang Wu
   * @since 2024/8/9 009 15:39
   */
  @ApiOperation(value = "诉讼台账导出", notes = "详细描述")
  @PostMapping(CONTEXT + "/litigation/ledger/export")
  void exportLitigationLedgers(@RequestBody ProjectInfoExtPageRequest request, HttpServletResponse response);

}