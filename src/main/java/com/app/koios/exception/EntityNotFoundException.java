package com.app.koios.exception;

/**
 * @author unalpolat
 */
public class EntityNotFoundException extends AbstractServiceException {

  public EntityNotFoundException() {
    super("1001", "Entity not found");
  }
}
