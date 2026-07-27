package net.hackyourfuture.tickettrackingsystem.config.validation;

import java.util.List;
import java.util.Arrays;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private List<String> validValues;

    @Override
    public void initialize(ValidEnum annotation) {
        validValues = Arrays.stream(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || validValues.contains(value.toUpperCase());
    }
}