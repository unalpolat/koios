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
@Constraint(validatedBy = DateValidator.class)
@Documented
public @interface Date {

	String message() default "you should provide a valid date with dd/MM/yyyy format";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
