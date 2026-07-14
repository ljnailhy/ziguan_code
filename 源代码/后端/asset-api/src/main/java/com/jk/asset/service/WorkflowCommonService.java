package com.jk.asset.service;

import com.jk.common.model.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 流程引擎公共调用方法
 *
 * @author WangShuai
 * @since 2024/8/5 16:18
 **/
public interface WorkflowCommonService {


  String CONTEXT = "/workflow/public";

  /**
   * 删除
   *
   * @param billId 单据主键
   * @param billType 单据类型
   * @param state 流程状态
   * @return com.jk.common.model.Result
   * @author wangshuai
   * @since 2024-06-28 17:23:16
   */
  @ApiOperation(value = "删除-delete", notes = "详细描述")
  @DeleteMapping(CONTEXT + "/{billId}/{billType}/{state}")
  Result<?> writeBackState(@ApiParam("单据主键") @PathVariable Long billId,@ApiParam("单据类型")  @PathVariable String billType,@ApiParam("流程状态")  @PathVariable String state);

}
