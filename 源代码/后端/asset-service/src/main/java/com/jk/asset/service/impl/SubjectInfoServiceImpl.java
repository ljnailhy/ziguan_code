package com.jk.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.jk.asset.enums.BillTypeEnum;
import com.jk.asset.mapper.SubjectInfoMapper;
import com.jk.asset.model.dto.SubjectInfoDTO;
import com.jk.asset.model.entity.ProjectInfo;
import com.jk.asset.model.entity.SubjectInfo;
import com.jk.asset.model.request.SubjectInfoRequest;
import com.jk.asset.model.request.page.SubjectInfoPageRequest;
import com.jk.asset.service.SubjectInfoService;
import com.jk.asset.service.handler.ProjectInfoHandler;
import com.jk.asset.service.handler.SubjectInfoHandler;
import com.jk.common.factory.PageFactory;
import com.jk.common.model.Result;
import com.jk.common.utils.PlatformMapUtils;
import com.jk.common.utils.PlatformStringUtils;
import com.jk.service.utils.PlatformFileUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 主体信息接口实现类
 *
 * @author wangshuai
 * @since 2024-06-20 14:25:14
 */
@RestController
@Slf4j
@Api(tags = "主体信息接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubjectInfoServiceImpl implements SubjectInfoService {

  private final SubjectInfoHandler handler;
  private final SubjectInfoMapper mapper;
  private final PlatformFileUtils platformFileUtils;
  private final ProjectInfoHandler projectInfoHandler;

  @Override
  public Result<SubjectInfoDTO> add(SubjectInfoRequest request) {
    if (null == request || request.unverified()) {
      String message = "主体信息新增 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    // 对象拷贝：request->DO
    SubjectInfo subjectInfo = PlatformMapUtils.getInstance().map(request, SubjectInfo.class);
    String documentNumber = subjectInfo.getDocumentNumber();
    if (ObjectUtils.isNotEmpty(documentNumber)) {
      List<SubjectInfo> list = handler.list(new LambdaQueryWrapper<SubjectInfo>().eq(SubjectInfo::getDocumentNumber, documentNumber));
      if (CollectionUtils.isNotEmpty(list)) {
        String message = "该证件号码已存在";
        return Result.error(message);
      }
    }
    handler.save(subjectInfo);
    //  通用附件新增
    platformFileUtils.batchAddFile(request.getFileInfoList(),subjectInfo.getId(), BillTypeEnum.SUBJECT_INFO.getKey());

    SubjectInfoDTO dto = new SubjectInfoDTO();
    dto.setId(subjectInfo.getId());
    return Result.success(dto);
  }

  @Override
  public Result<?> delete(Long id) {
    //  判断项目是否引用该主体
    List<ProjectInfo> list = projectInfoHandler.list(new LambdaQueryWrapper<ProjectInfo>().eq(ProjectInfo::getDebtorId, id));
    if (ObjectUtils.isNotEmpty(list)){
      String message = "删除主体失败 该主体已被项目引用";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    handler.removeById(id);
    platformFileUtils.deleteFileByDoId(id,BillTypeEnum.SUBJECT_INFO.getKey());
    return Result.success();
  }

  @Override
  public Result<SubjectInfoDTO> update(SubjectInfoRequest request) {
    if (null == request || null == request.getId()) {
      String message = "主体信息修改 请求参数异常";
      log.info("{} request {}", message, request);
      return Result.error(message);
    }

    SubjectInfo subjectInfo = PlatformMapUtils.getInstance().map(request, SubjectInfo.class);
    String documentNumber = subjectInfo.getDocumentNumber();
    SubjectInfo list = handler.getOne(new LambdaQueryWrapper<SubjectInfo>().eq(SubjectInfo::getId, request.getId()));
    String documentNumber1 = list.getDocumentNumber();
    if (!documentNumber.equals(documentNumber1)) {
      List<SubjectInfo> subjectInfos = handler.list(new LambdaQueryWrapper<SubjectInfo>().eq(SubjectInfo::getDocumentNumber, documentNumber));
      if (!subjectInfos.isEmpty()) {
        String message = "该证件号码已存在";
        return Result.error(message);
      }
    }

    handler.updateById(subjectInfo);
    //  通用附件修改
    platformFileUtils.batchUpdateFile(request.getFileInfoList(),subjectInfo.getId(), BillTypeEnum.SUBJECT_INFO.getKey());

    return Result.success();
  }

  @Override
  public Result<SubjectInfoDTO> findById(Long id) {
    SubjectInfo subjectInfo = handler.getById(id);
    if (null == subjectInfo) {
      String message = "主体信息单个查找 该记录不存在";
      log.info("{} id {}", message, id);
      return Result.error(message);
    }
    // 对象拷贝：DO->DTO
    SubjectInfoDTO dto = PlatformMapUtils.getInstance().map(subjectInfo, SubjectInfoDTO.class);
    return Result.success(dto);
  }

  @Override
  public Result<List<SubjectInfoDTO>> findAll(Integer current, Integer size, SubjectInfoPageRequest request) {
    if (PlatformStringUtils.isNotBlank(request.getField())) {
      request.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, request.getField()));
    }
    // Step1：创建一个 Page 对象
    IPage<SubjectInfo> page = new Page<>(current, size);
    // Step2：调用 mybatis-plus 提供的分页查询方法 - mapper
    List<SubjectInfo> doList = mapper.findAll(page, request);
    // 空则直接返回
    if (CollectionUtils.isEmpty(doList)) {
      return Result.success(Lists.newArrayList(), PageFactory.page(page));
    }
    // Step3：获取分页数据
    List<SubjectInfoDTO> dtoList = PlatformMapUtils.getInstance().mapAsList(doList, SubjectInfoDTO.class);
    return Result.success(dtoList, PageFactory.page(page));
  }

}