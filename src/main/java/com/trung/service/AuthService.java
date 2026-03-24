package com.trung.service;

import com.trung.dto.request.FormLoginRequest;
import com.trung.dto.request.FormRegisterRequest;
import com.trung.dto.request.PageRequestDTO;
import com.trung.dto.response.*;
import com.trung.exception.InvalidCredentialsException;
import com.trung.exception.ResourceConflictException;
import com.trung.exception.ResourceNotFoundException;

import javax.naming.AuthenticationException;

public interface AuthService {
    ApiResponse<RegisterResponse> register(FormRegisterRequest request) throws ResourceConflictException;
    ApiResponse<JwtResponse> login(FormLoginRequest request) throws AuthenticationException, InvalidCredentialsException, ResourceConflictException;
    ApiResponse<UserResponse> getMyProfile(String username) throws ResourceNotFoundException;

}
