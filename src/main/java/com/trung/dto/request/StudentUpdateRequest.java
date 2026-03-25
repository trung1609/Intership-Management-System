package com.trung.dto.request;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.trung.validation.UniqueStudentCode;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentUpdateRequest {

    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Student code must contain only letters, numbers, and underscores")
    private String studentCode;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String major;
    private String classRoom;
    private String address;

    private String dateOfBirth;
}
