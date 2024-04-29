package com.dadry.practicaltest.annotations;

import com.dadry.practicaltest.validators.BirthDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthDateValidator.class)
@Documented
public @interface ValidBirthDate {
    String message() default "User has to be born before now and has 18 years old!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default  {};
}
