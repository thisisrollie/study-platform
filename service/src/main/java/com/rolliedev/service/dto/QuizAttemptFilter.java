package com.rolliedev.service.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class QuizAttemptFilter {
    String studentEmail;
    LocalDate from;
    LocalDate to;
}
