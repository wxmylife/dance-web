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
public class UmsRegisterParam {
  @NotEmpty
  @ApiModelProperty(value = "手机号", required = true)
  private String phone;

  @NotEmpty
  @ApiModelProperty(value = "验证码", required = true)
  private String code;
}
