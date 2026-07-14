package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.WriteOff;
import com.jk.asset.model.request.page.WriteOffPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 核销 Mapper 接口
 *
 * @author wangtao
 * @since 2024-07-04 14:51:01
 */
public interface WriteOffMapper extends BaseMapper<WriteOff> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangtao
   * @since 2024-07-04 14:51:01
   */
  List<WriteOff> findAll(IPage<WriteOff> page, @Param("request") WriteOffPageRequest request);

}