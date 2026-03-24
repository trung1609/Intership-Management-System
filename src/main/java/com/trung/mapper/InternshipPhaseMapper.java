package com.trung.mapper;

import com.trung.domain.entity.InternshipPhase;
import com.trung.dto.request.InternshipPhaseCreateRequest;
import com.trung.dto.request.InternshipPhaseUpdateRequest;
import com.trung.dto.response.InternshipPhaseResponse;

public class InternshipPhaseMapper {
    public static InternshipPhaseResponse toDto(InternshipPhase internshipPhase){
        return InternshipPhaseResponse.builder()
                .phaseId(internshipPhase.getPhaseId())
                .phaseName(internshipPhase.getPhaseName())
                .startDate(internshipPhase.getStartDate())
                .endDate(internshipPhase.getEndDate())
                .description(internshipPhase.getDescription())
                .build();
    }

    public static InternshipPhase toEntity(InternshipPhaseCreateRequest request){
        return InternshipPhase.builder()
                .phaseName(request.getPhaseName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .description(request.getDescription())
                .build();
    }

    public static void updateFromDto(InternshipPhase internshipPhase, InternshipPhaseUpdateRequest request){
        if (request.getPhaseName() != null) {
            internshipPhase.setPhaseName(request.getPhaseName());
        }
        if (request.getStartDate() != null) {
            internshipPhase.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            internshipPhase.setEndDate(request.getEndDate());
        }
        if (request.getDescription() != null) {
            internshipPhase.setDescription(request.getDescription());
        }
    }
}
