package com.mw.dance.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author wxmylife
 */
@Configuration
@EnableTransactionManagement
@MapperScan({ "com.mw.dance.mapper", "com.macro.mall.dao" })
public class MyBatisConfig {
}
