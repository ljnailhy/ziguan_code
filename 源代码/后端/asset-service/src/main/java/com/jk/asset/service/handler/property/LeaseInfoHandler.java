package com.jk.asset.service.handler.property;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.property.LeaseInfoMapper;
import com.jk.asset.model.entity.property.LeaseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 租赁信息表接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-07-11 18:04:22
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LeaseInfoHandler extends ServiceImpl<LeaseInfoMapper, LeaseInfo> {

}