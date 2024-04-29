package com.dadry.practicaltest.annotations;

import com.dadry.practicaltest.validators.FromBeforeToValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FromBeforeToValidator.class)
@Documented
public @interface FromBeforeTo {
    String message() default "From Date should be before To Date!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default  {};
}
