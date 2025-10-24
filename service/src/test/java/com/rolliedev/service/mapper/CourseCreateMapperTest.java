package com.rolliedev.service.mapper;

import com.rolliedev.service.repository.InstructorRepository;
import com.rolliedev.service.dto.CourseCreateDto;
import com.rolliedev.service.entity.Course;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static util.TestObjectUtils.JAVA_BASICS_COURSE;
import static util.TestObjectUtils.LEILA_INSTRUCTOR;

@ExtendWith(MockitoExtension.class)
class CourseCreateMapperTest {

    @Mock
    private InstructorRepository instructorRepository;
    @InjectMocks
    private CourseCreateMapper courseCreateMapper;

    @Test
    void shouldMapAllFieldsCorrectly() {
        CourseCreateDto createDto = new CourseCreateDto(JAVA_BASICS_COURSE.getTitle(), JAVA_BASICS_COURSE.getDescription(), JAVA_BASICS_COURSE.getInstructor().getId());
        doReturn(Optional.of(LEILA_INSTRUCTOR)).when(instructorRepository).findById(JAVA_BASICS_COURSE.getInstructor().getId());

        Course actualResult = courseCreateMapper.mapFrom(createDto);

        assertEquals(JAVA_BASICS_COURSE.getTitle(), actualResult.getTitle());
        assertEquals(JAVA_BASICS_COURSE.getDescription(), actualResult.getDescription());
        assertEquals(JAVA_BASICS_COURSE.getInstructor().getId(), actualResult.getInstructor().getId());
        verify(instructorRepository).findById(JAVA_BASICS_COURSE.getInstructor().getId());
    }

    @Test
    void shouldThrowExceptionWhenInstructorDoesNotExist() {
        CourseCreateDto createDto = new CourseCreateDto(JAVA_BASICS_COURSE.getTitle(), JAVA_BASICS_COURSE.getDescription(), JAVA_BASICS_COURSE.getInstructor().getId());
        doReturn(Optional.empty()).when(instructorRepository).findById(JAVA_BASICS_COURSE.getInstructor().getId());

        assertThrows(IllegalArgumentException.class, () -> courseCreateMapper.mapFrom(createDto));
    }
}