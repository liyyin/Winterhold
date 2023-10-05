package com.Winterhold.validation.customer;


import com.Winterhold.service.absract.CustomerService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueMembershipValidator implements ConstraintValidator<UniqueMembership,String> {

    @Autowired
    private CustomerService customerService;

    @Override
    public void initialize(UniqueMembership constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = customerService.uniqueMembership(value);

        return !result;
    }
}
