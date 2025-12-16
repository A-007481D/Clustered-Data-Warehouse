package com.progresssoft.assignment.clustered_data_warehouse.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CurrencyValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCurrency {
    String message() default "Invalid Currency ISO Code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
