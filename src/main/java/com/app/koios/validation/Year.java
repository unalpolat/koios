package com.app.koios.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author unalpolat
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = YearValidator.class)
@Documented
public @interface Year {

  String message() default "must be valid with format: yyyy";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
