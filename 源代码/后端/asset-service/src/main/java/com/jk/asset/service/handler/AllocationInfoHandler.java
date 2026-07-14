package com.jk.asset.service.handler;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.AllocationInfoMapper;
import com.jk.asset.model.entity.AllocationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 分配/变更主表接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-06-28 10:13:53
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AllocationInfoHandler extends ServiceImpl<AllocationInfoMapper, AllocationInfo> {

}