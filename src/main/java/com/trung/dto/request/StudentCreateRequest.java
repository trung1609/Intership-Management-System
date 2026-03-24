package com.trung.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCreateRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Student code is required")
    public String studentCode;

    @NotBlank(message = "Major is required")
    public String major;

    @NotBlank(message = "Class room is required")
    public String classRoom;

    @NotNull(message = "Date of birth is required")
    public Date dateOfBirth;

    @NotBlank(message = "Address is required")
    public String address;
}
