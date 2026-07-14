package com.jk.asset.service;

import com.jk.asset.model.dto.proceeding.RecoveryExecuteDTO;
import com.jk.asset.model.request.proceeding.RecoveryExecuteRequest;
import com.jk.common.model.Result;
import com.jk.asset.model.dto.ProjectTransferDTO;
import com.jk.asset.model.request.ProjectTransferRequest;
import com.jk.asset.model.request.page.ProjectTransferPageRequest;
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
 * 项目移交接口
 *
 * @author wangshuai
 * @since 2024-07-17 18:27:55
 */
public interface ProjectTransferService {

  String CONTEXT = "/project/transfer";

  /**
   * 新增
   *
   * @param request 项目移交入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-17 18:27:55
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<ProjectTransferDTO> add(@RequestBody ProjectTransferRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-17 18:27:55
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 项目移交入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-17 18:27:55
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<ProjectTransferDTO> update(@RequestBody ProjectTransferRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-17 18:27:55
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<ProjectTransferDTO> findById(@ApiParam("主键") @PathVariable Long id);


  /**
   * 提交
   * @param request 项目移交	入参
   * @return com.jk.common.model.Result<com.jk.asset.model.dto.proceeding.RecoveryJudgementDTO>
   * @author wangshuai
   * @since 2024/7/9 16:45
   **/
  @ApiOperation(value = "提交-submit", notes = "详细描述")
  @PutMapping(CONTEXT + "/submit")
  Result<ProjectTransferDTO> submit(@RequestBody ProjectTransferRequest request);

  /**
   * 反写相关信息
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-03 10:13:14
   */
  @ApiOperation(value = "反写相关信息-writeBackProject", notes = "详细描述")
  @GetMapping(CONTEXT + "/writeBack/{id}")
  Result<ProjectTransferDTO> writeBackProject(@ApiParam("主键") @PathVariable Long id);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 项目移交分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-17 18:27:55
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<ProjectTransferDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody ProjectTransferPageRequest request);

}