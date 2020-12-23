package com.mw.dance.controller;

import com.mw.dance.common.api.CommonResult;
import com.mw.dance.dto.UmsCompanyRegisterParam;
import com.mw.dance.model.UmsCompany;
import com.mw.dance.service.UmsCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wxmylife
 */
@RestController
@Api(value = "/后台平台公司管理", tags = { "后台平台公司管理" })
@RequestMapping("v1/company")
public class UmsCompanyController {

  @Autowired
  private UmsCompanyService companyService;

  @ApiOperation("公司注册")
  @PostMapping(value = "/register")
  public CommonResult<UmsCompany> register(@Validated @RequestBody UmsCompanyRegisterParam umsRegisterParam) {
    UmsCompany umsCompany = companyService.register(umsRegisterParam);
    if (umsCompany == null) {
      return CommonResult.failed();
    }
    return CommonResult.success(umsCompany);
  }
}
