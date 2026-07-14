package com.jk.asset.generator;

import com.jk.service.utils.mybatis.MyBatisPlusGeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//@SpringBootTest
@Slf4j
class AssetMyBatisPlusGeneratorTests {

  /**
   * 【修改】本地后端工程路径
   */
  private static final String CONTEXT = "D:\\workspace\\jk\\product\\asset-service";
  /**
   * 【修改】本地前端工程路径，默认值： CONTEXT + "\\fe"
   */
  private static final String CONTEXT_FE = "D:\\workspace\\jk\\product-fe\\asset-fe";
  /**
   * 【修改】作者
   */
  private static final String AUTHOR = "Yuqiang Wu";
  /**
   * 【修改】需要生成代码的表名，模糊匹配，目前配置不会覆盖已生成文件
   */
  private static final String LIKE_TABLE_NAME = "agency";
  /**
   * 包名前缀
   */
  private static final String PARENT = "com.jk.asset";

//  @Value("${spring.datasource.url:jdbc:mysql://106.55.177.90:3306/platform?serverTimezone=GMT%2B8}")
  private static final String url = "jdbc:mysql://192.168.1.100:3306/platform_asset?serverTimezone=GMT%2B8";
//  @Value("${spring.datasource.username:develop}")
  private static final String username = "develop";
//  @Value("${spring.datasource.password:}")
  private static final String password = "develop";

  /**
   * 【按需】当上述匹配到的新表，不是全部要生成时，可在这排除
   */
  private static final String[] EXCLUDE_TABLE_NAMES = {};

  @Test
  void contextLoads() {
    MyBatisPlusGeneratorUtils.execute(url, username, password, CONTEXT, CONTEXT_FE, AUTHOR, PARENT, LIKE_TABLE_NAME);
    Assertions.assertTrue(true);
  }

}
