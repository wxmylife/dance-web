package com.mw.dance.common.util;

/**
 * @author wxmylife
 */
public class RegexConstants {

  public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[57])|(15[0-35-9])|(16[2567])|(17[01235-8])|(18[0-9])|(19[189]))\\d{8}$";

  public static final int AUTH_CODE_LENGTH = 6;

  public static final String REGEX_ID_CARD15     = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
  /**
   * Regex of id card number which length is 18.
   */
  public static final String REGEX_ID_CARD18     = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
}
