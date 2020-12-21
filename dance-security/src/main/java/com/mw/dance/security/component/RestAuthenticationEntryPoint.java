package com.mw.dance.security.component;

import cn.hutool.json.JSONUtil;
import com.mw.dance.common.api.CommonResult;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 自定义返回结果：未登录或登录过期
 *
 * @author wxmylife
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
    httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpServletResponse.setHeader("Cache-Control", "no-cache");
    httpServletResponse.setCharacterEncoding("UTF-8");
    httpServletResponse.setContentType("application/json");
    httpServletResponse.getWriter().println(JSONUtil.parse(CommonResult.unauthorized(e.getMessage())));
    httpServletResponse.getWriter().flush();
  }
}
