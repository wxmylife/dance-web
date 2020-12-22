package com.mw.dance.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wxmylife
 */
@Setter
@Getter
public class ForgetAdminPasswordParam {

  @NotEmpty
  @ApiModelProperty(value = "手机号", required = true)
  private String telephone;

  @NotEmpty
  @Size(min = 6, max = 6)
  @ApiModelProperty(value = "验证码", required = true)
  private String code;

  @ApiModelProperty(value = "新密码", required = true)
  private String newPassword;
}
