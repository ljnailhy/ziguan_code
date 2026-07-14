package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.AgencyMapper;
import com.jk.asset.model.entity.Agency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 代理机构接口实现相关处理
 *
 * @author wangtao
 * @since 2024-06-19 18:14:46
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgencyHandler extends ServiceImpl<AgencyMapper, Agency> {

}