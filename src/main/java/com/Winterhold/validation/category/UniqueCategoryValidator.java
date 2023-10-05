package com.Winterhold.validation.category;


import com.Winterhold.service.absract.CategoryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategoryName,String> {

    @Autowired
    private CategoryService categoryService;

    @Override
    public void initialize(UniqueCategoryName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = categoryService.uniqueCategoryName(value);

        return !result;
    }
}
