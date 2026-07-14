package com.jk.asset.tasks;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jk.asset.constant.AssertConstants;
import com.jk.asset.model.dto.ApiDataDTO;
import com.jk.asset.model.dto.CompensatoryProjectDTO;
import com.jk.asset.model.entity.CompensatorySync;
import com.jk.asset.model.request.CompensatoryProjectResultRequest;
import com.jk.asset.model.request.SyncCompensatoryProjectRequest;
import com.jk.asset.service.handler.CompensatorySyncHandler;
import com.jk.common.model.Result;
import com.jk.infrastructure.model.dto.SysRegionDTO;
import com.jk.infrastructure.model.request.sys.region.SysRegionPageRequest;
import com.jk.service.client.SysRegionClient;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wangTao
 * date2024/7/25 16:49
 * 项目同步数据导入 定时数据导入数据库
 **/
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompensatorySyncTask {

    private final RestTemplate restTemplate;
    @Value("${thirdParty.compensatory.domain:}")
    private String domain;
    @Value("${thirdParty.compensatory.api.url}")
    private String apiUrl;
    private final CompensatorySyncHandler compensatorySyncHandler;
    private final SysRegionClient sysRegionClient;

    @GlobalTransactional(noRollbackFor = Exception.class)
    @XxlJob("compensatorySync")
    public void compensatorySync() {
        log.info("定时 项目同步数据导入 开始");
        XxlJobHelper.log("定时 项目同步数据导入 开始");
        CompensatoryProjectDTO send = new CompensatoryProjectDTO();
        send.setService(AssertConstants.API_SERVICE);
        send.setVersion(AssertConstants.API_VERSION);
        ApiDataDTO apiData = new ApiDataDTO();
        apiData.setStatisticsCode(AssertConstants.API_STATISICS_CDOE);
        apiData.setSessionId(AssertConstants.API_SESSION_ID);
        Map<String, Object> map = new HashedMap<>();
        apiData.setDataMap(map);
        send.setData(apiData);
        String json = JSON.toJSONString(send);
        JSONObject jsonObject = null;
        try {
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
            jsonObject = restTemplate.postForObject(String.format("%s%s", domain, apiUrl), json, JSONObject.class);
        } catch (Exception e) {
            log.error("调用代偿台账统计异常 ->{}", e.getMessage());
        }
        CompensatoryProjectResultRequest resultRequest = JSONObject.toJavaObject((JSON) JSON.toJSON(jsonObject.get("result")), CompensatoryProjectResultRequest.class);
        if (ObjectUtils.isEmpty(resultRequest)) {
            return;
        }
        List<SyncCompensatoryProjectRequest> queryList = resultRequest.getData().getQueryList();
        if (ObjectUtils.isEmpty(queryList)) {
            return;
        }
        List<CompensatorySync> syncList = convertRequestToEntity(queryList);
        LambdaQueryWrapper<CompensatorySync> oldQuery = new LambdaQueryWrapper<>();
        oldQuery.select(CompensatorySync::getBusinessNo);
        List<CompensatorySync> list = compensatorySyncHandler.list(oldQuery);
        if (ObjectUtils.isEmpty(list)) {
//            int batchSize = 500;
//            for (int i = 0; i < syncList.size(); i += batchSize) {
//                List<CompensatorySync> sublist = syncList.subList(i, Math.min(i + batchSize, syncList.size()));
//                compensatorySyncHandler.saveBatch(sublist, batchSize);
//            }
            compensatorySyncHandler.saveBatch(syncList);
            log.info("开始执行");
            return;
        }
        List<String> collect = list.stream().map(CompensatorySync::getBusinessNo).collect(Collectors.toList());
        List<CompensatorySync> collect1 = syncList.stream().filter(a -> !collect.contains(a.getBusinessNo())).collect(Collectors.toList());
        if (!ObjectUtils.isEmpty(collect1)) {
            compensatorySyncHandler.saveBatch(collect1);
        }
        XxlJobHelper.log("定时 项目同步数据导入 完成");
    }

    private List<CompensatorySync> convertRequestToEntity(List<SyncCompensatoryProjectRequest> queryList) {
        List<CompensatorySync> resList = new ArrayList<>();
        for (SyncCompensatoryProjectRequest request : queryList) {
            CompensatorySync compensatorySync = new CompensatorySync();
            compensatorySync.setBusinessNo(request.getBusiness_no());
            compensatorySync.setBusinessType(request.getBusiness_type());
            compensatorySync.setProductName(request.getPRODUCT_NAME());
            compensatorySync.setCustomerName(request.getCUSTOMER_NAME());
            compensatorySync.setCustomerProperty(request.getCustomer_Property());
            compensatorySync.setCredentialType(request.getCREDENTIAL_TYPE());
            compensatorySync.setCredentialNo(request.getCREDENTIAL_NO());
            compensatorySync.setIsFirstLoanAccount(request.getIS_FIRST_LOAN_ACCOUNT());
            compensatorySync.setProjectFrom(request.getPROJECT_FROM());
            compensatorySync.setCooperativeBank(request.getCOOPERATIVE_BANK());
            compensatorySync.setCooperativeBankThird(request.getCooperative_bank_third());
            compensatorySync.setIndustryGxb(request.getINDUSTRY_GXB());
            compensatorySync.setIndustryJrj(request.getINDUSTRY_JRJ());
            compensatorySync.setIndustryGxw(request.getINDUSTRY_GXW() == null ? null : JSON.toJSONString(request.getINDUSTRY_GXW()));
            compensatorySync.setEmployedPopulation(request.getEMPLOYED_POPULATION());
            compensatorySync.setTotalAssets(request.getTOTAL_ASSETS());
            compensatorySync.setBusinessRevenue(request.getBUSINESS_REVENUE());
            compensatorySync.setTaxAmount(request.getTaxAmount());
            compensatorySync.setEnterpriseSize(request.getENTERPRISE_SIZE());
            compensatorySync.setIndustryPolicySupport(request.getINDUSTRY_POLICY_SUPPORT());
            compensatorySync.setDebtBillStartDay(request.getDEBT_BILL_START_DAY());
            compensatorySync.setDebtBillDueDay(request.getDEBT_BILL_DUE_DAY());
            compensatorySync.setDebtAmount(request.getDebt_amount());
            compensatorySync.setLoanRate(request.getLOAN_RATE());
            compensatorySync.setPrincipleClaimContractNo(request.getPrinciple_claim_contract_no());
            compensatorySync.setWarrantyContractNo(request.getWarranty_contract_no());
            compensatorySync.setGuaranteeContractNo(request.getGUARANTEE_CONTRACT_NO());
            compensatorySync.setDebtBillNo(request.getDEBT_BILL_NO());
            compensatorySync.setGuaranteeFeeRate(request.getGUARANTEE_FEE_RATE());
            compensatorySync.setCounterGuaranteeType(request.getCOUNTER_GUARANTEE_TYPE());
            compensatorySync.setRistTypeAfterGuarantee(request.getRIST_TYPE_AFTER_GUARANTEE());
            compensatorySync.setRiskRateBank(request.getRisk_rate_bank());
            compensatorySync.setRiskRateOrigin(request.getRisk_rate_origin());
            compensatorySync.setRiskRateAgain(request.getRisk_rate_again());
            compensatorySync.setRiskRateOther(request.getRisk_rate_other());
            compensatorySync.setRemark(request.getRemark());
            compensatorySync.setRelationEnterprise(request.getRelation_enterprise());
            compensatorySync.setRelationEnterpriseNo(request.getRelation_enterprise_no());
            compensatorySync.setContact(request.getCONTACT());
            compensatorySync.setTel(request.getTEL());
            compensatorySync.setBusinessManagerA(request.getBUSINESS_MANAGER_A());
            compensatorySync.setAcode(request.getA_code());
            compensatorySync.setAname(request.getA_name());
            compensatorySync.setAssistB(request.getASSIST_B());
            compensatorySync.setBcode(request.getB_code());
            compensatorySync.setBname(request.getB_name());
            compensatorySync.setRepaymentDate(request.getRepayment_date());
            compensatorySync.setRepaymentBank(request.getRepayment_bank());
            compensatorySync.setRepaymentAmount(request.getRepayment_amount());
            compensatorySync.setRegisteredAddress(request.getREGISTERED_ADDRESS());
            compensatorySync.setCurrentAddress(request.getCURRENT_ADDRESS());
            compensatorySync.setRiskFund(request.getRiskFund());
            compensatorySync.setAntiRemark(request.getAnti_remark() == null ? null: request.getAnti_remark().toString());
            compensatorySync.setRelatedBusNo(request.getRelated_bus_no());
            compensatorySync.setRelatedGuaranteeNo(request.getRelated_guarantee_no());
            compensatorySync.setCreatedDeptIds(request.getCREATED_DEPT_IDS());
            compensatorySync.setCreatedCompanyId(request.getCREATED_COMPANY_ID());
            // 直接把省市区转成Long
            SysRegionPageRequest regionPageRequest = new SysRegionPageRequest();
            regionPageRequest.setName(request.getProvince());
            Result<List<SysRegionDTO>> all = sysRegionClient.findAll(1, 1, regionPageRequest);
            Long provinceId = all.getData().get(0).getId();
            regionPageRequest.setName(request.getCity());
            regionPageRequest.setParentId(provinceId);
            Result<List<SysRegionDTO>> cityRes = sysRegionClient.findAll(1, 1, regionPageRequest);
            compensatorySync.setProvince(provinceId);
            if (cityRes.succeed()){
                if (!ObjectUtils.isEmpty(cityRes.getData())){
                    Long cityId = cityRes.getData().get(0).getId();
                    compensatorySync.setCity(cityId);
                    regionPageRequest.setParentId(cityId);
                    regionPageRequest.setName(request.getArea());
                    Result<List<SysRegionDTO>> areaRes = sysRegionClient.findAll(1, 1, regionPageRequest);
                    if (areaRes.succeed()){
                        if (!ObjectUtils.isEmpty(areaRes.getData())){
                            compensatorySync.setArea(areaRes.getData().get(0).getId());
                        }
                    }
                }

            }
            resList.add(compensatorySync);
        }
        return resList;
    }

}
