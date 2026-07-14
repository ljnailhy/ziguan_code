package com.jk.asset.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.asset.model.request.property.PropertyInfoRequest;
import com.jk.common.model.entity.BaseTenantDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author wangTao
 * date2024/8/30 16:18
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationInfoRemindDTO extends BaseTenantDO {

    @ApiModelProperty("对象Id")
    private Long doId;

    @ApiModelProperty("对象类型")
    private String doType;

    @ApiModelProperty("资产id")
    private Long propertyId;

    @ApiModelProperty("资产状态")
    private PropertyInfoRequest.PropertyStateEnum propertyState;

    @ApiModelProperty("资产标签")
    private String propertyTag;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("提醒时间")
    private Date operationDate;

    @ApiModelProperty("资产跟进人")
    private String followUpPerson;

    @ApiModelProperty("资产名称")
    private String propertyName;
}
