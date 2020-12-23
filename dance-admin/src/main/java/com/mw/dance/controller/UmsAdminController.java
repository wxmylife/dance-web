package com.mw.dance.controller;

import com.mw.dance.common.api.CommonResult;
import com.mw.dance.common.util.RegexConstants;
import com.mw.dance.dto.ForgetAdminPasswordParam;
import com.mw.dance.dto.UmsLoginParam;
import com.mw.dance.dto.UmsRegisterParam;
import com.mw.dance.dto.UpdateAdminPasswordParam;
import com.mw.dance.model.UmsAdmin;
import com.mw.dance.service.UmsAdminCacheService;
import com.mw.dance.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxmylife
 */
@RestController
@Api(value = "/后台账号管理", tags = { "后台用户管理" })
@RequestMapping("v1/admin")
public class UmsAdminController {

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Autowired
  private UmsAdminService adminService;

  @ApiOperation("获取验证码")
  @GetMapping(value = "/getAuthCode")
  public CommonResult<String> getAuthCode(@ApiParam("手机号") @RequestParam @Validated
                                          @Pattern(regexp = RegexConstants.REGEX_MOBILE_EXACT, message = "手机号码格式错误")
                                              String telephone) {
    String authCode = adminService.generateAuthCode(telephone);
    return CommonResult.success(authCode, "获取验证码成功");
  }

  @ApiOperation("用户注册")
  @PostMapping(value = "/register")
  public CommonResult<UmsAdmin> register(@Validated @RequestBody UmsRegisterParam umsRegisterParam) {
    UmsAdmin umsAdmin = adminService.register(umsRegisterParam);
    if (umsAdmin == null) {
      return CommonResult.failed();
    }
    return CommonResult.success(umsAdmin);
  }

  @ApiOperation(value = "登出功能")
  @RequestMapping(value = "/logout", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult logout() {
    return CommonResult.success(null);
  }

  @ApiOperation("用户登录")
  @PostMapping(value = "/login")
  public CommonResult<Map<String, String>> login(@Validated @RequestBody UmsLoginParam umsLoginParam) {
    String token = adminService.login(umsLoginParam.getTelephone(), umsLoginParam.getPassword());
    if (token == null) {
      return CommonResult.validateFailed("用户名或密码错误");
    }
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", token);
    tokenMap.put("tokenHead", tokenHead);
    return CommonResult.success(tokenMap);
  }

  @ApiOperation("获取用户信息")
  @GetMapping(value = "/info")
  public CommonResult<UmsAdmin> getAdminInfo(Principal principal) {
    if (principal == null) {
      return CommonResult.unauthorized(null);
    }
    String telephone = principal.getName();
    UmsAdmin admin = adminService.getAdminByTelephone(telephone);
    // Map<String, String> tokenMap = new HashMap<>();
    // tokenMap.put("token", token);
    // tokenMap.put("tokenHead", tokenHead);
    return CommonResult.success(admin);
  }

  @ApiOperation("修改指定用户密码")
  @PostMapping(value = "/updatePassword")
  public CommonResult updatePassword(@Validated @RequestBody UpdateAdminPasswordParam updateAdminPasswordParam) {
    int status = adminService.updatePassword(updateAdminPasswordParam);
    return handleChangePassword(status);
  }

  private CommonResult handleChangePassword(int status) {
    if (status > 0) {
      return CommonResult.success(status);
    } else if (status == -1) {
      return CommonResult.failed("提交参数不合法");
    } else if (status == -2) {
      return CommonResult.failed("找不到该用户");
    } else if (status == -3) {
      return CommonResult.failed("旧密码错误");
    } else {
      return CommonResult.failed();
    }
  }

  @ApiOperation("忘记密码")
  @PostMapping(value = "/forgetPassword")
  public CommonResult forgetPassword(@Validated @RequestBody ForgetAdminPasswordParam forgetAdminPasswordParam) {
    int status = adminService.forgetPassword(forgetAdminPasswordParam);
    return handleChangePassword(status);
  }

}
