package com.trung.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponse {
    public Long studentId;

    public String studentCode;

    public String major;

    public String classRoom;

    public Date dateOfBirth;

    public String address;
}
