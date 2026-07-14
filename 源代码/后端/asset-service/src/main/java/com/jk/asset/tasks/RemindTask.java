package com.jk.asset.tasks;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jk.asset.mapper.property.LeaseInfoMapper;
import com.jk.asset.model.entity.property.OperationInfo;
import com.jk.asset.service.handler.property.OperationInfoHandler;
import com.jk.workflow.enums.ProcessStatus;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 各类提醒定时任务
 * @author wangTao
 * date2024/8/13 9:19
 **/
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RemindTask {

    private final LeaseInfoMapper leaseInfoMapper;
    private final OperationInfoHandler operationInfoHandler;

    /**
     * 	用于资产运营登记。资产运营提示：上一次的运营时间往后推2个月作为提醒时间节点，每隔5天提醒一次资产跟进人
     */
    @XxlJob("operationInfoWarning")
    public void operationInfoWarning(){
        String title = "资产运营登记";
        List<OperationInfo> operationInfos = operationInfoHandler.list(new LambdaQueryWrapper<OperationInfo>()
                .eq(OperationInfo::getFlowState, ProcessStatus.completed));
    }

    /**
     * 租赁提醒：租赁缴费通知提示，提前一个月提醒资产跟进人
     */
    @XxlJob("leaseWarning")
    public void leaseWarning(){
        // 'YEAR'：年,'HALF_YEAR'：半年,'SEASON'：季,'MONTH'：月,'DISPOSABLE'
    }
}
