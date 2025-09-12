package com.rolliedev.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CourseFilter {
    @Builder.Default
    InstructorFilter instructor = InstructorFilter.builder().build();
}
