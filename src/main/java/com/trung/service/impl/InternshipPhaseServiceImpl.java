package com.trung.service.impl;

import com.trung.domain.entity.InternshipPhase;
import com.trung.dto.request.InternshipPhaseCreateRequest;
import com.trung.dto.request.InternshipPhaseUpdateRequest;
import com.trung.dto.request.PageRequestDTO;
import com.trung.dto.response.ApiResponse;
import com.trung.dto.response.InternshipPhaseResponse;
import com.trung.dto.response.PageResponseDTO;
import com.trung.exception.ResourceConflictException;
import com.trung.exception.ResourceNotFoundException;
import com.trung.mapper.InternshipPhaseMapper;
import com.trung.repository.InternshipPhaseRepository;
import com.trung.service.InternshipPhaseService;
import com.trung.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InternshipPhaseServiceImpl implements InternshipPhaseService {
    private final InternshipPhaseRepository internshipPhaseRepository;


    @Override
    public ApiResponse<InternshipPhaseResponse> createInternshipPhase(InternshipPhaseCreateRequest request) throws ResourceConflictException {
        InternshipPhase internshipPhase = new InternshipPhase();


        internshipPhase.setPhaseName(request.getPhaseName());
        internshipPhase.setStartDate(request.getStartDate());
        internshipPhase.setEndDate(request.getEndDate());
        internshipPhase.setDescription(request.getDescription());
        internshipPhaseRepository.save(internshipPhase);

        return new ApiResponse<>(
                InternshipPhaseMapper.toDto(internshipPhase),
                true,
                "Create internship phase successfully",
                null,
                LocalDateTime.now()
        );
    }

    @Override
    public PageResponseDTO<InternshipPhaseResponse> getAllInternshipPhase(String search, PageRequestDTO pageRequestDTO) {
        Pageable pageable = PaginationUtil.createPageRequest(pageRequestDTO);
        Page<InternshipPhase> internshipPhasePage;

        if (search != null && !search.isBlank()) {
            internshipPhasePage = internshipPhaseRepository.findAllByKeyword(pageable, search);
        } else {
            internshipPhasePage = internshipPhaseRepository.findAllByIsDeletedFalse(pageable);
        }
        return PaginationUtil.toPageResponseDTO(internshipPhasePage, InternshipPhaseMapper::toDto);
    }

    @Override
    public ApiResponse<InternshipPhaseResponse> getInternshipPhaseById(Long id) throws ResourceNotFoundException {
        InternshipPhase internshipPhase = internshipPhaseRepository.findByPhaseIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Internship phase not found with id: " + id));

        return new ApiResponse<>(InternshipPhaseMapper.toDto(internshipPhase), true, "SUCCESS", null, LocalDateTime.now());
    }

    @Override
    public ApiResponse<InternshipPhaseResponse> updateInternshipPhase(Long id, InternshipPhaseUpdateRequest request) throws ResourceNotFoundException, ResourceConflictException {
        Map<String, String> errors = new HashMap<>();
        InternshipPhase existingPhase = internshipPhaseRepository.findByPhaseIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Internship phase not found with id: " + id));

        if (internshipPhaseRepository.existsByPhaseNameIgnoreCaseAndIsDeletedFalseAndPhaseIdNot(request.getPhaseName(), id)) {
            errors.put("phaseName", "Internship phase name already exists");
        }
        if (!errors.isEmpty()) {
            throw new ResourceConflictException("Validation failed", errors);
        }

        InternshipPhaseMapper.updateFromDto(existingPhase, request);
        internshipPhaseRepository.save(existingPhase);
        return new ApiResponse<>(
                InternshipPhaseMapper.toDto(existingPhase),
                true,
                "SUCCESS",
                null,
                LocalDateTime.now());
    }

    @Override
    public ApiResponse<String> deleteInternshipPhase(Long id) throws ResourceNotFoundException {
        InternshipPhase existingPhase = internshipPhaseRepository.findByPhaseIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Internship phase not found with id: " + id));
        existingPhase.setDeleted(true);
        internshipPhaseRepository.save(existingPhase);
        return new ApiResponse<>("Internship phase deleted successfully",
                true,
                "SUCCESS",
                null,
                LocalDateTime.now());
    }
}
