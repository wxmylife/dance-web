package com.mw.dance.security.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 用于配置白名单资源路径
 *
 * @author wxmylife
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {

  private List<String> urls = new ArrayList<>();
}
