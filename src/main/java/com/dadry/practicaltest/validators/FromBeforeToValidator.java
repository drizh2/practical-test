package com.dadry.practicaltest.validators;

import com.dadry.practicaltest.annotations.FromBeforeTo;
import com.dadry.practicaltest.payload.request.FindUsersByDatesRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FromBeforeToValidator implements ConstraintValidator<FromBeforeTo, Object> {

    @Override
    public void initialize(FromBeforeTo constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        FindUsersByDatesRequest request = (FindUsersByDatesRequest) obj;
        return request.getFromDate().isBefore(request.getToDate());
    }


}
