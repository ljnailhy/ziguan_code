package com.jk.asset.model.request.property;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.infrastructure.model.request.sys.file.SysFileRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资产信息入参
 *
 * @author wangshuai
 * @since 2024-07-10 09:47:37
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "资产信息入参")
public class PropertyInfoRequest {

  @ApiModelProperty("主键")
  private Long id;

  @ApiModelProperty("资产标签")
  private String propertyTag;

    @ApiModelProperty("资产状态")
  private PropertyStateEnum propertyState;

  @ApiModelProperty("资产名称")
  private String propertyName;

  @ApiModelProperty("大类")
  private Long type;

  @ApiModelProperty("资产分类")
  private Long propertyType;

  @ApiModelProperty("来源方式")
  private Long sourceType;

  @ApiModelProperty("项目id")
  private Long projectId;

  @ApiModelProperty("来源项目")
  private String projectName;

  @ApiModelProperty("反担保id")
  private Long reveId;

  @ApiModelProperty("资产地址_省")
  private Long province;

  @ApiModelProperty("资产地址_市")
  private Long city;

  @ApiModelProperty("资产地址_区")
  private Long district;

  @ApiModelProperty("详细地址")
  private String address;

  @ApiModelProperty("资产标签")
  private String label;

  @ApiModelProperty("资产取得方式")
  private Long accessWay;

  @ApiModelProperty("资产描述")
  private String propertyDescribe;

  @ApiModelProperty("面积")
  private BigDecimal area;

  @ApiModelProperty("资产用途")
  private String assetUse;

  @ApiModelProperty("产权证号")
  private String propertyCode;

  @ApiModelProperty("权证到期日")
  private Date propertyEndDate;

  @ApiModelProperty("资产过户日期")
  private Date propertyTransferOwnership;

  @ApiModelProperty("资产备注")
  private String transferOwnershipRemark;

  @ApiModelProperty("资产抵债价格(元)")
  private BigDecimal debtRepaymentMoney;

  @ApiModelProperty("资产原值(元)")
  private BigDecimal originalValue;

  @ApiModelProperty("资产净值(元)")
  private BigDecimal netWorth;

  @ApiModelProperty("资产处置价格(元)")
  private BigDecimal disposalPrice;

  @ApiModelProperty("税费(元)")
  private BigDecimal taxeFee;

  @ApiModelProperty("处置资产支付税费")
  private BigDecimal disposeFee;

  @ApiModelProperty("原债务人费用(元)")
  private BigDecimal originalObligorFee;

  @ApiModelProperty("资产盈亏(元)")
  private BigDecimal profitAndLoss;

  @ApiModelProperty("资产跟进人")
  private String followUpPerson;

  @ApiModelProperty("产权人名称")
  private String propertyOwner;

  @ApiModelProperty("资产获得时间")
  private Date assertTime;

  @ApiModelProperty("是否拆分")
  private boolean separable;

  @ApiModelProperty("所属单位")
  private Long affiliatedUnit;

  @ApiModelProperty("用地性质（国有出让地，国有划拨地）")
  private Long landUseNature;

  @ApiModelProperty("附件信息")
  private List<SysFileRequest> fileInfoList;

  @ApiModelProperty("资产图片信息")
  private List<SysFileRequest> propertyPictureList;

  @ApiModelProperty("产权信息")
  private List<PropertyRightInfoRequest> propertyRightInfoList;

  @ApiModelProperty("运营费用")
  private BigDecimal yyMoney;

  @ApiModelProperty("转让收入")
  private BigDecimal zrMoney;

  @ApiModelProperty("租赁收入")
  private BigDecimal zlMoney;


  /**
   * 参数校验不通过
   *
   * @return boolean
   * @author wangshuai
   * @since 2024-07-10 09:47:37
   */
  // @Override
  public boolean unverified() {
    // String校验：空org.apache.commons.lang3.StringUtils.isBlank 非空isNotBlank 批量-是否有空isAnyBlank 批量-都不空isNoneBlank
    // List校验：空org.apache.commons.collections4.CollectionUtils.isEmpty 非空isNotEmpty
    // 其他Object(或List)：空org.apache.commons.lang3.ObjectUtils.isEmpty 非空isNotEmpty 批量-都不空allNotNull 批量-都空allNull 批量-是否有不空anyNotNull 批量-是否有空anyNull
    // return super.unverified();
    return false;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }

  /**
   * " '闲置': 'IDLE' '部分闲置': 'PART_IDLE' '占用': 'OCCUPY' '部分占用': 'PART_OCCUPY'
   * '已出租': 'LEASED' '部分转让':'PART_TRANSFER' '已转让': 'TRANSFERRED' '自用': 'SELF_USE' '部分租赁': 'PART_LEASE''在途': 'IN_TRANSIT"
   * @author wangshuai
   * @since 2024-07-10 09:47:37
   */
  @Getter
  public enum PropertyStateEnum {
    IDLE("IDLE","闲置"),
    SELF_USE("SELF_USE","自用"),
    LEASED("LEASED","已出租"),
    TRANSFERRED("TRANSFERRED","已转让"),
    PART_IDLE("PART_IDLE","待办证"),
    OCCUPY("OCCUPY","被占用"),
    PART_LEASE("PART_LEASE","部分租赁"),
    PART_TRANSFER("PART_TRANSFER","部分转让"),



    ;

    /** 键 */
    private final String key;
    /** 值 */
    private final String value;

    PropertyStateEnum(String key, String value) {
      this.key = key;
      this.value = value;
    }

    /** 保存key value对的map */
    private static final Map<String, String> MAP = new HashMap<>();

    static {
      PropertyStateEnum[] enums = PropertyStateEnum.values();
      for (PropertyStateEnum objEnum : enums) {
        MAP.put(objEnum.getKey(), objEnum.getValue());
      }
    }

    /**
     * 根据key获得name
     *
     * @param key 键
     * @return 值
     */
    public static String getValue(String key) {
      return PlatformStringUtils.isNotBlank(key) ? MAP.get(key) : PlatformStringUtils.EMPTY;
    }
  }
}
