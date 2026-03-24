package com.trung.validation;

import com.trung.repository.IUserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final IUserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        
        boolean exists = userRepository.existsByUsernameAndIsDeletedFalseAndIsActiveTrue(value);
        
        if (!exists) {
            return true;
        }
        
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Username already exists")
               .addConstraintViolation();
        return false;
    }
}

