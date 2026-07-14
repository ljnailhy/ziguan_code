package com.jk.asset.service.proceeding;

import com.jk.common.model.Result;
import com.jk.asset.model.dto.proceeding.RecoveryLitigationDTO;
import com.jk.asset.model.request.proceeding.RecoveryLitigationRequest;
import com.jk.asset.model.request.page.proceeding.RecoveryLitigationPageRequest;
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
 * 诉讼接口
 *
 * @author wangshuai
 * @since 2024-07-01 16:38:08
 */
public interface RecoveryLitigationService {

  String CONTEXT = "/recovery/litigation";

  /**
   * 新增
   *
   * @param request 诉讼入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-01 16:38:08
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<RecoveryLitigationDTO> add(@RequestBody RecoveryLitigationRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-01 16:38:08
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 诉讼入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-01 16:38:08
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<RecoveryLitigationDTO> update(@RequestBody RecoveryLitigationRequest request);


  /**
   * 提交
   *
   * @param request 诉讼入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024/6/29 029 17:52
   */
  @ApiOperation(value = "提交-submit", notes = "详细描述")
  @PutMapping(CONTEXT + "/submit")
  Result<RecoveryLitigationDTO> submit(@RequestBody RecoveryLitigationRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-01 16:38:08
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<RecoveryLitigationDTO> findById(@ApiParam("主键") @PathVariable Long id);

  @ApiOperation(value = "根据项目id查询最新已提交的诉讼信息-findByProjectId", notes = "详细描述")
  @GetMapping(CONTEXT + "/project/{projectId}")
  Result<RecoveryLitigationDTO> findByProjectId(@ApiParam("主键") @PathVariable Long projectId);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 诉讼分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-01 16:38:08
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<RecoveryLitigationDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody RecoveryLitigationPageRequest request);

}