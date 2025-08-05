package com.rolliedev.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CourseFilter {
    String title;
    InstructorFilter instructor;
}
