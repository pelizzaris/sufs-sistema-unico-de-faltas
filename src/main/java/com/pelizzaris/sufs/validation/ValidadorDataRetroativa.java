package com.pelizzaris.sufs.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidadorDataRetroativa implements ConstraintValidator<DataRetroativa, LocalDate> {

    private int daysLimit;

    @Override
    public void initialize(DataRetroativa constraintAnnotation) {
        this.daysLimit = constraintAnnotation.days();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {

        if (value == null) return true;

        LocalDate today = LocalDate.now();
        LocalDate limit = today.minusDays(daysLimit);

        return !value.isAfter(today) && !value.isBefore(limit);
    }
}
