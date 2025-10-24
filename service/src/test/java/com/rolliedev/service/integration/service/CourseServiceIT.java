package com.rolliedev.service.integration.service;

import com.rolliedev.service.repository.CourseRepository;
import com.rolliedev.service.repository.InstructorRepository;
import com.rolliedev.service.dto.CourseCreateDto;
import com.rolliedev.service.dto.CourseFilter;
import com.rolliedev.service.dto.CourseReadDto;
import com.rolliedev.service.dto.CourseUpdateDto;
import com.rolliedev.service.dto.InstructorFilter;
import com.rolliedev.service.entity.Course;
import com.rolliedev.service.mapper.CourseCreateMapper;
import com.rolliedev.service.mapper.CourseReadMapper;
import com.rolliedev.service.mapper.CourseUpdateMapper;
import com.rolliedev.service.mapper.InstructorReadMapper;
import com.rolliedev.service.service.CourseService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseServiceIT extends AbstractServiceIT {

    private static final String EMPTY = "";

    private CourseRepository courseRepository;
    private CourseService courseService;

    @BeforeEach
    void init() {
        courseRepository = new CourseRepository(entityManager);
        InstructorRepository instructorRepository = new InstructorRepository(entityManager);

        InstructorReadMapper instructorReadMapper = new InstructorReadMapper();
        CourseReadMapper courseReadMapper = new CourseReadMapper(instructorReadMapper);
        CourseCreateMapper courseCreateMapper = new CourseCreateMapper(instructorRepository);
        CourseUpdateMapper courseUpdateMapper = new CourseUpdateMapper(instructorRepository);

        courseService = new CourseService(courseRepository, courseReadMapper, courseCreateMapper, courseUpdateMapper, validator);
    }

    @Test
    void shouldFindAllByInstructor() {
        CourseFilter courseFilter = CourseFilter.builder()
                .instructor(InstructorFilter.builder()
                        .email("jaradyagin@gmail.com")
                        .build())
                .build();

        List<CourseReadDto> results = courseService.findAllByInstructor(courseFilter);

        assertThat(results).hasSize(2);
        List<String> courseTitles = results.stream()
                .map(CourseReadDto::title)
                .toList();
        assertThat(courseTitles).containsExactlyInAnyOrder("Master English Grammar", "Advanced English Vocabulary");
    }

    @Test
    void shouldThrowExceptionIfInstructorEmailIsNotValid() {
        CourseFilter courseFilter = CourseFilter.builder()
                .instructor(InstructorFilter.builder()
                        .email("dummy_email")
                        .build())
                .build();

        assertThrows(ConstraintViolationException.class, () -> courseService.findAllByInstructor(courseFilter));
    }

    @Test
    void shouldFindAllByTitle() {
        List<CourseReadDto> results = courseService.findAllByTitle("english");

        assertThat(results).hasSize(2);
        List<String> titles = results.stream()
                .map(CourseReadDto::title)
                .toList();
        assertThat(titles).containsExactlyInAnyOrder("Master English Grammar", "Advanced English Vocabulary");
    }

    @Test
    void shouldFindAllByStudentEmail() {
        List<CourseReadDto> results = courseService.findAllByStudentEmail("clarkkent@gmail.com");

        assertThat(results).hasSize(2);
        List<String> titles = results.stream()
                .map(CourseReadDto::title)
                .toList();
        assertThat(titles).containsExactlyInAnyOrder("Java Basics", "Introduction To Databases");
    }

    @Test
    void shouldFindAll() {
        List<CourseReadDto> results = courseService.findAll();

        assertThat(results).hasSize(5);
    }

    @Test
    void shouldUpdateExistingEntity() {
        Course course = courseRepository.findById(1L)
                .orElseThrow();
        CourseUpdateDto expectedCourse = new CourseUpdateDto(course.getId(), course.getTitle() + "123", "changed description", course.getId());

        courseService.update(expectedCourse);
        entityManager.flush();
        entityManager.clear();

        Course actualCourse = courseRepository.findById(course.getId()).get();
        assertEquals(expectedCourse.title(), actualCourse.getTitle());
        assertEquals(expectedCourse.description(), actualCourse.getDescription());
    }

    @Test
    void updateShouldThrowExceptionIfDtoIsInvalid() {
        Course course = courseRepository.findById(1L)
                .orElseThrow();
        CourseUpdateDto invalidDto = new CourseUpdateDto(course.getId(), EMPTY, null, course.getId());

        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> courseService.update(invalidDto));
        assertThat(exception.getConstraintViolations()).hasSize(2);
        List<String> exceptionMessages = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        assertThat(exceptionMessages).containsExactlyInAnyOrder("Title cannot be null or blank", "Description cannot be null or blank");
    }

    @Test
    void createShouldThrowExceptionWhenDtoIsInvalid() {
        CourseCreateDto invalidDto = new CourseCreateDto(EMPTY, "some description", 1L);

        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> courseService.create(invalidDto));
        assertThat(exception.getConstraintViolations()).hasSize(1);
        List<String> exceptionMessages = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        assertThat(exceptionMessages).containsExactlyInAnyOrder("Title cannot be null or blank");
    }

    @Test
    void shouldDeleteExistingEntity() {
        Course courseToDelete = courseRepository.findById(1L)
                .orElseThrow();

        boolean actualResult = courseService.delete(courseToDelete.getId());

        assertTrue(actualResult);
    }

    @Test
    void shouldReturnFalseIfEntityDoesNotExist() {
        boolean actualResult = courseService.delete(99L);

        assertFalse(actualResult);
    }
}