package com.rolliedev.service.mapper;

import com.rolliedev.service.dto.InstructorReadDto;
import com.rolliedev.service.entity.Instructor;

public class InstructorReadMapper implements Mapper<Instructor, InstructorReadDto> {

    @Override
    public InstructorReadDto mapFrom(Instructor object) {
        return new InstructorReadDto(
                object.getId(),
                object.getFirstName(),
                object.getLastName(),
                object.getEmail(),
                object.getRole()
        );
    }
}
