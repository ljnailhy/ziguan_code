package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.WriteOffMapper;
import com.jk.asset.model.entity.WriteOff;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 核销接口实现相关处理
 *
 * @author wangtao
 * @since 2024-07-04 14:51:01
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WriteOffHandler extends ServiceImpl<WriteOffMapper, WriteOff> {

}