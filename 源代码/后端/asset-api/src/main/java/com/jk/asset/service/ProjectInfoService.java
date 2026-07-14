package com.jk.asset.service;

import com.jk.asset.model.dto.ContractInfoDTO;
import com.jk.asset.model.dto.ProductNameDTO;
import com.jk.asset.model.dto.ProjectInfoDTO;
import com.jk.asset.model.dto.ProjectWorkflowProcess;
import com.jk.asset.model.request.ProjectInfoRequest;
import com.jk.asset.model.request.SyncCompensatoryRequest;
import com.jk.asset.model.request.page.ProjectInfoPageRequest;
import com.jk.common.model.Result;
import io.lettuce.core.dynamic.annotation.Param;
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

import java.util.List;

/**
 * 项目信息表接口
 *
 * @author wangtao
 * @since 2024-06-24 09:41:55
 */
public interface ProjectInfoService {

  String CONTEXT = "/project/info";

  /**
   * 新增
   *
   * @param request 项目信息表入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:41:55
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<ProjectInfoDTO> add(@RequestBody ProjectInfoRequest request);

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
   * @param request 项目信息表入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:41:55
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<ProjectInfoDTO> update(@RequestBody ProjectInfoRequest request);

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
  Result<ProjectInfoDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 变更保全经理
   *
   * @param request 保全经理
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:41:55
   */
  @ApiOperation(value = "变更保全经理-changeManage", notes = "详细描述")
  @PostMapping(CONTEXT + "/change/manage")
  Result<ProjectInfoDTO> changeManage(@RequestBody ProjectInfoRequest request);

  /**
   * 批量查询项目信息
   * @param idList
   * @return com.jk.common.model.Result<java.util.List<com.jk.asset.model.dto.LawyerInfoDTO>>
   * @author wangshuai
   * @since 2024/7/10 17:11
   **/
  @ApiOperation(value = "批量查询项目信息-findByIds", notes = "详细描述")
  @PostMapping(CONTEXT + "/batch")
  Result<List<ProjectInfoDTO>>  findByIds(@RequestBody List<Long> idList);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 项目信息表分页入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:41:55
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<ProjectInfoDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody ProjectInfoPageRequest request);

  /**
   * 分页带权限查询查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 项目信息表分页入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-06-24 09:41:55
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "/limits")
  Result<List<ProjectInfoDTO>> findLimitsAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
                                       @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
                                       @RequestBody ProjectInfoPageRequest request);

  /**
   * @param syncCompensatoryRequest 代偿项目同步 身份证号 入参
   * @return com.jk.common.model.Result
   * @author wangtao
   */
  @ApiOperation(value = "代偿项目同步", notes = "代偿项目同步")
  @PostMapping(CONTEXT + "/compensatory/project/sync")
  Result<String> syncCompensatory(@RequestBody SyncCompensatoryRequest syncCompensatoryRequest);

  /**
   * 导入 代偿项目
   */
  @PostMapping(value = CONTEXT + "/import/compensatory")
  Result<String> importData(@RequestParam("file") MultipartFile file, String isImport);

  /**
   * 查询项目 各流程附件
   */
  @GetMapping(value = CONTEXT + "/files")
  Result<List<Long>> getProjectFile(@Param("id") Long id, @Param("type") String type);

  /**
   * 查询 产品名称
   */
  @ApiOperation(value = "查询产品名称", notes = "查询产品名称")
  @GetMapping(value = CONTEXT + "/productName")
  Result<List<ProductNameDTO>> findProductName();


  /**
   * 查询项目 各流程附件
   */
  @GetMapping(value = CONTEXT + "/flow")
  Result<List<ProjectWorkflowProcess>> getProjectFlow(@Param("id") Long id);

  /* 查询项目 各流程附件
   */
  @GetMapping(value = CONTEXT + "/contract")
  Result<List<ContractInfoDTO>> findContractByProjectId(@Param("id") Long id);

}