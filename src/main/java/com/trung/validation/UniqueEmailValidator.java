package com.trung.validation;

import com.trung.exception.ResourceConflictException;
import com.trung.exception.ResourceNotFoundException;
import com.trung.repository.IUserRepository;
import com.trung.util.ValidationErrorUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final IUserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Map<String, String> errorList = ValidationErrorUtil.createErrorMap();
        if (value == null || value.isBlank()) {
            return true;
        }
        
        boolean exists = userRepository.existsByEmailAndIsDeletedFalseAndIsActiveTrue(value);
        
        if (exists) {
            ValidationErrorUtil.addError(errorList, "email", "Email already exists");
        }
        if(ValidationErrorUtil.hasErrors(errorList)) {
            try {
                throw new ResourceConflictException("CONFLICT", errorList);
            } catch (ResourceConflictException e) {
                throw new RuntimeException(e);
            }
        }

        return !exists;
    }
}

