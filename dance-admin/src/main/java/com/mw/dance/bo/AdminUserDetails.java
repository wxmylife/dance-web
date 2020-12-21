package com.mw.dance.bo;

import com.mw.dance.model.UmsAdmin;
import com.mw.dance.model.UmsResource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * SpringSecurity 需要的用户详情
 *
 * @author wxmylife
 */
public class AdminUserDetails implements UserDetails {

  private UmsAdmin umsAdmin;

  private List<UmsResource> resourceList;

  public AdminUserDetails(UmsAdmin umsAdmin, List<UmsResource> resourceList) {
    this.umsAdmin = umsAdmin;
    this.resourceList = resourceList;
  }

  @Override public Collection<? extends GrantedAuthority> getAuthorities() {
    return resourceList.stream()
        .map(role -> new SimpleGrantedAuthority(role.getId() + ":" + role.getName()))
        .collect(Collectors.toList());
  }

  @Override public String getPassword() {
    return umsAdmin.getPassword();
  }

  @Override public String getUsername() {
    return umsAdmin.getUsername();
  }

  @Override public boolean isAccountNonExpired() {
    return true;
  }

  @Override public boolean isAccountNonLocked() {
    return true;
  }

  @Override public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override public boolean isEnabled() {
    return umsAdmin.getStatus().equals(1);
  }
}
