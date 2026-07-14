package com.jk.asset.model.dto;

import com.jk.asset.model.dto.property.PropertyInfoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangTao
 * date2024/8/30 17:23
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class PropertyInfoRemindDTO extends PropertyInfoDTO {

    private Long doId;

}
