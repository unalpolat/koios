package com.app.koios.response;

/**
 * @author unalpolat
 */
public class ServiceResponse<T> {

  private static final ServiceResponse DEFAULT_SUCCESS = from(null);

  private final Integer status;

  private final Boolean successful;

  private final String errorCode;

  private final String errorDetail;

  private final String errorMessageKey;

  private final T data;

  public ServiceResponse(Integer status, Boolean successful, String errorCode, String errorDetail,
                         String messageKey, T data) {
    this.status = status;
    this.successful = successful;
    this.errorCode = errorCode;
    this.errorDetail = errorDetail;
    this.errorMessageKey = messageKey;
    this.data = data;
  }

  public static <T> ServiceResponse<T> from(T data) {
    return new ServiceResponse<>(200, true, null, null, null, data);
  }

  public static ServiceResponse successful() {
    return DEFAULT_SUCCESS;
  }

  public Integer getStatus() {
    return status;
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
           "status=" + status +
           ", successful=" + successful +
           ", errorCode='" + errorCode + '\'' +
           ", errorDetail='" + errorDetail + '\'' +
           ", errorMessageKey='" + errorMessageKey + '\'' +
           ", data=" + data +
           '}';
  }
}
