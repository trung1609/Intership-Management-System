package com.trung.mapper;

import com.trung.domain.entity.Users;
import com.trung.domain.enums.Role;
import com.trung.dto.request.UserUpdateRequest;
import com.trung.dto.response.UserResponse;

public class UserMapper {
    public static UserResponse toDto(Users users){
        return UserResponse.builder()
                .userId(users.getUserId())
                .username(users.getUsername())
                .fullName(users.getFullName())
                .email(users.getEmail())
                .role(users.getRole().name())
                .build();
    }

    public static Users toEntity(UserResponse userResponse){
        return Users.builder()
                .userId(userResponse.getUserId())
                .username(userResponse.getUsername())
                .fullName(userResponse.getFullName())
                .email(userResponse.getEmail())
                .role(Role.valueOf(userResponse.getRole()))
                .build();
    }

    public static void updateFromDto(Users users, UserUpdateRequest userUpdateRequest){
        if (userUpdateRequest.getUsername() != null && !userUpdateRequest.getUsername().isBlank()){
            users.setUsername(userUpdateRequest.getUsername());
        }
        if (userUpdateRequest.getFullName() != null && !userUpdateRequest.getFullName().isBlank()) {
            users.setFullName(userUpdateRequest.getFullName());
        }
        if (userUpdateRequest.getEmail() != null && !userUpdateRequest.getEmail().isBlank()) {
            users.setEmail(userUpdateRequest.getEmail());
        }
        if (userUpdateRequest.getPhoneNumber() != null && !userUpdateRequest.getPhoneNumber().isBlank()) {
            users.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        }
    }
}
