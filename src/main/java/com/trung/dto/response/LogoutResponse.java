package com.trung.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LogoutResponse {
    private String message;
    
    // Access Token Info
    private Long accessTokenTTL; // Time To Live in seconds
    private String accessTokenStatus; // "blacklisted"
    
    // Refresh Token Info
    private Long refreshTokenTTL; // Time To Live in seconds
    private String refreshTokenStatus; // "blacklisted"
    
    // Timestamps
    private Long blacklistedAt; // Timestamp khi tokens bị blacklist
}

