package com.mw.dance.security.component;

import com.mw.dance.security.config.IgnoreUrlsConfig;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * 动态权限过滤器，用于实现基于路径的动态权限过滤
 *
 * @author wxmylife
 */
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {

  private static final Logger LOGGER = LoggerFactory.getLogger(DynamicSecurityFilter.class);

  @Autowired
  private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;
  @Autowired
  private IgnoreUrlsConfig ignoreUrlsConfig;

  @Autowired
  public void setMyAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager) {
    super.setAccessDecisionManager(dynamicAccessDecisionManager);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);

    LOGGER.info("请求路径为:{},请求方式为:{}", request.getRequestURI(),request.getMethod());

    //OPTIONS请求直接放行
    if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
      fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
      return;
    }
    //白名单请求直接放行
    PathMatcher pathMatcher = new AntPathMatcher();
    for (String path : ignoreUrlsConfig.getUrls()) {
      if (pathMatcher.match(path, request.getRequestURI())) {
        LOGGER.info("该路径是白名单:{}", path);
        fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        return;
      }
    }
    //此处会调用AccessDecisionManager中的decide方法进行鉴权操作
    InterceptorStatusToken token = super.beforeInvocation(fi);
    try {
      fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
    } finally {
      super.afterInvocation(token, null);
    }
  }

  @Override
  public void destroy() {
  }

  @Override
  public Class<?> getSecureObjectClass() {
    return FilterInvocation.class;
  }

  @Override
  public SecurityMetadataSource obtainSecurityMetadataSource() {
    return dynamicSecurityMetadataSource;
  }
}