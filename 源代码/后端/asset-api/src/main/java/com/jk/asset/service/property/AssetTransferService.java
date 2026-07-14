package com.jk.asset.service.property;

import com.jk.asset.model.dto.property.LeaseInfoDTO;
import com.jk.asset.model.dto.property.OperationInfoDTO;
import com.jk.asset.model.request.property.LeaseInfoRequest;
import com.jk.common.model.Result;
import com.jk.asset.model.dto.property.AssetTransferDTO;
import com.jk.asset.model.request.property.AssetTransferRequest;
import com.jk.asset.model.request.page.property.AssetTransferPageRequest;
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
 * 资产转让接口
 *
 * @author wangshuai
 * @since 2024-07-13 09:53:13
 */
public interface AssetTransferService {

  String CONTEXT = "/asset/transfer";

  /**
   * 新增
   *
   * @param request 资产转让入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-13 09:53:13
   */
  @ApiOperation(value = "新增-add", notes = "详细描述")
  @PostMapping(CONTEXT)
  Result<AssetTransferDTO> add(@RequestBody AssetTransferRequest request);

  /**
   * 删除
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-13 09:53:13
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{id}")
  Result<?> delete(@ApiParam("主键") @PathVariable Long id);

  /**
   * 修改
   *
   * @param request 资产转让入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-13 09:53:13
   */
  @ApiOperation(value = "修改-update", notes = "详细描述")
  @PutMapping(CONTEXT)
  Result<AssetTransferDTO> update(@RequestBody AssetTransferRequest request);

  /**
   * 修改
   * @param request 资产转让入参
   * @return com.jk.common.model.Result<com.jk.asset.model.dto.property.AssetTransferDTO>
   * @author wangshuai
   * @since 2024/7/13 15:58
   **/
  @ApiOperation(value = "提交-submit", notes = "详细描述")
  @PutMapping(CONTEXT+"/submit")
  Result<AssetTransferDTO> submit(@RequestBody AssetTransferRequest request);

  @ApiOperation(value = "反写相关信息-writeBackProject", notes = "详细描述")
  @GetMapping(CONTEXT + "/writeBack/{id}")
  Result<AssetTransferDTO> writeBackProject(@ApiParam("主键") @PathVariable Long id);

  /**
   * 获取网关变量
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-13 09:53:13
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/variables/{id}")
  Result<Map<String,Object>> getFlowVariables(@ApiParam("主键") @PathVariable Long id);

  /**
   * 获取流程变量
   *
   * @param id 主键
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-13 09:53:13
   */
  @ApiOperation(value = "单个查找-findById", notes = "详细描述")
  @GetMapping(CONTEXT + "/{id}")
  Result<AssetTransferDTO> findById(@ApiParam("主键") @PathVariable Long id);

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 资产转让分页入参
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-07-13 09:53:13
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<AssetTransferDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody AssetTransferPageRequest request);

}