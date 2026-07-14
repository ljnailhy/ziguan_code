package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.CustomerInfoMapper;
import com.jk.asset.model.entity.CustomerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 客户信息表接口实现相关处理
 *
 * @author wangtao
 * @since 2024-06-20 11:38:52
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerInfoHandler extends ServiceImpl<CustomerInfoMapper, CustomerInfo> {

}