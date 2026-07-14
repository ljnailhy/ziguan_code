package com.jk.asset.service;

import com.jk.asset.model.dto.LawyerInfoDTO;
import com.jk.common.model.Result;
import com.jk.asset.model.dto.BillLawyerDTO;
import com.jk.asset.model.request.BillLawyerRequest;
import com.jk.asset.model.request.page.BillLawyerPageRequest;
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
 * 单据与律师关系表接口
 *
 * @author wangshuai
 * @since 2024-06-28 11:49:45
 */
public interface BillLawyerService {

  String CONTEXT = "/bill/lawyer";

  /**
   * 新增
   *
   * @param request 单据与律师关系表入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 11:49:45
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<BillLawyerDTO> add(@RequestBody BillLawyerRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 11:49:45
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 单据与律师关系表入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 11:49:45
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<BillLawyerDTO> update(@RequestBody BillLawyerRequest request);

  /**
   * 单个查找
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 11:49:45
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<BillLawyerDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 根据doType和doId查询律师
   * @param doId  对象iD
   * @param doType  对象iD
   * @return com.jk.common.model.Result<java.util.List<com.jk.asset.model.dto.BillLawyerDTO>>
   * @author wangshuai
   * @since 2024/7/1 10:37
   **/
  @ApiOperation(value = "根据doType和doId查询关联律师-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{doType}/{doId}")
  Result<List<Long>> findByDoId(@ApiParam("对象iD") @PathVariable Long doId,@ApiParam("对象类型") @PathVariable String doType);

  @ApiOperation(value = "根据doType和doId查询历史律师-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/history/{doType}/{doId}")
  Result<List<BillLawyerDTO>> findHistoryByDoId(@ApiParam("对象iD") @PathVariable Long doId,@ApiParam("对象类型") @PathVariable String doType);

  /**
   * 根据doType和doId修改律师
   * @param doId  对象iD
   * @param doType  对象iD
   * @param idList  律师ids
   * @return com.jk.common.model.Result<java.util.List<com.jk.asset.model.dto.LawyerInfoDTO>>
   * @author wangshuai
   * @since 2024/7/1 11:49
   **/
  @ApiOperation(value = "根据doType和doId修改律师-addOrUpdateBatch", notes = "详细描述")
  @PostMapping(CONTEXT + "/batchUpdate/{doType}/{doId}")
  Result<?>  batchUpdata(@ApiParam("对象iD") @PathVariable Long doId,@ApiParam("对象iD") @PathVariable String doType,@RequestBody List<Long> idList);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 单据与律师关系表分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 11:49:45
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<BillLawyerDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody BillLawyerPageRequest request);

}