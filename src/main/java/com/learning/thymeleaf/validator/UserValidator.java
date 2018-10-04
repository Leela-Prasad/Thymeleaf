package com.learning.thymeleaf.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.learning.thymeleaf.model.User;

public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors e) {
		User user = (User)obj;
		if(!(user.getPassword().equals(user.getConfirmPassword()))) {
			e.rejectValue("confirmPassword", "PasswordsDontMatch");
		}
	}

}
