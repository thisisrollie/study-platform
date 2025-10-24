package com.rolliedev.service.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InstructorFilter {
    Long id;
    String firstName;
    String lastName;
    @Email(regexp = "[a-zA-Z]\\w*@\\w{3,}\\.[a-z]{2,3}")
    String email;
}
