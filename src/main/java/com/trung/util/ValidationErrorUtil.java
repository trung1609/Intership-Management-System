package com.trung.util;

import com.trung.exception.InvalidDateFormatException;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ValidationErrorUtil {

    private ValidationErrorUtil() {

    }

    public static Map<String, String> createErrorMap() {
        return new HashMap<>();
    }


    public static boolean hasErrors(Map<String, String> errorMap) {
        return errorMap != null && !errorMap.isEmpty();
    }

    public static void addError(Map<String, String> errorMap, String key, String message) {
        if (errorMap != null) {
            errorMap.put(key, message);
        }
    }

    public static LocalDate isValidDate(String date, String dateFormat) throws InvalidDateFormatException {
        try {
            DateTimeFormatter sdf = DateTimeFormatter.ofPattern(dateFormat);
            return LocalDate.parse(date, sdf);
        }catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Date must be in format " + dateFormat);
        }
    }
}

