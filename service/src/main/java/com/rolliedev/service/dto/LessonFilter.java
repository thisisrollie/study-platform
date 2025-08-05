package com.rolliedev.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LessonFilter {
    String title;
    Long courseId;
    String courseTitle;
    Long instructorId;
}
