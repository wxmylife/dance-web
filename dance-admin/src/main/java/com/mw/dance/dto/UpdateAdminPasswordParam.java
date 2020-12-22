package com.mw.dance.dto;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wxmylife
 */
@Getter
@Setter
public class UpdateAdminPasswordParam {

  @NotEmpty
  @ApiModelProperty(value = "手机号", required = true)
  private String telephone;
  @NotEmpty
  @ApiModelProperty(value = "旧密码", required = true)
  private String oldPassword;
  @NotEmpty
  @ApiModelProperty(value = "新密码", required = true)
  private String newPassword;
}
