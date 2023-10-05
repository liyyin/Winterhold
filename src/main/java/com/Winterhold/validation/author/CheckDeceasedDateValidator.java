package com.Winterhold.validation.author;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class CheckDeceasedDateValidator implements ConstraintValidator<CheckDeceasedDate,Object> {

    private String birthDateField;
    private String deceasedDateField;
    private String message;

    @Override
    public void initialize(CheckDeceasedDate constraintAnnotation) {
        this.birthDateField = constraintAnnotation.birthDateField();
        this.deceasedDateField = constraintAnnotation.deceasedDateField();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try{
        LocalDate birthDateValue = LocalDate.parse(new BeanWrapperImpl(value).getPropertyValue(birthDateField).toString());
        LocalDate deceasedDateValue = LocalDate.parse(new BeanWrapperImpl(value).getPropertyValue(deceasedDateField).toString());

        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(deceasedDateField)
                .addConstraintViolation();
            if(deceasedDateValue.isBefore(birthDateValue)){
                return false;
            }else {
                return true;
            }
        }catch (Exception ex){
            return true;
        }
    }
}
