package com.trung.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoundCriterionResponse {
    private String criterionName;
    private BigDecimal weight;
    private BigDecimal maxScore;
}
