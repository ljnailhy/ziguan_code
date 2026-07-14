package com.jk.asset;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class AssetApplicationTests {

  @Test
  void contextLoads() {
    log.info("开始测试");
//    myBatisPlusGenerator();
    log.info("测试结束");
    Assertions.assertTrue(true);
  }

}
