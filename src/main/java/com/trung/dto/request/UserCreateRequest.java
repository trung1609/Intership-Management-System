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
public class UserCreateRequest {

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username must contain only letters, numbers, and underscores")
    @UniqueUsername
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Full name is required")
    @Pattern(regexp = "^[\\p{L}0-9]+(\\s[\\p{L}0-9]+)*$", message = "Full name must contain only letters and numbers, and cannot have leading or trailing spaces")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    @UniqueEmail
    private String email;

    @Pattern(regexp = "^0[356789]\\d{8}$", message = "Phone number is invalid")
    private String phoneNumber;

    @NotBlank(message = "Role is required")
    private String role;
}
