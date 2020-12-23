package com.mw.dance.dto;

import com.mw.dance.common.util.RegexConstants;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author wxmylife
 */
@Setter
@Getter
public class UmsCompanyRegisterParam {

  @NotEmpty
  @ApiModelProperty(value = "公司名称", required = true)
  private String name;

  @NotEmpty
  @Size(min = 2,max = 4)
  @ApiModelProperty(value = "法人名称", required = true)
  private String legalName;

  @NotNull
  @ApiModelProperty(value = "省", required = true)
  private int province;

  @NotNull
  @ApiModelProperty(value = "市", required = true)
  private int city;

  @NotNull
  @ApiModelProperty(value = "区", required = true)
  private int district;

  @NotEmpty
  @ApiModelProperty(value = "地址", required = true)
  private String address;

  @ApiModelProperty(value = "管理者id", required = true)
  private int adminId;

  @NotEmpty
  @ApiModelProperty(value = "营业执照", required = true)
  private String businessLicense;

  @NotEmpty
  @ApiModelProperty(value = "营业执照编号", required = true)
  private String businessLicenseNumber;

  @NotEmpty
  @Length(min = 11, max = 11)
  @Pattern(regexp = RegexConstants.REGEX_MOBILE_EXACT, message = "手机号码格式错误")
  @ApiModelProperty(value = "手机号", required = true)
  private String telephone;

}
