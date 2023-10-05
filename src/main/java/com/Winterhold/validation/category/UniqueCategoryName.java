package com.Winterhold.validation.category;

import com.Winterhold.validation.book.UniqueBookCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueCategoryValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCategoryName {
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default { };
    public String message();
}
