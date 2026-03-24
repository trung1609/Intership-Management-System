package com.trung.dto.response;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class JwtResponse {
    private String token;
    private final String type = "Bearer";
    private String username;
    private Date expiresIn;
    private UserResponse user;
}
