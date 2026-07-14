package com.jk.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.asset.model.dto.WorkRegisterDTO;
import com.jk.asset.model.entity.WorkRegister;
import com.jk.asset.model.request.page.WorkRegisterPageRequest;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 工作登记 Mapper 接口
 *
 * @author wangshuai
 * @since 2024-06-28 17:23:16
 */
public interface WorkRegisterMapper extends BaseMapper<WorkRegister> {

  /**
   * 分页查找
   *
   * @param page 分页信息
   * @param request 入参
   * @return java.util.List
   * @author wangshuai
   * @since 2024-06-28 17:23:16
   */
  List<WorkRegisterDTO> findAll(IPage<WorkRegister> page, @Param("request") WorkRegisterPageRequest request);

}