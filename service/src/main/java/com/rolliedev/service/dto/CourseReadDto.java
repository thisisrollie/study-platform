package com.rolliedev.service.dto;

public record CourseReadDto(Long id,
                            String title,
                            String description,
                            InstructorReadDto instructor) {
}
