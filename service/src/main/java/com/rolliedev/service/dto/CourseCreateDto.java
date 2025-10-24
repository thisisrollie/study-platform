package com.rolliedev.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseCreateDto(
        @NotBlank(message = "Title cannot be null or blank")
        String title,
        @NotBlank(message = "Description cannot be null or blank")
        @Size(max = 255, message = "Description should be up to 255 characters")
        String description,
        @NotNull
        Long instructorId) {
}
