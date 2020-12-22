package com.mw.dance.dto;

import com.mw.dance.common.util.RegexConstants;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * @author wxmylife
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UmsLoginParam {
  @NotEmpty
  @Length(min = 11, max = 11)
  @Pattern(regexp = RegexConstants.REGEX_MOBILE_EXACT, message = "手机号码格式错误")
  @ApiModelProperty(value = "手机号", required = true)
  private String telephone;

  @NotEmpty
  @ApiModelProperty(value = "密码", required = true)
  private String password;
}
