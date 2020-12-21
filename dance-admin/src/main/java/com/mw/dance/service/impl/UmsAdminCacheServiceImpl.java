package com.mw.dance.service.impl;

import com.mw.dance.common.service.RedisService;
import com.mw.dance.service.UmsAdminCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author wxmylife
 */
@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {

  @Autowired
  private RedisService redisService;

  @Value("${redis.database}")
  private String REDIS_DATABASE;

  // @Value("${redis.expire.common}")
  // private Long REDIS_EXPIRE;

  @Value("${redis.expire.authCode}")
  private Long REDIS_EXPIRE_AUTH_CODE;

  // @Value("${redis.key.member}")
  // private String REDIS_KEY_MEMBER;

  @Value("${redis.key.authCode}")
  private String REDIS_KEY_AUTH_CODE;

  @Override public void setAuthCode(String telephone, String authCode) {
    String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + telephone;
    redisService.set(key, authCode, REDIS_EXPIRE_AUTH_CODE);
  }

  @Override public String getAuthCode(String telephone) {
    String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + telephone;
    return (String) redisService.get(key);
  }
}
