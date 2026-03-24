# 📚 Hướng Dẫn Sử Dụng Custom Validator

## 📋 Tổng Quan

Dự án hiện đã có các custom validator để xử lý validation một cách tự động thay vì sử dụng `HashMap` trong service layer.

---

## ✅ Các Validator Đã Tạo

### 1️⃣ **@UniquePhaseName**
- **Mục đích**: Kiểm tra tên internship phase không bị trùng lặp
- **Nơi sử dụng**: `InternshipPhaseCreateRequest.phaseName`
- **Thông báo lỗi**: "Internship phase already exists"

**Ví dụ:**
```java
@UniquePhaseName
private String phaseName;
```

---

### 2️⃣ **@ValidDateRange**
- **Mục đích**: Kiểm tra start date < end date
- **Nơi sử dụng**: Class level (trên DTO)
- **Thông báo lỗi**: "Start date must be before end date"

**Ví dụ:**
```java
@ValidDateRange(startDateField = "startDate", endDateField = "endDate")
public class InternshipPhaseCreateRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}
```

---

### 3️⃣ **@UniqueUsername**
- **Mục đích**: Kiểm tra username không bị trùng lặp
- **Nơi sử dụng**: `UserCreateRequest.username`, `FormRegisterRequest.username`
- **Thông báo lỗi**: "Username already exists"

**Ví dụ:**
```java
@UniqueUsername
@NotBlank(message = "Username is required")
private String username;
```

---

### 4️⃣ **@UniqueEmail**
- **Mục đích**: Kiểm tra email không bị trùng lặp
- **Nơi sử dụng**: `UserCreateRequest.email`, `FormRegisterRequest.email`
- **Thông báo lỗi**: "Email already exists"

**Ví dụ:**
```java
@UniqueEmail
@Email(message = "Email is invalid")
private String email;
```

---

## 🔧 Cách Tạo Custom Validator Mới

### Bước 1: Tạo Annotation
```java
package com.trung.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = YourCustomValidator.class)
@Target(ElementType.FIELD)  // hoặc ElementType.TYPE nếu validate class
@Retention(RetentionPolicy.RUNTIME)
public @interface YourCustomValidator {
    String message() default "Default error message";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```

### Bước 2: Tạo Validator Implementation
```java
package com.trung.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class YourCustomValidator implements ConstraintValidator<YourCustomValidator, String> {
    // Inject repository nếu cần
    private final YourRepository repository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        
        // Logic kiểm tra
        boolean isValid = repository.existsByField(value);
        
        if (!isValid) {
            return true;
        }
        
        // Tùy chỉnh thông báo lỗi
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Your custom error message")
               .addConstraintViolation();
        return false;
    }
}
```

### Bước 3: Sử dụng trong DTO
```java
public class YourDTO {
    @YourCustomValidator
    private String field;
}
```

---

## 🎯 Lợi Ích

✅ **Clean Code**: Loại bỏ `Map<String, String> = new HashMap()` lặp lại  
✅ **Tái sử dụng**: Dùng validator ở nhiều DTO mà không lặp code  
✅ **Dễ bảo trì**: Validation logic tập trung ở một nơi  
✅ **Tự động hóa**: Spring Boot tự động gọi validator  
✅ **Lỗi nhất quán**: Thông báo lỗi luôn nhất quán  

---

## 📊 So Sánh Trước/Sau

### ❌ Cách Cũ (Lặp lại trong nhiều service)
```java
@Override
public ApiResponse<T> createUser(UserCreateRequest request) {
    Map<String, String> errorList = new HashMap<>();
    
    if (userRepository.existsByUsername(request.getUsername())) {
        errorList.put("username", "Username already exists");
    }
    if (userRepository.existsByEmail(request.getEmail())) {
        errorList.put("email", "Email already exists");
    }
    if (!errorList.isEmpty()) {
        throw new ResourceConflictException("CONFLICT", errorList);
    }
    
    // ... logic tạo user
}
```

### ✅ Cách Mới (Sạch và tái sử dụng)
```java
@Override
public ApiResponse<T> createUser(UserCreateRequest request) {
    // Validation tự động được Spring xử lý
    
    // ... logic tạo user
}

// Trong DTO:
public class UserCreateRequest {
    @UniqueUsername
    private String username;
    
    @UniqueEmail
    private String email;
}
```

---

## 🚀 Các File Đã Được Cập Nhật

| File | Thay đổi |
|------|---------|
| `InternshipPhaseCreateRequest.java` | Thêm @UniquePhaseName, @ValidDateRange |
| `UserCreateRequest.java` | Thêm @UniqueUsername, @UniqueEmail |
| `FormRegisterRequest.java` | Thêm @UniqueUsername, @UniqueEmail |
| `InternshipPhaseServiceImpl.java` | Loại bỏ HashMap validation |
| `AuthServiceImpl.java` | Loại bỏ HashMap validation |
| `UserServiceImpl.java` | Loại bỏ HashMap validation |
| `StudentServiceImpl.java` | Tối ưu HashMap validation |

---

## 💡 Gợi Ý Tiếp Theo

1. **Tạo validator cho Student**: `@UniqueStudentCode`
2. **Tạo validator cho Mentor**: `@UniqueMentorCode`
3. **Tạo validator custom**: `@ValidPhoneNumber` (kiểm tra format phone)
4. **Tạo global error handler**: Để format lỗi validation nhất quán

---

## 📞 Liên Hệ

Nếu có câu hỏi hoặc cần hỗ trợ thêm, vui lòng tham khảo Spring Validation Documentation:
- https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#validation


