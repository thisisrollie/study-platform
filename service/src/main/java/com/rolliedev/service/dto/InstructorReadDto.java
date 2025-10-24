package com.rolliedev.service.dto;

import com.rolliedev.service.entity.enums.Role;

public record InstructorReadDto(Long id,
                                String firstName,
                                String lastName,
                                String email,
                                Role role) {
}
