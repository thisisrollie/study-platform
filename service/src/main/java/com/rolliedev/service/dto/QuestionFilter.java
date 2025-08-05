package com.rolliedev.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QuestionFilter {
    Long quizId;
    String quizTitle;
}
