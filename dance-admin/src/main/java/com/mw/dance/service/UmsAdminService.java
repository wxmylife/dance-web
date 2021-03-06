package com.mw.dance.service;

import com.mw.dance.dto.ForgetAdminPasswordParam;
import com.mw.dance.dto.UmsRegisterParam;
import com.mw.dance.dto.UpdateAdminPasswordParam;
import com.mw.dance.model.UmsAdmin;
import com.mw.dance.model.UmsResource;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author wxmylife
 */
public interface UmsAdminService {

  UmsAdmin getAdminByTelephone(String telephone);

  UmsAdmin register(UmsRegisterParam umsRegisterParam);

  boolean logout(Long adminId);

  String login(String telephone, String password);

  String generateAuthCode(String telephone);

  List<UmsResource> getResourceList(Long adminId);

  UserDetails loadUserByTelephone(String telephone);

  int forgetPassword(ForgetAdminPasswordParam forgetParam);

  int updatePassword(UpdateAdminPasswordParam updateParam);

  UmsAdmin getItem(Long id);
}
