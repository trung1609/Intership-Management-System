package com.trung.service.impl;

import com.trung.domain.entity.Users;
import com.trung.domain.enums.Role;
import com.trung.dto.request.PageRequestDTO;
import com.trung.dto.request.UserCreateRequest;
import com.trung.dto.request.UserUpdateRequest;
import com.trung.dto.response.ApiResponse;
import com.trung.dto.response.PageResponseDTO;
import com.trung.dto.response.UserResponse;
import com.trung.exception.ResourceBadRequestException;
import com.trung.exception.ResourceConflictException;
import com.trung.exception.ResourceNotFoundException;
import com.trung.mapper.UserMapper;
import com.trung.repository.IUserRepository;
import com.trung.service.UserService;
import com.trung.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Map<String, String> errorList = new HashMap<>();

    @Override
    public PageResponseDTO<UserResponse> getAllProfile(String role, PageRequestDTO pageRequestDTO) throws ResourceBadRequestException {

        Pageable pageable = PaginationUtil.createPageRequest(pageRequestDTO);

        Page<Users> usersPage;
        Role roleEnum = null;
        if (role != null && !role.isBlank()) {
            try {
                roleEnum = Role.valueOf(role.toUpperCase());
            } catch (IllegalArgumentException e) {
                errorList.put("role", "Invalid role value");
            }
        }
        if (!errorList.isEmpty()) {
            throw new ResourceBadRequestException("BAD_REQUEST", errorList);
        }

        if (roleEnum != null) {
            usersPage = userRepository.findByRoleAndIsDeletedFalseAndIsActiveTrue(Role.valueOf(role.toUpperCase()), pageable);
        } else {
            usersPage = userRepository.findAllByIsDeletedFalseAndIsActiveTrue(pageable);
        }

        return PaginationUtil.toPageResponseDTO(usersPage, UserMapper::toDto);
    }

    @Override
    public ApiResponse<UserResponse> getProfileById(Long id) throws ResourceConflictException, ResourceNotFoundException {
        Users users = userRepository.findByUserIdAndIsDeletedFalseAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return new ApiResponse<>(UserMapper.toDto(users), true, "SUCCESS", null, null);
    }

    @Override
    public ApiResponse<UserResponse> createProfile(UserCreateRequest userCreateRequest) throws ResourceConflictException, ResourceBadRequestException {
        if (userRepository.existsByUsernameAndIsDeletedFalseAndIsActiveTrue(userCreateRequest.getUsername())) {
            errorList.put("username", "Username already exists");
        }
        if (userRepository.existsByEmailAndIsDeletedFalseAndIsActiveTrue(userCreateRequest.getEmail())) {
            errorList.put("email", "Email already exists");
        }
        if (!errorList.isEmpty()) {
            throw new ResourceConflictException("CONFLICT", errorList);
        }

        Role roleEnum = null;
        if (userCreateRequest.getRole() != null && !userCreateRequest.getRole().isBlank()) {
            try {
                roleEnum = Role.valueOf(userCreateRequest.getRole().toUpperCase());
            } catch (IllegalArgumentException e) {
                errorList.put("role", "Invalid role value");
            }
        }
        if (!errorList.isEmpty()) {
            throw new ResourceBadRequestException("BAD_REQUEST", errorList);
        }

        Users users = new Users();
        users.setUsername(userCreateRequest.getUsername());
        users.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        users.setFullName(userCreateRequest.getFullName());
        users.setEmail(userCreateRequest.getEmail());
        users.setPhoneNumber(userCreateRequest.getPhoneNumber());

        if (roleEnum != null) {
            users.setRole(roleEnum);
        }

        userRepository.save(users);

        return new ApiResponse<>(UserMapper.toDto(users), true, "SUCCESS", null, LocalDateTime.now());
    }

    @Override
    public ApiResponse<UserResponse> updateProfile(Long id, UserUpdateRequest userUpdateRequest) throws ResourceConflictException, ResourceNotFoundException {
        Users existingUser = userRepository.findByUserIdAndIsDeletedFalseAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        if (userRepository.existsByUsernameAndIsDeletedFalseAndIsActiveTrueAndUserIdNot(userUpdateRequest.getUsername(), id)) {
            errorList.put("username", "Username already exists");
        }

        if (userRepository.existsByEmailAndIsDeletedFalseAndIsActiveTrueAndUserIdNot(userUpdateRequest.getEmail(), id)) {
            errorList.put("email", "Email already exists");
        }
        if (!errorList.isEmpty()) {
            throw new ResourceConflictException("CONFLICT", errorList);
        }
        UserMapper.updateFromDto(existingUser, userUpdateRequest);
        userRepository.save(existingUser);
        return new ApiResponse<>(UserMapper.toDto(existingUser), true, "SUCCESS", null, LocalDateTime.now());
    }

    @Override
    public ApiResponse<UserResponse> updateStatus(Long id) throws ResourceConflictException, ResourceNotFoundException {
        Users users = userRepository.findByUserIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        users.setActive(!users.isActive());
        userRepository.save(users);
        return new ApiResponse<>(UserMapper.toDto(users), true, "SUCCESS", null, LocalDateTime.now());
    }

    @Override
    public ApiResponse<UserResponse> updateRole(Long id, String role) throws ResourceConflictException {
        return null;
    }

    @Override
    public ApiResponse<String> deleteProfile(Long id) throws ResourceConflictException {
        return null;
    }
}
