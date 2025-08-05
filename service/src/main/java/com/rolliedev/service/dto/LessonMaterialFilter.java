package com.rolliedev.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LessonMaterialFilter {
    Long lessonId;
    String lessonTitle;
}
