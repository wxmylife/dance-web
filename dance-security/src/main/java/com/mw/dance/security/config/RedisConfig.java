package com.mw.dance.security.config;

import com.mw.dance.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @author wxmylife
 */
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {
}
