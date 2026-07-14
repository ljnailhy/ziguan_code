package com.jk.asset.service.property;

import com.jk.asset.model.dto.proceeding.RecoveryAdjustTrialDTO;
import com.jk.asset.model.dto.property.OperationInfoDTO;
import com.jk.asset.model.request.proceeding.RecoveryAdjustTrialRequest;
import com.jk.common.model.Result;
import com.jk.asset.model.dto.property.LeaseInfoDTO;
import com.jk.asset.model.request.property.LeaseInfoRequest;
import com.jk.asset.model.request.page.property.LeaseInfoPageRequest;
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
 * 租赁信息表接口
 *
 * @author wangshuai
 * @since 2024-07-11 18:04:22
 */
public interface LeaseInfoService {

  String CONTEXT = "/lease/info";

  /**
   * 新增
   *
   * @param request 租赁信息表入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-11 18:04:22
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<LeaseInfoDTO> add(@RequestBody LeaseInfoRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-11 18:04:22
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 租赁信息表入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-11 18:04:22
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<LeaseInfoDTO> update(@RequestBody LeaseInfoRequest request);

  /**
   * 提交
   * @param request 调解或审判信息	入参
   * @return com.jk.common.model.Result<com.jk.asset.model.dto.proceeding.RecoveryAdjustTrialDTO>
   * @author wangshuai
   * @since 2024/7/13 14:22
   **/
  @ApiOperation(value = "提交-submit", notes = "详细描述")
  @PutMapping(CONTEXT+"/submit")
  Result<LeaseInfoDTO> submit(@RequestBody LeaseInfoRequest request);


  @ApiOperation(value = "反写相关信息-writeBackProject", notes = "详细描述")
  @GetMapping(CONTEXT + "/writeBack/{id}")
  Result<LeaseInfoDTO> writeBackProject(@ApiParam("主键") @PathVariable Long id);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-11 18:04:22
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<LeaseInfoDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 租赁信息表分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-11 18:04:22
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<LeaseInfoDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody LeaseInfoPageRequest request);

}