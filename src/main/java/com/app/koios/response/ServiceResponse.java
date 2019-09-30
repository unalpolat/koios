package com.app.koios.response;

/**
 * @author unalpolat
 */
public class ServiceResponse<T> {

  private final Boolean successful;

  private final String errorCode;

  private final String errorDetail;

  private final String errorMessageKey;

  private final T data;

  public ServiceResponse(Boolean successful, String errorCode, String errorDetail, String messageKey, T data) {
    this.successful = successful;
    this.errorCode = errorCode;
    this.errorDetail = errorDetail;
    this.errorMessageKey = messageKey;
    this.data = data;
  }

  public static <T> ServiceResponse<T> from(T data) {
    return new ServiceResponse<>(true, null, null, null, data);
  }

  public static <T> ServiceResponse<T> failure(String errorCode, String errorDetail) {
    return new ServiceResponse<>(false, errorCode, errorDetail, null, null);
  }

  public Boolean getSuccessful() {
    return successful;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorDetail() {
    return errorDetail;
  }

  public String getErrorMessageKey() {
    return errorMessageKey;
  }

  public T getData() {
    return data;
  }

  @Override
  public String toString() {
    return "ServiceResponse{" +
           "successful=" + successful +
           ", errorCode='" + errorCode + '\'' +
           ", errorDetail='" + errorDetail + '\'' +
           ", data=" + data +
           '}';
  }
}
