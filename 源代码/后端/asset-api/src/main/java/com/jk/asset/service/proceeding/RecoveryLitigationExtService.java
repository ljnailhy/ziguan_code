package com.jk.asset.service.proceeding;

import com.jk.asset.model.request.page.proceeding.RecoveryLitigationExtPageRequest;
import com.jk.common.model.Result;
import com.jk.asset.model.dto.proceeding.RecoveryLitigationExtDTO;
import com.jk.asset.model.request.proceeding.RecoveryLitigationExtRequest;
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
 * 诉讼反写信息扩展表接口
 *
 * @author wangshuai
 * @since 2024-07-04 10:35:25
 */
public interface RecoveryLitigationExtService {

  String CONTEXT = "/recovery/litigation/ext";

  /**
   * 新增
   *
   * @param request 诉讼反写信息扩展表入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-04 10:35:25
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<RecoveryLitigationExtDTO> add(@RequestBody RecoveryLitigationExtRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-04 10:35:25
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 诉讼反写信息扩展表入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-04 10:35:25
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<RecoveryLitigationExtDTO> update(@RequestBody RecoveryLitigationExtRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-04 10:35:25
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<RecoveryLitigationExtDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 诉讼反写信息扩展表分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-04 10:35:25
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<RecoveryLitigationExtDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody RecoveryLitigationExtPageRequest request);

}