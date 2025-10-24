package com.rolliedev.service.mapper;

import com.rolliedev.service.repository.InstructorRepository;
import com.rolliedev.service.dto.CourseCreateDto;
import com.rolliedev.service.entity.Course;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CourseCreateMapper implements Mapper<CourseCreateDto, Course> {

    private final InstructorRepository instructorRepository;

    @Override
    public Course mapFrom(CourseCreateDto object) {
        return Course.builder()
                .title(object.title())
                .description(object.description())
                .instructor(instructorRepository.findById(object.instructorId()).orElseThrow(IllegalArgumentException::new))
                .build();
    }
}
