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
    ServiceResponse response = new ServiceResponse(false, ex.getErrorCode(), ex.getErrorDetail(),
                                                   INTERNAL_EXCEPTION_KEY + ex.getClass().getSimpleName(),
                                                   null);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
    FieldError fieldError = ex.getBindingResult().getFieldError();
    String errorDetail = "for " + fieldError.getField() + ", " + fieldError.getDefaultMessage();
    ServiceResponse response = new ServiceResponse(false, null, errorDetail,
                                                   VALIDATION_EXCEPTION_KEY + ex.getClass().getSimpleName(),
                                                   null);
    return ResponseEntity.ok(response);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ServiceResponse> handleConstraintViolation(ConstraintViolationException ex) {
    ServiceResponse response = new ServiceResponse(false, null, ex.getMessage(),
                                                   INTERNAL_EXCEPTION_KEY + ex.getClass().getSimpleName(),
                                                   null);
    return ResponseEntity.ok(response);
  }
}
