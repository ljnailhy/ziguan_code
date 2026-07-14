package com.jk.asset.service;

import com.jk.asset.model.dto.AssetAnalyseDTO;
import com.jk.asset.model.dto.OverviewPastProjectsDTO;
import com.jk.asset.model.dto.ProjectDateDTO;
import com.jk.asset.model.dto.ProjectStatusDTO;
import com.jk.asset.model.dto.RecoveryStatisticsDTO;
import com.jk.asset.model.dto.WriteOffClassificationDTO;
import com.jk.common.model.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author wangTao
 * date2024/7/15 9:40
 **/
public interface HomePageStatisticsService {

    String CONTEXT = "/home/page";

    /**
     * 首页 统计
     * @return RecoveryStatisticsDTO
     */
    @ApiOperation(value = "统计项目-statistics", notes = "统计项目")
    @GetMapping(CONTEXT +"/statistics")
    Result<RecoveryStatisticsDTO> statistics();

    /**
     * 首页 历年项目概况
     * @return RecoveryStatisticsDTO
     */
    @ApiOperation(value = "历年项目概况-overviewPastProjects", notes = "历年项目概况")
    @GetMapping(CONTEXT + "/overview/past/projects")
    Result<List<OverviewPastProjectsDTO>> overviewPastProjects();

    /**
     * 首页 临期预警
     * @return RecoveryStatisticsDTO
     */
    @ApiOperation(value = "临期预警-DeadlineWarning", notes = "临期预警")
    @GetMapping(CONTEXT + "/deadline/warning")
    Result<ProjectDateDTO> deadlineWarning();

    /**
     * 首页 核销项目分类
     * @return RecoveryStatisticsDTO
     */
    @ApiOperation(value = "核销项目分类-WriteOffClassification", notes = "核销项目分类")
    @GetMapping(CONTEXT + "/write/off/classification")
    Result<List<WriteOffClassificationDTO>> writeOffClassification();

    /**
     * 首页 资产分析
     * @return RecoveryStatisticsDTO
     */
    @ApiOperation(value = "资产分析-assetAnalyse", notes = "资产分析")
    @GetMapping(CONTEXT + "/asset/analyse")
    Result<AssetAnalyseDTO> assetAnalyse();

    /**
     * 首页 诉讼阶段统计
     * @return RecoveryStatisticsDTO
     */
    @ApiOperation(value = "诉讼阶段统计- proceedStatistics", notes = "诉讼阶段统计")
    @GetMapping(CONTEXT + "/proceed/statistics")
    Result<ProjectStatusDTO> proceedStatistics();

}
