package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.mapper.AgencyMapper;
import com.jk.asset.model.dto.AgencyDTO;
import com.jk.asset.model.entity.Agency;
import com.jk.asset.model.entity.property.AssetTransfer;
import com.jk.asset.model.entity.property.LeaseInfo;
import com.jk.asset.model.request.AgencyRequest;
import com.jk.asset.model.request.page.AgencyPageRequest;
import com.jk.asset.service.AgencyService;
import com.jk.asset.service.handler.AgencyHandler;
import com.jk.asset.service.handler.property.AssetTransferHandler;
import com.jk.asset.service.handler.property.LeaseInfoHandler;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.service.utils.PlatformFileUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 代理机构接口实现类
 *
 * @author wangtao
 * @since 2024-06-19 18:14:46
 */
@RestController
@Slf4j
@Api(tags = "代理机构接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgencyServiceImpl implements AgencyService {

  private final AgencyHandler handler;
  private final AgencyMapper mapper;
  private final PlatformFileUtils platformFileUtils;
  private final AssetTransferHandler assetTransferHandler;
  private final LeaseInfoHandler leaseInfoHandler;

  @Override
  public Result<AgencyDTO> add(AgencyRequest request) {
    if (null == request || request.unverified()) {
      String message = "代理机构新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    Agency agency = PlatformMapUtils.getInstance().map(request, Agency.class);
    handler.save(agency);

    platformFileUtils.batchAddFile(request.getFileRequests(), agency.getId(), BillTypeEnum.AGENCY.getKey());
    AgencyDTO dto = new AgencyDTO();
    dto.setId(agency.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
  List<AssetTransfer> transfers = assetTransferHandler.list(new LambdaQueryWrapper<AssetTransfer>().eq(AssetTransfer::getEstimateId, id));
    List<LeaseInfo> leaseInfos = leaseInfoHandler.list(new LambdaQueryWrapper<LeaseInfo>().eq(LeaseInfo::getEstimateId, id));

    if (!transfers.isEmpty()){
    return Result.error("该机构已关联资产转让,请先解除关联关系");
  }
    if (!leaseInfos.isEmpty()){
      return Result.error("该机构已关联资产租赁,请先解除关联关系");
    }

    // 校验 资产转让是否关联机构
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<AgencyDTO> update(AgencyRequest request) {
    if (null == request || null == request.getId()) {
      String message = "代理机构修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }
    Agency agency = PlatformMapUtils.getInstance().map(request, Agency.class);
    handler.updateById(agency);
    platformFileUtils.batchUpdateFile(request.getFileRequests(), agency.getId(), BillTypeEnum.AGENCY.getKey());
    return Result.success();
  }

  @Override
  public Result<AgencyDTO> findById(Long id) {
    Agency agency = handler.getById(id);
    if (null == agency) {
      String message = "代理机构单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    AgencyDTO dto = PlatformMapUtils.getInstance().map(agency, AgencyDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<AgencyDTO>> findAll(Integer current, Integer size, AgencyPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<Agency> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<Agency> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<AgencyDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, AgencyDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}