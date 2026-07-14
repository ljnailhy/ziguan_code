package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.CompensatorySyncMapper;
import com.jk.asset.model.entity.CompensatorySync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 项目同步接收表接口实现相关处理
 *
 * @author wangtao
 * @since 2024-07-25 16:40:16
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompensatorySyncHandler extends ServiceImpl<CompensatorySyncMapper, CompensatorySync> {

}