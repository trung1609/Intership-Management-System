package com.trung.dto.response;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long userId;
    private String username;
    private String fullName;
    private String email;
    private String role;
}
