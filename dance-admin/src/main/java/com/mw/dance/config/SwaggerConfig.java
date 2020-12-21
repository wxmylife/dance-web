package com.mw.dance.config;

import com.mw.dance.common.config.BaseSwaggerConfig;
import com.mw.dance.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wxmylife
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
  @Override public SwaggerProperties swaggerProperties() {
    return SwaggerProperties.builder()
        .apiBasePackage("com.mw.admin.controller")
        .title("后台系统")
        .description("后台相关接口文档")
        .version("1.0")
        .enableSecurity(true)
        .build();
  }
}
