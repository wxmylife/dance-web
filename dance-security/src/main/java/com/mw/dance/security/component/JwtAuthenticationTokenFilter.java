package com.mw.dance.security.component;

import com.mw.dance.common.service.RedisService;
import com.mw.dance.security.util.JwtTokenUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JWT登录授权过滤器
 *
 * @author wxmylife
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
  private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private RedisService redisService;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Value("${jwt.tokenHeader}")
  private String tokenHeader;

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Value("${redis.key.auth}")
  private String REDIS_KEY_AUTH;

  @Value("${redis.database}")
  private String REDIS_DATABASE;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain) throws ServletException, IOException {
    String authHeader = request.getHeader(this.tokenHeader);
    LOGGER.info("读取请求 header: {}", authHeader);

    if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
      String authToken = authHeader.substring(this.tokenHead.length());
      LOGGER.info("读取请求 authToken: {}", authToken);
      String telephone = jwtTokenUtil.getUserTelephoneFromToken(authToken);
      LOGGER.info("读取请求 telephone: {}", telephone);
      String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH + ":" + telephone;
      // if (telephone != null && SecurityContextHolder.getContext().getAuthentication() == null && redisService.get(key) != null) {

      if (telephone != null  && redisService.get(key) != null) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(telephone);
        if (jwtTokenUtil.validateToken(authToken, userDetails)) {
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          LOGGER.info("authenticated user:{}", telephone);
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    }
    chain.doFilter(request, response);
  }
}
