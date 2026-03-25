package com.trung.validation;

import com.trung.exception.ResourceConflictException;
import com.trung.repository.IEvaluationCriteriaRepository;
import com.trung.util.ValidationErrorUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UniqueCriteriaValidator implements ConstraintValidator<UniqueCriteria, String> {

    private final IEvaluationCriteriaRepository evaluationCriteriaRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Map<String, String> errorList = ValidationErrorUtil.createErrorMap();
        if (value == null || value.isBlank()) {
            return true;
        }

        String normalizedValue = value.trim().replaceAll("\\s+", " ");
        boolean exist = evaluationCriteriaRepository.existsByCriterionNameIgnoreCaseAndIsDeletedFalse(normalizedValue);

        if (exist) {
            ValidationErrorUtil.addError(errorList, "criterionName", "Evaluation criteria already exists");
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
