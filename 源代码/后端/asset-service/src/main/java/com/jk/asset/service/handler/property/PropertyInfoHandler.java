package com.jk.asset.service.handler.property;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.asset.mapper.property.PropertyInfoMapper;
import com.jk.asset.model.entity.property.PropertyInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 资产信息接口实现相关处理
 *
 * @author wangshuai
 * @since 2024-07-10 09:47:37
 */
@Slf4j
@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyInfoHandler extends ServiceImpl<PropertyInfoMapper, PropertyInfo> {

}