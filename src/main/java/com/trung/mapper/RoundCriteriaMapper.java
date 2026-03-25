package com.trung.mapper;

import com.trung.domain.entity.RoundCriteria;
import com.trung.dto.request.RoundCriterionUpdateRequest;
import com.trung.dto.response.RoundCriterionResponse;

public class RoundCriteriaMapper {
    public static RoundCriterionResponse toDto(RoundCriteria rc) {
        return RoundCriterionResponse.builder()
                .criterionName(rc.getCriterion().getCriterionName())
                .weight(rc.getWeight())
                .maxScore(rc.getCriterion().getMaxScore())
                .build();
    }

    public static void updateFromDto(RoundCriteria rc, RoundCriterionUpdateRequest request) {
        if (request.getWeight() != null) {
            rc.setWeight(request.getWeight());
        }
    }
}
