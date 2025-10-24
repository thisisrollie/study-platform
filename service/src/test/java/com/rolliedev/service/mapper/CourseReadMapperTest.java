package com.rolliedev.service.mapper;

import com.rolliedev.service.dto.CourseReadDto;
import com.rolliedev.service.dto.InstructorReadDto;
import com.rolliedev.service.entity.Instructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static util.TestObjectUtils.JAVA_BASICS_COURSE;

@ExtendWith(MockitoExtension.class)
class CourseReadMapperTest {

    @Mock
    private InstructorReadMapper instructorReadMapper;
    @InjectMocks
    private CourseReadMapper courseReadMapper;

    @Test
    void shouldMapAllFieldsCorrectly() {
        Instructor instructor = JAVA_BASICS_COURSE.getInstructor();
        InstructorReadDto instructorReadDto = new InstructorReadDto(instructor.getId(), instructor.getFirstName(), instructor.getLastName(), instructor.getEmail(), instructor.getRole());
        doReturn(instructorReadDto).when(instructorReadMapper).mapFrom(instructor);

        CourseReadDto actualResult = courseReadMapper.mapFrom(JAVA_BASICS_COURSE);

        assertEquals(JAVA_BASICS_COURSE.getId(), actualResult.id());
        assertEquals(JAVA_BASICS_COURSE.getTitle(), actualResult.title());
        assertEquals(JAVA_BASICS_COURSE.getDescription(), actualResult.description());
        assertEquals(instructorReadDto, actualResult.instructor());
        verify(instructorReadMapper).mapFrom(instructor);
    }
}