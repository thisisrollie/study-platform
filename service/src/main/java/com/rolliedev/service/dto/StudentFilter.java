package com.rolliedev.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StudentFilter {
    String firstName;
    String lastName;
    Long courseId;
    String courseTitle;
}
