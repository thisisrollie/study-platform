package com.rolliedev.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InstructorFilter {
    String firstName;
    String lastName;
    String email;
}
