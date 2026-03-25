package com.trung.validation;

import com.trung.exception.ResourceConflictException;
import com.trung.repository.IStudentRepository;
import com.trung.util.ValidationErrorUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UniqueStudentCodeValidator implements ConstraintValidator<UniqueStudentCode, String> {
    private final IStudentRepository studentRepository;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Map<String, String> errorList = ValidationErrorUtil.createErrorMap();
        if (value == null || value.isBlank()) {
            return true;
        }

        String normalizedValue = value.replaceAll("\\s+", "").toUpperCase().trim();
        boolean exist = studentRepository.existsByStudentCode(normalizedValue);

        if (exist) {
            ValidationErrorUtil.addError(errorList, "studentCode", "Student code already exists");
        }

        if(ValidationErrorUtil.hasErrors(errorList)) {
            try {
                throw new ResourceConflictException("CONFLICT", errorList);
            } catch (ResourceConflictException e) {
                throw new RuntimeException(e);
            }
        }

        return !exist;
    }




}
