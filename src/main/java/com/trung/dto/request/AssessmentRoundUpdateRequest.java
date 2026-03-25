package com.trung.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssessmentRoundUpdateRequest {

    @Pattern(regexp = "^[\\p{L}0-9]+(\\s[\\p{L}0-9]+)*$", message = "Round name must contain only letters and numbers, and cannot have leading or trailing spaces.")
    private String roundName;
    private String startDate;
    private String endDate;
    private String description;
    private Boolean isActive;

    @Valid
    private List<RoundCriterionUpdateRequest> criteria;
}
