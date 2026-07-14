package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.Agency;
import com.jk.asset.model.request.page.AgencyPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 代理机构 Mapper 接口
 *
 * @author wangtao
 * @since 2024-06-19 18:14:46
 */
public interface AgencyMapper extends BaseMapper<Agency> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangtao
   * @since 2024-06-19 18:14:46
   */
  List<Agency> findAll(IPage<Agency> page, @Param("request") AgencyPageRequest request);

}