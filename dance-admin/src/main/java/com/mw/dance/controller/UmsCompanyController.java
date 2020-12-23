package com.mw.dance.controller;

import com.mw.dance.common.api.CommonPage;
import com.mw.dance.common.api.CommonResult;
import com.mw.dance.dto.UmsCompanyRegisterParam;
import com.mw.dance.model.UmsCompany;
import com.mw.dance.service.UmsCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @ApiOperation("公司查询")
  @GetMapping(value = "/list")
  public CommonResult<CommonPage<UmsCompany>> list(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
    List<UmsCompany> companyList = companyService.list(pageSize, pageNum);
    return CommonResult.success(CommonPage.restPage(companyList));
  }

  @ApiOperation("公司状态修改")
  @PostMapping(value = "/update")
  public CommonResult update(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
    if (status > 2) {
      return CommonResult.failed("操作失败");
    }
    if (companyService.updateCompanyStatus(id, status) == 1) {
      return CommonResult.success(null);
    } else {
      return CommonResult.failed();
    }
  }
}
