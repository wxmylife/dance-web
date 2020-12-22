package com.mw.dance.config;

import com.mw.dance.model.UmsResource;
import com.mw.dance.security.component.DynamicSecurityService;
import com.mw.dance.security.config.SecurityConfig;
import com.mw.dance.service.UmsAdminService;
import com.mw.dance.service.UmsResourceService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author wxmylife
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DanceSecurityConfig extends SecurityConfig {

  @Autowired
  private UmsAdminService adminService;

  @Autowired
  private UmsResourceService resourceService;

  @Override @Bean
  public UserDetailsService userDetailsService() {
    //获取登录用户信息
    return username -> adminService.loadUserByUsername(username);
  }

  @Bean
  public DynamicSecurityService dynamicSecurityService() {
    return new DynamicSecurityService() {
      @Override
      public Map<String, ConfigAttribute> loadDataSource() {
        Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
        List<UmsResource> resourceList = resourceService.listAll();
        for (UmsResource resource : resourceList) {
          map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
        }
        return map;
      }
    };
  }

}
