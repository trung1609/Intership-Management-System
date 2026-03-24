package com.trung.dto.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentUpdateRequest {
    private String studentCode;
    private String major;
    private String classRoom;
    private String address;
    private Date dateOfBirth;
}
