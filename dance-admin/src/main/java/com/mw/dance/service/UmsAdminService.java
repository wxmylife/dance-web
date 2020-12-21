package com.mw.dance.service;

import com.mw.dance.model.UmsAdmin;
import com.mw.dance.model.UmsResource;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author wxmylife
 */
public interface UmsAdminService {

  UmsAdmin getAdminByUsername(String username);

  UmsAdmin register(String telephone, String authCode);

  String login(String username, String password);

  String generateAuthCode(String telephone);

  List<UmsResource> getResourceList(Long adminId);

  UserDetails loadUserByUsername(String username);
}
