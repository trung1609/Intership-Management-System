package com.trung.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {

    private String username;
    private String fullName;

    @Email(message = "Email is not valid")
    private String email;

    @Pattern(regexp = "^0[356789]\\d{8}$", message = "Phone number is not valid")
    private String phoneNumber;
}
