package com.jk.asset.service.ledger;

import com.jk.asset.model.dto.AssetDetailLedgerDTO;
import com.jk.asset.model.request.page.property.PropertyRightInfoPageRequest;
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
public interface AssetDetailLedgerService {

    String CONTEXT = "/asset/detail/ledger";

    /**
     * 分页查找
     *
     * @param current 当前页码
     * @param size 每页大小
     * @param request 资产明细分页入参
     * @return com.jk.common.model.Result
     * @author wangTao
     * @since 2024-06-28 10:13:53
     */
    @ApiOperation(value = "分页查找-findAll", notes = "详细描述")
    @PostMapping(CONTEXT + "s")
    Result<List<AssetDetailLedgerDTO>> findAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
                                               @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
                                               @RequestBody PropertyRightInfoPageRequest request);

    /**
     * 资产明细台账导出
     *
     * @param request 资产明细台账导出 入参
     * @author wangtao
     * @since 2024-06-24 09:41:55
     */
    @ApiOperation(value = "资产明细台账导出", notes = "资产明细台账导出")
    @PostMapping(CONTEXT + "/export")
    void exportProjectLedger(@RequestBody PropertyRightInfoPageRequest request, HttpServletResponse response);
}
