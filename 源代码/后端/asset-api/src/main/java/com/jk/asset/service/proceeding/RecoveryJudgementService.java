package com.jk.asset.service.proceeding;

import com.jk.asset.model.dto.AllocationInfoDTO;
import com.jk.asset.model.dto.proceeding.RecoveryAdjustTrialDTO;
import com.jk.asset.model.request.AllocationInfoRequest;
import com.jk.common.model.Result;
import com.jk.asset.model.dto.proceeding.RecoveryJudgementDTO;
import com.jk.asset.model.request.proceeding.RecoveryJudgementRequest;
import com.jk.asset.model.request.page.proceeding.RecoveryJudgementPageRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 审判信息（立案一审二审再审）接口
 *
 * @author wangshuai
 * @since 2024-07-02 09:56:48
 */
public interface RecoveryJudgementService {

  String CONTEXT = "/recovery/judgement";

  /**
   * 新增
   *
   * @param request 审判信息（立案一审二审再审）入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-02 09:56:48
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<RecoveryJudgementDTO> add(@RequestBody RecoveryJudgementRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-02 09:56:48
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 审判信息（立案一审二审再审）入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-02 09:56:48
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<RecoveryJudgementDTO> update(@RequestBody RecoveryJudgementRequest request);

  @ApiOperation(value = "查询保全经理 key固定为manage", notes = "详细描述")
  @GetMapping(CONTEXT + "/examine/user/{id}")
  Result<Map<String,Object>> getExamineUser(@ApiParam("主键") @PathVariable Long id);

  /**
   * 提交
   *
   * @param request 审判信息（立案一审二审再审）入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024/6/29 029 17:52
   */
  @ApiOperation(value = "提交-submit", notes = "详细描述")
  @PutMapping(CONTEXT + "/submit")
  Result<RecoveryJudgementDTO> submit(@RequestBody RecoveryJudgementRequest request);

  /**
   * 反写相关信息
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-03 10:13:14
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/writeBack/{id}")
  Result<RecoveryJudgementDTO> writeBackProject(@ApiParam("主键") @PathVariable Long id);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-02 09:56:48
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<RecoveryJudgementDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 根据诉讼id 查询审判信息
   *
   * @param litigationId 诉讼id
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-02 09:56:48
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/litigation/{litigationId}")
  Result<List<RecoveryJudgementDTO>> findByLitigationId(@ApiParam("诉讼id") @PathVariable Long litigationId);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 审判信息（立案一审二审再审）分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-02 09:56:48
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<RecoveryJudgementDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody RecoveryJudgementPageRequest request);

}