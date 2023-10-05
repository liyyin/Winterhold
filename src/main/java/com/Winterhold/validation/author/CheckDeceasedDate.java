package com.Winterhold.validation.author;

import com.Winterhold.validation.book.UniqueBookCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CheckDeceasedDateValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckDeceasedDate {
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default { };
    public String message();

    public String birthDateField();
    public String deceasedDateField();
}
