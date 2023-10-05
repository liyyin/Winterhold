package com.Winterhold.validation.customer;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = value.matches("[0-9-]*");

        return result;
    }
}
