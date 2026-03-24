package com.trung.dto.request;

import com.trung.validation.UniqueEmail;
import com.trung.validation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormRegisterRequest {
    @NotBlank(message = "Username is required")
    @UniqueUsername
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    @UniqueEmail
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^0[356789]\\d{8}$", message = "Phone number is invalid")
    private String phoneNumber;

    private String role;
}
