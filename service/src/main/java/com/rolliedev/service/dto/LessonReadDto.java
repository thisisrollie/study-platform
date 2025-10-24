package com.rolliedev.service.dto;

public record LessonReadDto(Long id,
                            String title,
                            String description,
                            Integer order,
                            Long courseId) {
}
