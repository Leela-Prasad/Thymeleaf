package com.virtualpairprogrammers.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ISBNValidator implements ConstraintValidator<ISBN, String> 
{
	//@Override
	public void initialize(ISBN arg0) {
		// TODO Auto-generated method stub		
	}

	//@Override
	public boolean isValid(String raw, ConstraintValidatorContext arg1) 
	{
		return true;
	}

}
