package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.RecoveryPaymentMapper;
import com.jk.asset.model.entity.RecoveryPayment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 付款表接口实现相关处理
 *
 * @author wangtao
 * @since 2024-07-08 11:35:36
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RecoveryPaymentHandler extends ServiceImpl<RecoveryPaymentMapper, RecoveryPayment> {

}