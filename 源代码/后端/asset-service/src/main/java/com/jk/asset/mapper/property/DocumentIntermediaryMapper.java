package com.jk.asset.mapper.property;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.property.DocumentIntermediary;
import com.jk.asset.model.request.page.property.DocumentIntermediaryPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 单据和中介关联表 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-07-12 16:11:38
 */
public interface DocumentIntermediaryMapper extends BaseMapper<DocumentIntermediary> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-07-12 16:11:38
   */
  List<DocumentIntermediary> findAll(IPage<DocumentIntermediary> page, @Param("request") DocumentIntermediaryPageRequest request);

}