package com.mw.dance.service;

/**
 * @author wxmylife
 */
public interface UmsAdminCacheService {

  /**
   * 设置验证码
   *
   * @param telephone 手机号
   * @param authCode 验证码
   */
  void setAuthCode(String telephone, String authCode);

  /**
   * 获取验证码
   *
   * @param telephone 手机号
   * @return 验证码
   */
  String getAuthCode(String telephone);
}
