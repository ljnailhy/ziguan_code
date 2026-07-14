package com.jk.asset.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author wangTao
 * date2024/6/29 19:47
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "代偿api请求参数")
public class ApiDataDTO {
        private String statisticsCode;
        private Integer pageCount;
        private Integer pageIndex;
        Map<String,Object> dataMap;
        private String  sessionId;
}
