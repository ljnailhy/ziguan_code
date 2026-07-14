package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.ProjectInfoMapper;
import com.jk.asset.model.entity.ProjectInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 项目信息表接口实现相关处理
 *
 * @author wangtao
 * @since 2024-06-24 09:41:55
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProjectInfoHandler extends ServiceImpl<ProjectInfoMapper, ProjectInfo> {

}