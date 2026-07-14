package com.jk.asset.service.ledger;

import com.jk.asset.model.dto.LawyerLedgerDTO;
import com.jk.asset.model.request.page.LawFirmInfoPageRequest;
import com.jk.common.model.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 律所台账
 * @author wangTao
 * date2024/7/16 9:57
 **/
public interface LawyerLedgerService {

    String CONTEXT = "/lawyer/ledger";

    /**
     * 分页查找
     *
     * @param current 当前页码
     * @param size 每页大小
     * @param request 分配/变更主表分页入参
     * @return com.jk.common.model.Result
     * @author wangshuai
     * @since 2024-06-28 10:13:53
     */
    @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
    @PostMapping(CONTEXT + "s")
    Result<List<LawyerLedgerDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
                                          @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
                                          @RequestBody LawFirmInfoPageRequest request);

    /**
     * 律所台账导出
     *
     * @param request 入参
     * @param response 响应
     * @author Yuqiang Wu
     * @since 2024/8/9 009 15:39
     */
    @ApiOperation(value = "律所台账导出", notes = "详细描述")
    @PostMapping(CONTEXT + "/export")
    void export(@RequestBody LawFirmInfoPageRequest request, HttpServletResponse response);
}
