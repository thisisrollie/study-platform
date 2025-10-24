package com.rolliedev.service.mapper;

import com.rolliedev.service.repository.InstructorRepository;
import com.rolliedev.service.dto.CourseUpdateDto;
import com.rolliedev.service.entity.Course;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CourseUpdateMapper implements Mapper<CourseUpdateDto, Course> {

    private final InstructorRepository instructorRepository;

    @Override
    public Course mapFrom(CourseUpdateDto object) {
        return Course.builder()
                .id(object.id())
                .title(object.title())
                .description(object.description())
                .instructor(instructorRepository.findById(object.instructorId()).orElseThrow(IllegalArgumentException::new))
                .build();
    }
}
