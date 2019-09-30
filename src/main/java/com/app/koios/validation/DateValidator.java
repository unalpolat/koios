package com.app.koios.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.validator.GenericValidator;

/**
 * @author unalpolat
 */
public class DateValidator implements ConstraintValidator<Date, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return GenericValidator.isDate(value, "dd/MM/yyyy", true);
	}
}
