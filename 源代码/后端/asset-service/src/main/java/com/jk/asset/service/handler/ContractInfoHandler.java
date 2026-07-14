package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.ContractInfoMapper;
import com.jk.asset.model.entity.ContractInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 合同信息接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-06-20 17:46:10
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContractInfoHandler extends ServiceImpl<ContractInfoMapper, ContractInfo> {

}