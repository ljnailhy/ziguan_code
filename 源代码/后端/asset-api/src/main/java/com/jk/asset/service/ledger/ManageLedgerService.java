package com.jk.asset.service.ledger;

import com.jk.asset.model.dto.CompensatoryCountDTO;
import com.jk.asset.model.dto.ManageLedgerDTO;
import com.jk.asset.model.dto.ManageLedgerDetailDTO;
import com.jk.asset.model.dto.ManageLedgerProjectDTO;
import com.jk.asset.model.dto.PaymentCollectionPageDTO;
import com.jk.asset.model.dto.ProjectLedgerDTO;
import com.jk.asset.model.request.ExportCompensatoryRequest;
import com.jk.asset.model.request.page.ManagePageRequest;
import com.jk.asset.model.request.page.PaymentCollectionLedgerPageRequest;
import com.jk.asset.model.request.page.ProjectInfoPageRequest;
import com.jk.common.model.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 数据台账
 * @author wangTao
 * date2024/7/16 9:57
 **/
public interface ManageLedgerService {


    String CONTEXT = "/ledger";

    /**
     * 保全经理台账分页查找
     *
     * @param current 当前页码
     * @param size 每页大小
     * @param request 保全经理台账分页 入参
     * @return com.jk.common.model.Result
     * @author wangtao
     * @since 2024-06-24 09:41:55
     */
    @ApiOperation(value = "保全经理台账分页查找-findManageAll", notes = "详细描述")
    @PostMapping(CONTEXT + "/manage")
    Result<List<ManageLedgerDTO>> findManageAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
                                          @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
                                          @RequestBody ProjectInfoPageRequest request);

    /**
     * 保全经理台账导出
     *
     * @param request 入参
     * @param response 响应
     * @author Yuqiang Wu
     * @since 2024/8/9 009 15:39
     */
    @ApiOperation(value = "保全经理台账导出", notes = "详细描述")
    @PostMapping(CONTEXT + "/manage/export")
    void exportManage(@RequestBody ProjectInfoPageRequest request, HttpServletResponse response);

    /**
     * 保全经理详情
     *
     * @param id 保全经理详情 入参
     * @return com.jk.common.model.Result
     * @author wangtao
     * @since 2024-06-24 09:41:55
     */
    @ApiOperation(value = "保全经理详情-findByManageId", notes = "保全经理详情")
    @PostMapping(CONTEXT + "/findByManageId")
    Result<ManageLedgerDetailDTO> findByManageId(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
                                                 @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
                                                 @RequestBody ManagePageRequest request);

    /**
     * 回款台账分页查找
     *
     * @param current 当前页码
     * @param size 每页大小
     * @param request 回款台账分页 入参
     * @return com.jk.common.model.Result
     * @author wangtao
     * @since 2024-06-24 09:41:55
     */
    @ApiOperation(value = "回款台账分页查找-findPaymentCollectionAll", notes = "详细描述")
    @PostMapping(CONTEXT + "/payment/collection")
    Result<List<PaymentCollectionPageDTO>> findPaymentCollectionAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
                                                                    @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
                                                                    @RequestBody PaymentCollectionLedgerPageRequest request);

    /**
     * 回款台账导出
     *
     * @param request 入参
     * @param response 响应
     * @author Yuqiang Wu
     * @since 2024/8/9 009 15:39
     */
    @ApiOperation(value = "回款台账导出", notes = "详细描述")
    @PostMapping(CONTEXT + "/payment/collection/export")
    void exportPaymentCollection(@RequestBody PaymentCollectionLedgerPageRequest request, HttpServletResponse response);

    /**
     * 项目台账分页查找
     *
     * @param current 当前页码
     * @param size 每页大小
     * @param request 项目台账分页 入参
     * @return com.jk.common.model.Result
     * @author wangtao
     * @since 2024-06-24 09:41:55
     */
    @ApiOperation(value = "项目台账分页查找-findProjectLedgerAll", notes = "项目台账")
    @PostMapping(CONTEXT + "/project")
    Result<List<ProjectLedgerDTO>> findProjectLedgerAll(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
                                                        @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
                                                        @RequestBody ProjectInfoPageRequest request);
    /**
     * 项目台账导出
     *
     * @param request 项目台账导出 入参
     * @author wangtao
     * @since 2024-06-24 09:41:55
     */
    @ApiOperation(value = "项目台账导出", notes = "项目台账导出")
    @PostMapping(CONTEXT + "/project/export")
    void exportProjectLedger(@RequestBody ProjectInfoPageRequest request, HttpServletResponse response);

    /**
     * 保全经理在管项目分页查找
     *
     * @param current 当前页码
     * @param size 每页大小
     * @param request 保全经理在管项目 入参
     * @return com.jk.common.model.Result
     * @author wangtao
     * @since 2024-06-24 09:41:55
     */
    @ApiOperation(value = "保全经理在管项目分页查找-findMangeDetail", notes = "保全经理在管项目")
    @PostMapping(CONTEXT + "/find/mangeDetail")
    Result<List<ManageLedgerProjectDTO>> findManageProject(@ApiParam("当前页码") @RequestParam(defaultValue = "1") Integer current,
                                                                  @ApiParam("每页大小") @RequestParam(defaultValue = "50") Integer size,
                                                                  @RequestBody ManagePageRequest request);

    /**
     * 追偿情况统计
     *
     * @param request 追偿情况统计 入参
     * @author wangtao
     * @since 2024-06-24 09:41:55
     */
    @ApiOperation(value = "追偿情况统计", notes = "追偿情况统计")
    @GetMapping(CONTEXT + "/project/count")
    Result<List<CompensatoryCountDTO>>  findCompensatoryCountByYear(@ApiParam("年度") @Param("year") Integer request);

    /**
     * 追偿情况统计导出
     *
     * @param request 追偿情况统计导出 入参
     * @author wangtao
     * @since 2024-06-24 09:41:55
     */
    @ApiOperation(value = "追偿情况统计导出", notes = "追偿情况统计导出")
    @PostMapping(CONTEXT + "/compensatory/count/export")
    void exportCompensatoryCount(@RequestBody ExportCompensatoryRequest request, HttpServletResponse response);
}
