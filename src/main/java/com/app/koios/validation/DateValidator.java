package com.app.koios.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.validator.GenericValidator;

import static java.util.Objects.nonNull;

/**
 * @author unalpolat
 */
public class DateValidator implements ConstraintValidator<Date, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return nonNull(value) && GenericValidator.isDate(value, "dd/MM/yyyy", true);
	}
}
