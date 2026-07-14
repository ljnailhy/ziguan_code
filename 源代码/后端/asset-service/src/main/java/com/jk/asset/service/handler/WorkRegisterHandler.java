package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.WorkRegisterMapper;
import com.jk.asset.model.entity.WorkRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 工作登记接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-06-28 17:23:16
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkRegisterHandler extends ServiceImpl<WorkRegisterMapper, WorkRegister> {

}