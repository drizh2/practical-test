package com.dadry.practicaltest.validators;

import com.dadry.practicaltest.annotations.ValidBirthDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class BirthDateValidator implements ConstraintValidator<ValidBirthDate, String> {

    @Value("${value.age.restriction}")
    private int ageRestriction;

    @Override
    public void initialize(ValidBirthDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String birthDate, ConstraintValidatorContext constraintValidatorContext) {
        return validateBirthDate(birthDate);
    }

    private boolean validateBirthDate(String birthDate) {
        if (Objects.isNull(birthDate)) return true;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate userBirthDate = LocalDate.parse(birthDate, formatter);

        return userBirthDate.isBefore(LocalDate.now()) &&
                userBirthDate.isBefore(LocalDate.now().minusYears(ageRestriction));
    }
}
