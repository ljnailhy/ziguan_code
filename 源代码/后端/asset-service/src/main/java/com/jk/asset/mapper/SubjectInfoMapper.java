package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.SubjectInfo;
import com.jk.asset.model.request.page.SubjectInfoPageRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 主体信息 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-06-20 14:25:14
 */
public interface SubjectInfoMapper extends BaseMapper<SubjectInfo> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-06-20 14:25:14
   */
  List<SubjectInfo> findAll(IPage<SubjectInfo> page, @Param("request") SubjectInfoPageRequest request);

    List<String> findRepeatDocumentCode(List<String> documentCodeList);
}