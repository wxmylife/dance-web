package com.mw.dance.controller;

import com.mw.dance.common.api.CommonResult;
import com.mw.dance.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxmylife
 */
@RestController
@Api(value = "/后台账号管理", tags = { "后台用户管理" })
@RequestMapping("v1/admin")
public class UmsAdminController {

  @Autowired
  private UmsAdminService adminService;

  @ApiOperation("获取验证码")
  @PostMapping(value = "/getAuthCode")
  public CommonResult<String> getAuthCode(@RequestParam String telephone) {
    String authCode = adminService.generateAuthCode(telephone);
    return CommonResult.success(authCode, "获取验证码成功");
  }
}
