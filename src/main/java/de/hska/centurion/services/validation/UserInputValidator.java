package de.hska.centurion.services.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import de.hska.centurion.domain.gui.UserInput;

public class UserInputValidator {

	public static String validate(UserInput userInput) {

		Validator v = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<UserInput>> constraintViolations;

		constraintViolations = v.validate(userInput, UserInput.class);
		StringBuffer buffer = new StringBuffer();

		for (ConstraintViolation<UserInput> violation : constraintViolations) {
			buffer.append(violation.getPropertyPath() + " "
					+ violation.getMessage());
			buffer.append("\r\n");
		}

		if (constraintViolations.isEmpty())
			return null;
		else
			return buffer.toString();

	}
}
