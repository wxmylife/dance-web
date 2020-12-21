package com.mw.dance.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.mw.dance.bo.AdminUserDetails;
import com.mw.dance.common.exception.Asserts;
import com.mw.dance.mapper.UmsAdminMapper;
import com.mw.dance.model.UmsAdmin;
import com.mw.dance.model.UmsAdminExample;
import com.mw.dance.model.UmsResource;
import com.mw.dance.service.UmsAdminCacheService;
import com.mw.dance.service.UmsAdminService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author wxmylife
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

  @Autowired
  private UmsAdminMapper adminMapper;

  @Autowired
  private UmsAdminCacheService adminCacheService;

  @Override public UmsAdmin getAdminByUsername(String username) {
    return null;
  }

  @Override public UmsAdmin register(String telephone, String authCode) {
    // 验证码校验
    if (!verifyAuthCode(authCode, telephone)) {
      Asserts.fail("验证码错误");
    }
    // 查询用户是否已经注册
    UmsAdminExample example = new UmsAdminExample();
    example.createCriteria().andTelephoneEqualTo(telephone);
    List<UmsAdmin> umsAdmins = adminMapper.selectByExample(example);
    if (ArrayUtil.isEmpty(umsAdmins)) {
      Asserts.fail("该用户已经存在");
    }
    // 注册用户
    UmsAdmin umsAdmin = new UmsAdmin();
    umsAdmin.setTelephone(telephone);
    umsAdmin.setCreateTime(new Date());
    umsAdmin.setStatus(1);
    adminMapper.insert(umsAdmin);
    return umsAdmin;
  }

  @Override public String login(String username, String password) {
    return null;
  }

  @Override public String generateAuthCode(String telephone) {
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < 6; i++) {
      sb.append(random.nextInt(10));
    }
    adminCacheService.setAuthCode(telephone, sb.toString());
    return sb.toString();
  }

  @Override public List<UmsResource> getResourceList(Long adminId) {
    return new ArrayList<UmsResource>();
  }

  @Override public UserDetails loadUserByUsername(String username) {
    UmsAdmin admin = getAdminByUsername(username);
    if (admin != null) {
      List<UmsResource> resourceList = getResourceList(admin.getId());
      return new AdminUserDetails(admin, resourceList);
    }
    throw new UsernameNotFoundException("用户名或密码错误");
  }

  private boolean verifyAuthCode(String authCode, String telephone) {
    if (StrUtil.hasEmpty(authCode)) {
      return false;
    }
    String realAuthCode = adminCacheService.getAuthCode(telephone);
    return StrUtil.equals(authCode, realAuthCode);
  }
}
