package com.rolliedev.service.dto;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CourseFilter {
    @Builder.Default
    @Valid
    InstructorFilter instructor = InstructorFilter.builder().build();
}
