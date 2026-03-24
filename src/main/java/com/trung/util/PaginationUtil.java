package com.trung.util;

import com.trung.dto.request.PageRequestDTO;
import com.trung.dto.response.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Function;


public class PaginationUtil {

    public static Pageable createPageRequest(PageRequestDTO pageRequestDTO) {

        List<String> allowedSortFields = List.of("userId", "username", "email", "fullName", "role", "createdAt", "updatedAt"); // Danh sách các trường được phép sort

        int page = pageRequestDTO.getPage() != null && pageRequestDTO.getPage() > 0 ? pageRequestDTO.getPage() : 0;
        int size = pageRequestDTO.getSize() != null && pageRequestDTO.getSize() > 0 ? pageRequestDTO.getSize() : 10;
        String sortBy = pageRequestDTO.getSortBy() != null && allowedSortFields.contains(pageRequestDTO.getSortBy())
                ? pageRequestDTO.getSortBy()
                : "userId";

        Sort.Direction direction;
        try {
            direction = pageRequestDTO.getSortDirection() == null || pageRequestDTO.getSortDirection().isBlank()
                    ? Sort.Direction.ASC
                    : Sort.Direction.fromString(pageRequestDTO.getSortDirection());
        } catch (Exception e) {
            direction = Sort.Direction.ASC; // Tránh user sort ko theo asc hoặc desc
        }

        return PageRequest.of(page, size, Sort.by(direction, sortBy));
    }

    public static <T, R> PageResponseDTO<R> toPageResponseDTO(Page<T> page, Function<T, R> mapper) {
        List<R> content = page.getContent().stream()
                .map(mapper)
                .toList();
        return new PageResponseDTO<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                content,
                page.hasNext(),
                page.hasPrevious()
        );
    }
}
