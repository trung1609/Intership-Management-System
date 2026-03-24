package com.trung.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.errorprone.annotations.FormatMethod;
import com.trung.validation.UniquePhaseName;
import com.trung.validation.ValidDateRange;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.processing.Pattern;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ValidDateRange(startDateField = "startDate", endDateField = "endDate")
public class InternshipPhaseCreateRequest {
    @NotBlank(message = "Phase name is required")
    @UniquePhaseName
    private String phaseName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;


    private String description;
}
