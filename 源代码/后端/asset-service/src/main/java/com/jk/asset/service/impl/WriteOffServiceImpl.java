package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.mapper.ProjectInfoMapper;
import com.jk.asset.mapper.WriteOffMapper;
import com.jk.asset.model.dto.ProjectInfoDTO;
import com.jk.asset.model.dto.WriteOffDTO;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.entity.RevePropertyInfo;
import com.jk.asset.model.entity.WriteOff;
import com.jk.asset.model.request.RevePropertyInfoRequest;
import com.jk.asset.model.request.WriteOffRequest;
import com.jk.asset.model.request.page.WriteOffPageRequest;
import com.jk.asset.service.WriteOffService;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.RevePropertyInfoHandler;
import com.jk.asset.service.handler.WriteOffHandler;
import com.jk.asset.utils.limit.AssetUserLimitsUtils;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.service.client.SysDictionaryClient;
import com.jk.service.utils.PlatformFileUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 核销接口实现类
 *
 * @author wangtao
 * @since 2024-07-04 14:51:01
 */
@RestController
@Slf4j
@Api(tags = "核销接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WriteOffServiceImpl implements WriteOffService {

  private final WriteOffHandler handler;
  private final WriteOffMapper mapper;
  private final ProjectInfoMapper projectInfoMapper;
  private final PlatformFileUtils platformFileUtils;
  private final RevePropertyInfoHandler revePropertyInfoHandler;
  private final ProjectInfoHandler projectInfoHandler;
  private final AssetUserLimitsUtils assetUserLimitsUtils;
  private final SysDictionaryClient sysDictionaryClient;
  @Override
  public Result<WriteOffDTO> add(WriteOffRequest request) {
    if (null == request || request.unverified()) {
      String message = "核销新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    WriteOff writeOff = PlatformMapUtils.getInstance().map(request, WriteOff.class);
    if (request.getProjectId().equals(request.getId())){
      writeOff.setId(null);
    }
    ProjectInfoDTO infoById = projectInfoMapper.findInfoById(writeOff.getProjectId());
    writeOff.setProjectName(infoById.getProjectName());
    handler.saveOrUpdate(writeOff);
    if (request.getWriteOffStatus() == null){
      infoById.setIsWriteOff(false);
    }else if (request.getWriteOffStatus()){
      infoById.setIsWriteOff(true);
    }
    // 修改项目状态
    ProjectInfo projectInfo = PlatformMapUtils.getInstance().map(infoById, ProjectInfo.class);
    projectInfoMapper.updateById(projectInfo);
    WriteOffDTO dto = new WriteOffDTO();
    // 写入附件信息
    dto.setId(writeOff.getId());
    if (!ObjectUtils.isEmpty(request.getFileRequests())){
      platformFileUtils.batchAddFile(request.getFileRequests(), writeOff.getId(), BillTypeEnum.WRITE_OFF.getKey());
    }
    return Result.success(dto);
  }

  @Override
  public Result<WriteOffDTO> submit(WriteOffRequest request) {
    Result<WriteOffDTO> result;
    // 已提交
    request.setWriteOffStatus(true);
    if (ObjectUtils.isEmpty(request.getId())){
      result= add(request);
    }else {
      result = update(request);
    }
    if (null == result || !result.succeed()) {
      return Result.error("提交失败");
    }
    return result;
  }

  @Override
  public Result<?> delete(Long id) {
    handler.removeById(id);
    return Result.success();
  }

  @Override
  public Result<WriteOffDTO> update(WriteOffRequest request) {
    if (null == request || null == request.getId()) {
      String message = "核销修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    WriteOff writeOff = PlatformMapUtils.getInstance().map(request, WriteOff.class);
    ProjectInfoDTO infoById = projectInfoMapper.findInfoById(writeOff.getProjectId());
    writeOff.setProjectName(infoById.getProjectName());
    handler.updateById(writeOff);
    if (request.getWriteOffStatus() == null){
      infoById.setIsWriteOff(false);
    } else if (request.getWriteOffStatus()){
      infoById.setIsWriteOff(true);
    }

    ProjectInfo projectInfo = PlatformMapUtils.getInstance().map(infoById, ProjectInfo.class);
    projectInfoMapper.updateById(projectInfo);
    // 修改附件信息
    if (!ObjectUtils.isEmpty(request.getFileRequests())){
      platformFileUtils.batchUpdateFile(request.getFileRequests(),writeOff.getId(),BillTypeEnum.WRITE_OFF.getKey());
    }
    return Result.success();
  }

  @Override
  public Result<WriteOffDTO> findById(Long id) {
    WriteOff writeOff = handler.getById(id);
    if (null == writeOff) {
      String message = "核销单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    WriteOffDTO dto = PlatformMapUtils.getInstance().map(writeOff, WriteOffDTO.class);

    LambdaQueryWrapper<RevePropertyInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(RevePropertyInfo::getDoId,dto.getProjectId())
            .eq(RevePropertyInfo::getDoType,BillTypeEnum.PROJECT_INFO);
    List<RevePropertyInfo> list = revePropertyInfoHandler.list(queryWrapper);
    if (!ObjectUtils.isEmpty(list)){
      dto.setRevePropertyInfoRequest( PlatformMapUtils.getInstance().mapAsList(list, RevePropertyInfoRequest.class));
    }
    return Result.success(dto);
  }



  @Override
  public Result<List<WriteOffDTO>> findAll(Integer current, Integer size, WriteOffPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }

    Set<Long> userLimitsOrg = assetUserLimitsUtils.getUserLimitsOrg();
    request.setOrgUserIds(userLimitsOrg);
    // Step1：创建一个 Page 对象
    IPage<WriteOff> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<WriteOff> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<WriteOffDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, WriteOffDTO.class);
    List<Long> collect = dtoList.stream().map(WriteOffRequest::getProjectId).collect(Collectors.toList());
    LambdaQueryWrapper<RevePropertyInfo> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.in(RevePropertyInfo::getDoId,collect)
            .eq(RevePropertyInfo::getDoType,BillTypeEnum.PROJECT_INFO);
    List<RevePropertyInfo> list = revePropertyInfoHandler.list(queryWrapper);
    if (!ObjectUtils.isEmpty(list)){
      Map<Long, List<RevePropertyInfo>> collect1 = list.stream().collect(Collectors.groupingBy(RevePropertyInfo::getDoId));
      dtoList.forEach(a ->{
        for (WriteOffDTO dto : dtoList) {
          if (!ObjectUtils.isEmpty(collect1.get(dto.getProjectId()))){
            dto.setRevePropertyInfoRequest(PlatformMapUtils.getInstance().mapAsList(collect1.get(dto.getProjectId()), RevePropertyInfoRequest.class));
          }
        }
      });
    }
//    List<SysDictionaryItemDTO> items = sysDictionaryClient.findByCode(ProjectStateEnum.PROJECT_STATE.getKey())
//            .getData()
//            .getItems();

//    dtoList.forEach(a -> a.setProjectStateList(items.stream().filter(b ->b.getItemCode().equals(DictEnum.PROJECT_STATE_01.getKey())).collect(Collectors.toList()).stream().map(SysDictionaryItemDTO::getId).collect(Collectors.toList())));
    return Result.success(dtoList, PageFactory.page(page));
  }

}