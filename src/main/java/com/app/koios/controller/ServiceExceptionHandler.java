package com.app.koios.controller;

import com.app.koios.exception.AbstractServiceException;
import com.app.koios.response.ServiceResponse;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.lang.String.format;

/**
 * @author unalpolat
 */
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String KEY_PREFIX = "serviceExceptions.";

  private static final String INTERNAL_EXCEPTION_KEY = KEY_PREFIX + "internalException.";

  private static final String VALIDATION_EXCEPTION_KEY = KEY_PREFIX + "validationException.";

  @ExceptionHandler(AbstractServiceException.class)
  public ResponseEntity<ServiceResponse> handleServiceException(AbstractServiceException ex) {
    return ResponseEntity.ok(createFailedServiceResponse(HttpStatus.OK.value(),
                                                         ex.getErrorCode(),
                                                         ex.getErrorDetail(),
                                                         INTERNAL_EXCEPTION_KEY + ex.getClass().getSimpleName()));
  }

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
    FieldError fieldError = ex.getBindingResult().getFieldError();
    assert fieldError != null;
    String errorDetail = format("%s %s", fieldError.getField(), fieldError.getDefaultMessage());
    return ResponseEntity.ok(createFailedServiceResponse(HttpStatus.OK.value(),
                                                         "1002",
                                                         errorDetail,
                                                         VALIDATION_EXCEPTION_KEY + ex.getClass().getSimpleName()));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ServiceResponse> handleConstraintViolation(ConstraintViolationException ex) {
    return ResponseEntity.ok(createFailedServiceResponse(HttpStatus.OK.value(),
                                                         "1003",
                                                         ex.getMessage(),
                                                         VALIDATION_EXCEPTION_KEY + ex.getClass().getSimpleName()));
  }

  @ExceptionHandler(value = RuntimeException.class)
  public ResponseEntity<ServiceResponse> handleRuntimeException(RuntimeException ex) {
    return ResponseEntity.ok(createFailedServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                                         "999",
                                                         ex.getMessage(),
                                                         INTERNAL_EXCEPTION_KEY + ex.getClass().getSimpleName()));
  }

  private ServiceResponse createFailedServiceResponse(Integer status,
                                                      String errorCode,
                                                      String errorDetail,
                                                      String messageKey) {
    return new ServiceResponse(status, false, errorCode, errorDetail, messageKey, null);
  }
}
