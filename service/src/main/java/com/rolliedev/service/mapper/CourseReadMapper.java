package com.rolliedev.service.mapper;

import com.rolliedev.service.dto.CourseReadDto;
import com.rolliedev.service.entity.Course;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CourseReadMapper implements Mapper<Course, CourseReadDto> {

    private final InstructorReadMapper instructorReadMapper;

    @Override
    public CourseReadDto mapFrom(Course object) {
        return new CourseReadDto(
                object.getId(),
                object.getTitle(),
                object.getDescription(),
                instructorReadMapper.mapFrom(object.getInstructor())
        );
    }
}
