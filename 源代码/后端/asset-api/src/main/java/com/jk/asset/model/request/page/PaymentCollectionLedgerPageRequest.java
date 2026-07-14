package com.jk.asset.model.request.page;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 项目信息表分页入参
 *
 * @author wangtao
 * @since 2024-06-24 09:41:55
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "项目信息表分页入参")
public class PaymentCollectionLedgerPageRequest {

  @ApiModelProperty("创建时间范围")
  private List<Date> createStampRange;

  @ApiModelProperty("项目名称")
  private String projectName;

  @ApiModelProperty("律所")
  private Long lawyerName;

  @ApiModelProperty("回款标记")
  private String collectionSign;

  @ApiModelProperty("回款归属")
  private String collectionAscription;

  @ApiModelProperty("回款时间")
  private List<Date> collectionDateRange;

  @ApiModelProperty("用户权限")
  private Set<Long> orgUserIds;

  @ApiModelProperty("回款状态")
  private Long status;

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
