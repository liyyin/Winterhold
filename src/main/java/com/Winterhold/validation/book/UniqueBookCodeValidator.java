package com.Winterhold.validation.book;

import com.Winterhold.service.absract.BookService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueBookCodeValidator implements ConstraintValidator<UniqueBookCode,String> {

    @Autowired
    private BookService bookService;

    @Override
    public void initialize(UniqueBookCode constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String usernameValue = value;

        boolean result = bookService.uniqueBookCode(usernameValue);

        return !result;
    }
}
