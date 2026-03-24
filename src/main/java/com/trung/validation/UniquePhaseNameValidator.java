package com.trung.validation;

import com.trung.repository.InternshipPhaseRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePhaseNameValidator implements ConstraintValidator<UniquePhaseName, String> {
    private final InternshipPhaseRepository internshipPhaseRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        
        boolean exists = internshipPhaseRepository.existsByPhaseNameIgnoreCaseAndIsDeletedFalse(value);
        
        if (!exists) {
            return true;
        }
        
        // Tùy chỉnh thông báo lỗi
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Internship phase already exists")
               .addConstraintViolation();
        return false;
    }
}

