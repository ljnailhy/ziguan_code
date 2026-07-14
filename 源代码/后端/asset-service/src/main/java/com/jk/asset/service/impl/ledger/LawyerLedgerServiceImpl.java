package com.jk.asset.service.impl.ledger;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.mapper.ledger.LawyerLedgerMapper;
import com.jk.asset.model.dto.LawyerLedgerDTO;
import com.jk.asset.model.request.page.LawFirmInfoPageRequest;
import com.jk.asset.service.ledger.LawyerLedgerService;
import com.jk.asset.utils.excel.ExcelUtil;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformStringUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.math.RoundingMode;
import java.util.List;

/**
 * @author wangTao
 * date2024/7/16 10:13
 **/
@RestController
@Slf4j
@Api(tags = "律所台账接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LawyerLedgerServiceImpl implements LawyerLedgerService {
    private final LawyerLedgerMapper lawyerLedgerMapper;

    @Override
    public Result<List<LawyerLedgerDTO>> findAll(Integer current, Integer size, LawFirmInfoPageRequest request) {
        if (PlatformStringUtils.isNotBlank(request.getField())) {
            request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
        }
        // Step1：创建一个 Page 对象
        IPage<LawyerLedgerDTO> page = new Page<>(current, size);
        // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
        List<LawyerLedgerDTO> doList = lawyerLedgerMapper.findAll(page, request);
        // 空则直接返回
        if (CollectionUtils.isEmpty(doList)) {
            return Result.success(Lists.newArrayList(), PageFactory.page(page));
        }
        doList.forEach(item ->{
            if (item.getCollectionRatio() !=null){
                item.setCollectionRatio(item.getCollectionRatio().setScale(2, RoundingMode.HALF_UP));
            }
        });
        return Result.success(doList, PageFactory.page(page));
    }

    @Override
    public void export(LawFirmInfoPageRequest request, HttpServletResponse response) {
        // Step1：创建一个 Page 对象
        IPage<LawyerLedgerDTO> page = new Page<>(1, 10000);
        // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
        List<LawyerLedgerDTO> doList = lawyerLedgerMapper.findAll(page, request);
        if (ObjectUtils.isEmpty(doList)) {
            return;
        }
        String fileName = "律所台账";
        ExcelUtil.exportExcel(doList, fileName, LawyerLedgerDTO.class, response);
    }
}
