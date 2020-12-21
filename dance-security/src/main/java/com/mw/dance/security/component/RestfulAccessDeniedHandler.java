package com.mw.dance.security.component;

import cn.hutool.json.JSONUtil;
import com.mw.dance.common.api.CommonResult;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 自定义返回结果：没有权限访问时
 *
 * @author wxmylife
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
  @Override public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
    httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpServletResponse.setHeader("Cache-Control", "no-cache");
    httpServletResponse.setCharacterEncoding("UTF-8");
    httpServletResponse.getWriter().println(JSONUtil.parse(CommonResult.forbidden(e.getMessage())));
    httpServletResponse.getWriter().flush();
  }
}
