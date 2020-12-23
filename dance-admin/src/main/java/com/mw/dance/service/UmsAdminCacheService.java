package com.mw.dance.service;

import com.mw.dance.model.UmsAdmin;

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

  /**
   * 删除验证码
   *
   * @param telephone 手机号
   */
  void deleteAuthCode(String telephone);

  /**
   * 获取缓存用户信息
   *
   * @param telephone 手机号
   * @return 后台用户
   */
  UmsAdmin getAdmin(String telephone);

  /**
   * 设置缓存后台用户数据
   *
   * @param admin 用户信息
   */
  void setAdmin(UmsAdmin admin);

  /**
   * 删除缓存后台用户数据
   *
   * @param adminId 用户信息Id
   */
  void delAdmin(Long adminId);

  /**
   * 设置登录凭证缓存
   *
   * @param telephone 手机号
   * @param authToken 用户 token
   */
  void setLoginAuth(String telephone, String authToken);

  /**
   * 删除登录凭证缓存
   *
   * @param telephone 手机号
   */
  boolean deleteLoginAuth(String telephone);

}
