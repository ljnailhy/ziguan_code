package com.jk.asset.service;

import com.jk.asset.model.dto.CompensatorySyncDTO;
import com.jk.asset.model.request.page.CompensatorySyncPageRequest;
import com.jk.common.model.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 项目同步接收表接口
 *
 * @author wangtao
 * @since 2024-07-25 16:40:16
 */
public interface CompensatorySyncService {

  String CONTEXT = "/compensatory/sync";

  /**
   * 分页查找
   *
   * @param current 当前页码
   * @param size 每页大小
   * @param request 项目同步接收表分页入参
   * @return com.jk.common.model.Result
   * @author wangtao
   * @since 2024-07-25 16:40:16
   */
  @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
  @PostMapping(CONTEXT + "s")
  Result<List<CompensatorySyncDTO>> findCompensatorySyncAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
      @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
      @RequestBody CompensatorySyncPageRequest request);

}