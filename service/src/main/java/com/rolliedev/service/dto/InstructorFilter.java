package com.rolliedev.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InstructorFilter {
    Long id;
    String firstName;
    String lastName;
    String email;
}
