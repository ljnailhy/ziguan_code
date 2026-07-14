package com.jk.asset.service;

import com.jk.asset.model.dto.LawyerInfoDTO;
import com.jk.asset.model.request.LawyerInfoRequest;
import com.jk.asset.model.request.page.LawyerInfoPageRequest;
import com.jk.common.model.Result;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
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
 * 律师团队接口
 *
 * @author wangshuai
 * @since 2024-06-24 11:09:49
 */
public interface LawyerInfoService {

  String CONTEXT = "/lawyer/info";

  /**
   * 新增
   *
   * @param request 律师团队入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-24 11:09:49
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<LawyerInfoDTO> add(@RequestBody LawyerInfoRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-24 11:09:49
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 律师团队入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-24 11:09:49
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<LawyerInfoDTO> update(@RequestBody LawyerInfoRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-24 11:09:49
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<LawyerInfoDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 批量查询律师信息
   * @param idList  律师id
   * @return com.jk.common.model.Result<java.util.List<com.jk.asset.model.dto.LawyerInfoDTO>>
   * @author wangshuai
   * @since 2024/6/28 18:41
   **/
  @ApiOperation(value = "批量查询律师信息-addOrUpdateBatch", notes = "详细描述")
  @PostMapping(CONTEXT + "/batch")
  Result<List<LawyerInfoDTO>>  findByIds(@RequestBody List<Long> idList);

  /**
   * 根据律所id查询律师团队
   *
   * @param lawFirmId 律所id
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-24 11:09:49
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/LawFirm/{lawFirmId}")
  Result<List<LawyerInfoDTO>> findByLawFirmId(@ApiParam("律所id") @PathVariable Long lawFirmId);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 律师团队分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-24 11:09:49
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<LawyerInfoDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody LawyerInfoPageRequest request);

}