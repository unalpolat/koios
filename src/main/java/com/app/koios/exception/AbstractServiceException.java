package com.app.koios.exception;

/**
 * @author unalpolat
 */
public abstract class AbstractServiceException extends Exception {

  private final String errorCode;

  private final String errorDetail;

  protected AbstractServiceException(String errorCode, String message) {
    this(errorCode, message, message);
  }

  public AbstractServiceException(String errorCode, String message, String errorDetail) {
    super(message);
    this.errorCode = errorCode;
    this.errorDetail = errorDetail;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorDetail() {
    return errorDetail;
  }
}
