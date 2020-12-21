package com.mw.dance.common.exception;

import com.mw.dance.common.api.IErrorCode;

/**
 * @author wxmylife
 */
public class Asserts {

  public static void fail(String message) {
    throw new ApiException(message);
  }

  public static void fail(IErrorCode errorCode) {
    throw new ApiException(errorCode);
  }
}
