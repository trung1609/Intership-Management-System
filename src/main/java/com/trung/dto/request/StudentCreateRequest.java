package com.trung.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCreateRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Student code is required")
    @Pattern(regexp = "^(|S[a-zA-Z0-9]{7})$", message = "Student code must start with 'S' followed by 7 digits.")
    public String studentCode;

    @NotBlank(message = "Major is required")
    @Pattern(regexp = "^(|[\\p{L}0-9]+( [\\p{L}0-9]+)*)$", message = "Major must contain only letters and numbers separated by single spaces")
    public String major;

    @NotBlank(message = "Class room is required")
    @Pattern(regexp = "^(|[\\p{L}0-9]+( [\\p{L}0-9]+)*)$", message = "Class room must contain only letters and numbers separated by single spaces")
    public String classRoom;

    @NotBlank(message = "Date of birth is required")
    public String dateOfBirth;

    @NotBlank(message = "Address is required")
    @Pattern(regexp = "^(|[\\p{L}0-9]+( [\\p{L}0-9]+)*)$", message = "Address must contain only letters and numbers separated by single spaces")
    public String address;
}
