package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.entity.RevePropertyInfo;
import com.jk.asset.model.request.page.RevePropertyInfoPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 反担保/其他财产线索信息 Mapper 接口
 *
 * @author wangtao
 * @since 2024-06-24 09:42:39
 */
public interface RevePropertyInfoMapper extends BaseMapper<RevePropertyInfo> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangtao
   * @since 2024-06-24 09:42:39
   */
  List<RevePropertyInfo> findAll(IPage<RevePropertyInfo> page, @Param("request") RevePropertyInfoPageRequest request);

}