package com.mw.dance.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.mw.dance.bo.AdminUserDetails;
import com.mw.dance.common.exception.Asserts;
import com.mw.dance.common.util.RequestUtil;
import com.mw.dance.dto.ForgetAdminPasswordParam;
import com.mw.dance.dto.UmsRegisterParam;
import com.mw.dance.dto.UpdateAdminPasswordParam;
import com.mw.dance.mapper.UmsAdminLoginLogMapper;
import com.mw.dance.mapper.UmsAdminMapper;
import com.mw.dance.model.UmsAdmin;
import com.mw.dance.model.UmsAdminExample;
import com.mw.dance.model.UmsAdminLoginLog;
import com.mw.dance.model.UmsResource;
import com.mw.dance.security.util.JwtTokenUtil;
import com.mw.dance.service.UmsAdminCacheService;
import com.mw.dance.service.UmsAdminService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.mw.dance.common.util.RegexConstants.AUTH_CODE_LENGTH;

/**
 * @author wxmylife
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

  @Autowired
  private UmsAdminMapper adminMapper;

  @Autowired
  private UmsAdminLoginLogMapper loginLogMapper;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UmsAdminCacheService adminCacheService;

  @Override public UmsAdmin getAdminByTelephone(String telephone) {
    UmsAdmin admin = adminCacheService.getAdmin(telephone);
    if (admin != null) {
      return admin;
    }
    UmsAdminExample example = new UmsAdminExample();
    example.createCriteria().andTelephoneEqualTo(telephone);
    List<UmsAdmin> adminList = adminMapper.selectByExample(example);
    if (CollUtil.isNotEmpty(adminList)) {
      admin = adminList.get(0);
      adminCacheService.setAdmin(admin);
      return admin;
    }
    return null;
  }

  @Override public UmsAdmin register(UmsRegisterParam umsRegisterParam) {
    // 验证码校验
    if (!verifyAuthCode(umsRegisterParam.getCode(), umsRegisterParam.getTelephone())) {
      Asserts.fail("验证码错误");
    }
    // 查询用户是否已经注册
    UmsAdminExample example = new UmsAdminExample();
    example.createCriteria().andTelephoneEqualTo(umsRegisterParam.getTelephone());
    List<UmsAdmin> umsAdmins = adminMapper.selectByExample(example);
    if (ArrayUtil.isEmpty(umsAdmins)) {
      Asserts.fail("该用户已经存在");
    }
    // 注册用户
    UmsAdmin umsAdmin = new UmsAdmin();
    umsAdmin.setTelephone(umsRegisterParam.getTelephone());
    umsAdmin.setPassword(passwordEncoder.encode(umsRegisterParam.getPassword()));
    umsAdmin.setCreateTime(new Date());
    umsAdmin.setUsername(umsRegisterParam.getTelephone());
    umsAdmin.setStatus(1);
    adminMapper.insert(umsAdmin);

    // 验证码充值
    adminCacheService.deleteAuthCode(umsRegisterParam.getTelephone());
    // 密码重置
    umsAdmin.setPassword(null);
    return umsAdmin;
  }

  @Override public boolean logout(Long adminId) {
    UmsAdminExample example = new UmsAdminExample();
    example.createCriteria().andIdEqualTo(adminId);
    List<UmsAdmin> adminList = adminMapper.selectByExample(example);
    if (CollUtil.isEmpty(adminList)) {
      return false;
    }
    UmsAdmin umsAdmin = adminList.get(0);
    adminCacheService.delAdmin(adminId);
    return adminCacheService.deleteLoginAuth(umsAdmin.getTelephone());
  }

  @Override public String login(String telephone, String password) {
    String token = null;
    try {
      UserDetails userDetails = loadUserByTelephone(telephone);
      if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        Asserts.fail("密码错误");
      }
      if (!userDetails.isEnabled()) {
        Asserts.fail("账号已被禁用");
      }
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      token = jwtTokenUtil.generateToken(telephone);
      // insertLoginLog(telephone);
      adminCacheService.setLoginAuth(telephone, token);
    } catch (AuthenticationException e) {
      LOGGER.warn("登录异常:{}", e.getMessage());
    }
    return token;
  }

  /**
   * 添加登录记录
   *
   * @param telephone 用户手机号
   */
  private void insertLoginLog(String telephone) {
    UmsAdmin admin = getAdminByTelephone(telephone);
    if (admin == null) {
      return;
    }
    Date loginDate = new Date();
    // 更新用户登录时间
    admin.setLoginTime(loginDate);
    adminMapper.updateByPrimaryKeySelective(admin);

    // 插入用户上次登录信息
    UmsAdminLoginLog log = new UmsAdminLoginLog();
    log.setAdminId(admin.getId());
    log.setCreateTime(new Date());
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attributes != null) {
      HttpServletRequest request = attributes.getRequest();
      log.setIp(RequestUtil.getRequestIp(request));
    }
    loginLogMapper.insert(log);

  }

  @Override public String generateAuthCode(String telephone) {
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < AUTH_CODE_LENGTH; i++) {
      sb.append(random.nextInt(10));
    }
    adminCacheService.setAuthCode(telephone, sb.toString());
    return sb.toString();
  }

  @Override public List<UmsResource> getResourceList(Long adminId) {
    return new ArrayList<UmsResource>();
  }

  @Override public UserDetails loadUserByTelephone(String telephone) {
    UmsAdmin admin = getAdminByTelephone(telephone);
    if (admin != null) {
      List<UmsResource> resourceList = getResourceList(admin.getId());
      return new AdminUserDetails(admin, resourceList);
    }
    throw new UsernameNotFoundException("用户名或密码错误");
  }

  @Override public int forgetPassword(ForgetAdminPasswordParam forgetParam) {
    if (StrUtil.isEmpty(forgetParam.getTelephone())
        || StrUtil.isEmpty(forgetParam.getCode())
        || StrUtil.isEmpty(forgetParam.getNewPassword())) {
      return -1;
    }

    // 验证码校验
    if (!verifyAuthCode(forgetParam.getCode(), forgetParam.getTelephone())) {
      Asserts.fail("验证码错误");
    }

    UmsAdminExample example = new UmsAdminExample();
    example.createCriteria().andTelephoneEqualTo(forgetParam.getTelephone());
    List<UmsAdmin> adminList = adminMapper.selectByExample(example);
    if (CollUtil.isEmpty(adminList)) {
      return -2;
    }
    UmsAdmin umsAdmin = adminList.get(0);
    umsAdmin.setPassword(passwordEncoder.encode(forgetParam.getNewPassword()));
    adminMapper.updateByPrimaryKeySelective(umsAdmin);
    adminCacheService.delAdmin(umsAdmin.getId());
    adminCacheService.deleteAuthCode(forgetParam.getTelephone());
    return 1;
  }

  @Override public int updatePassword(UpdateAdminPasswordParam updateParam) {

    if (StrUtil.isEmpty(updateParam.getTelephone())
        || StrUtil.isEmpty(updateParam.getOldPassword())
        || StrUtil.isEmpty(updateParam.getNewPassword())) {
      return -1;
    }
    UmsAdminExample example = new UmsAdminExample();
    example.createCriteria().andTelephoneEqualTo(updateParam.getTelephone());
    List<UmsAdmin> adminList = adminMapper.selectByExample(example);
    if (CollUtil.isEmpty(adminList)) {
      return -2;
    }
    UmsAdmin umsAdmin = adminList.get(0);
    if (!passwordEncoder.matches(updateParam.getOldPassword(), umsAdmin.getPassword())) {
      return -3;
    }
    umsAdmin.setPassword(passwordEncoder.encode(updateParam.getNewPassword()));
    adminMapper.updateByPrimaryKeySelective(umsAdmin);
    adminCacheService.delAdmin(umsAdmin.getId());
    adminCacheService.deleteLoginAuth(umsAdmin.getTelephone());
    return 1;
  }

  @Override public UmsAdmin getItem(Long id) {
    return adminMapper.selectByPrimaryKey(id);
  }

  private boolean verifyAuthCode(String authCode, String telephone) {
    if (StrUtil.hasEmpty(authCode)) {
      return false;
    }
    String realAuthCode = adminCacheService.getAuthCode(telephone);
    return StrUtil.equals(authCode, realAuthCode);
  }
}
