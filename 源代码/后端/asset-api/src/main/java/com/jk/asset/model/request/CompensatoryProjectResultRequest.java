package com.jk.asset.model.request;

/**
 * @author wangTao
 * date2024/7/1 9:33
 **/

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wangTao
 * date2024/6/29 18:59
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "代偿项目api 返回参数")
public class CompensatoryProjectResultRequest {
    private Data data;
    private Integer totalCount;

    @lombok.Data
    public static class Data{
        private List<SyncCompensatoryProjectRequest> queryList;
        private List<String> totalList;
    }
}
