package com.Winterhold.validation.customer;

import com.Winterhold.validation.category.UniqueCategoryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueMembershipValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueMembership {
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default { };
    public String message();
}
