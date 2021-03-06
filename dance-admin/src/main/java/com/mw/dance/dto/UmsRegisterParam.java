package com.mw.dance.dto;

import com.mw.dance.common.util.RegexConstants;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author wxmylife
 */
@Getter
@Setter
public class UmsRegisterParam {
  @NotEmpty
  @Length(min = 11, max = 11)
  @Pattern(regexp = RegexConstants.REGEX_MOBILE_EXACT, message = "手机号码格式错误")
  @ApiModelProperty(value = "手机号", required = true)
  private String telephone;

  @NotEmpty
  @Size(min = 6, max = 6)
  @ApiModelProperty(value = "验证码", required = true)
  private String code;

  @NotEmpty
  @ApiModelProperty(value = "密码", required = true)
  private String password;
}
