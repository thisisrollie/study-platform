package com.rolliedev.service.service;

import com.rolliedev.service.repository.CourseRepository;
import com.rolliedev.service.dto.CourseCreateDto;
import com.rolliedev.service.dto.CourseFilter;
import com.rolliedev.service.dto.CourseReadDto;
import com.rolliedev.service.dto.CourseUpdateDto;
import com.rolliedev.service.dto.InstructorFilter;
import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Instructor;
import com.rolliedev.service.mapper.CourseCreateMapper;
import com.rolliedev.service.mapper.CourseReadMapper;
import com.rolliedev.service.mapper.CourseUpdateMapper;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.hibernate.graph.GraphSemantic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static util.TestObjectUtils.INTRO_TO_DB_COURSE;
import static util.TestObjectUtils.JAVA_BASICS_COURSE;
import static util.TestObjectUtils.LEILA_INSTRUCTOR;
import static util.TestObjectUtils.PYTHON_COURSE;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private CourseReadMapper courseReadMapper;
    @Mock
    private CourseCreateMapper courseCreateMapper;
    @Mock
    private CourseUpdateMapper courseUpdateMapper;
    @Mock
    private Validator validator;
    @InjectMocks
    private CourseService courseService;

    @Test
    void shouldValidateAndConvertFoundEntitiesByInstructor() {
        CourseFilter courseFilter = CourseFilter.builder()
                .instructor(InstructorFilter.builder()
                        .id(LEILA_INSTRUCTOR.getId())
                        .build())
                .build();
        List<Course> courses = List.of(JAVA_BASICS_COURSE, PYTHON_COURSE, INTRO_TO_DB_COURSE);
        doReturn(Set.of()).when(validator).validate(courseFilter);
        doReturn(courses).when(courseRepository).findAllByInstructor(courseFilter);
        courses.forEach(course -> {
            CourseReadDto courseReadDto = new CourseReadDto(course.getId(), course.getTitle(), course.getDescription(), null);
            doReturn(courseReadDto).when(courseReadMapper).mapFrom(course);
        });

        List<CourseReadDto> actualResult = courseService.findAllByInstructor(courseFilter);

        assertThat(actualResult).hasSize(courses.size());
        verify(validator).validate(courseFilter);
        verify(courseRepository).findAllByInstructor(courseFilter);
        verify(courseReadMapper, times(courses.size())).mapFrom(any(Course.class));
    }

    @Test
    void shouldCallRepositoryAndConvertFoundEntitiesByTitle() {
        List<Course> courses = List.of(JAVA_BASICS_COURSE);
        doReturn(courses).when(courseRepository).findAllByTitle("java");
        courses.forEach(course -> {
            CourseReadDto courseDto = new CourseReadDto(course.getId(), course.getTitle(), course.getTitle(), null);
            doReturn(courseDto).when(courseReadMapper).mapFrom(course);
        });

        List<CourseReadDto> actualResult = courseService.findAllByTitle("java");

        assertThat(actualResult).hasSize(1);
        verify(courseRepository).findAllByTitle("java");
        verify(courseReadMapper, times(courses.size())).mapFrom(any(Course.class));
    }

    @Test
    void shouldNotFoundAnyCoursesByTitle() {
        doReturn(Collections.emptyList()).when(courseRepository).findAllByTitle("Ruby");

        List<CourseReadDto> actualResult = courseService.findAllByTitle("Ruby");

        assertThat(actualResult).isEmpty();
        verify(courseRepository).findAllByTitle("Ruby");
        verifyNoInteractions(courseReadMapper);
    }

    @Test
    void shouldCallRepositoryAndConvertFoundEntitiesByStudentEmail() {
        List<Course> courses = List.of(
                JAVA_BASICS_COURSE,
                PYTHON_COURSE
        );
        doReturn(courses).when(courseRepository).findAllByStudentEmail("ivanov@gmail.com");
        courses.forEach(course -> {
            CourseReadDto courseDto = new CourseReadDto(course.getId(), course.getTitle(), course.getTitle(), null);
            doReturn(courseDto).when(courseReadMapper).mapFrom(course);
        });

        List<CourseReadDto> actualResult = courseService.findAllByStudentEmail("ivanov@gmail.com");

        assertThat(actualResult).hasSize(courses.size());
        List<String> titles = actualResult.stream()
                .map(CourseReadDto::title)
                .toList();
        assertThat(titles).containsExactlyInAnyOrder(JAVA_BASICS_COURSE.getTitle(), PYTHON_COURSE.getTitle());
        verify(courseRepository).findAllByStudentEmail("ivanov@gmail.com");
        verify(courseReadMapper, times(courses.size())).mapFrom(any());
    }

    @Test
    void shouldConvertAllFoundEntities() {
        List<Course> listOfEntities = List.of(
                JAVA_BASICS_COURSE,
                PYTHON_COURSE,
                INTRO_TO_DB_COURSE
        );
        doReturn(listOfEntities).when(courseRepository).findAll();
        listOfEntities.forEach(entity -> {
            CourseReadDto courseDto = new CourseReadDto(entity.getId(), entity.getTitle(), entity.getDescription(), null);
            doReturn(courseDto).when(courseReadMapper).mapFrom(entity);
        });

        List<CourseReadDto> actualResult = courseService.findAll();

        assertThat(actualResult).hasSize(listOfEntities.size());
        verify(courseRepository).findAll();
        verify(courseReadMapper, times(listOfEntities.size())).mapFrom(any());
    }

    @Test
    void shouldValidateInputAndConvertUpdatedEntity() {
        CourseUpdateDto courseDto = new CourseUpdateDto(1L, "Java Basics", "changed description", 1L);
        Course course = Course.builder()
                .id(courseDto.id())
                .title(courseDto.title())
                .description(courseDto.description())
                .instructor(Instructor.builder()
                        .id(courseDto.instructorId())
                        .build())
                .build();
        doReturn(Set.of()).when(validator).validate(courseDto);
        doReturn(course).when(courseUpdateMapper).mapFrom(courseDto);

        courseService.update(courseDto);

        verify(validator).validate(courseDto);
        verify(courseUpdateMapper).mapFrom(courseDto);
        verify(courseRepository).update(course);
    }

    @Test
    void shouldValidateInputAndConvertSavedEntity() {
        CourseCreateDto courseDto = new CourseCreateDto("Java Basics", "some description", 1L);
        Course courseToSave = Course.builder().build();
        doReturn(Set.of()).when(validator).validate(courseDto);
        doReturn(courseToSave).when(courseCreateMapper).mapFrom(courseDto);
        doReturn(JAVA_BASICS_COURSE).when(courseRepository).save(courseToSave);

        Long actualResult = courseService.create(courseDto);

        assertSame(JAVA_BASICS_COURSE.getId(), actualResult);
        verify(validator).validate(courseDto);
        verify(courseCreateMapper).mapFrom(courseDto);
        verify(courseRepository).save(courseToSave);
    }

    @Test
    void shouldThrowExceptionIfDtoIsInvalid() {
        CourseCreateDto invalidCourseDto = new CourseCreateDto(null, "some description", 1L);
        ConstraintViolation<?> titleConstraintViolation = Mockito.mock(ConstraintViolation.class);
        doReturn("Title cannot be null or blank").when(titleConstraintViolation).getMessage();
        doReturn(Set.of(titleConstraintViolation)).when(validator).validate(invalidCourseDto);

        ConstraintViolationException actualResult = assertThrows(ConstraintViolationException.class, () -> courseService.create(invalidCourseDto));

        assertThat(actualResult.getMessage()).contains("Title cannot be null or blank");
        assertThat(actualResult.getConstraintViolations()).hasSize(1);
        verifyNoInteractions(courseRepository, courseCreateMapper);
    }

    @Test
    void shouldCallRepositoryAndConvertEntityOnFindById() {
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        EntityGraph<?> entityGraph = Mockito.mock(EntityGraph.class);
        doReturn(entityManager).when(courseRepository).getEntityManager();
        doReturn(entityGraph).when(entityManager).getEntityGraph(anyString());
        doReturn(Optional.of(JAVA_BASICS_COURSE)).when(courseRepository).findById(eq(JAVA_BASICS_COURSE.getId()), anyMap());
        CourseReadDto expectedCourseDto = new CourseReadDto(JAVA_BASICS_COURSE.getId(), JAVA_BASICS_COURSE.getTitle(), JAVA_BASICS_COURSE.getDescription(), null);
        doReturn(expectedCourseDto).when(courseReadMapper).mapFrom(JAVA_BASICS_COURSE);

        Optional<CourseReadDto> actualResult = courseService.findById(JAVA_BASICS_COURSE.getId());

        assertThat(actualResult).isPresent();
        assertSame(expectedCourseDto, actualResult.get());
        verify(courseRepository).findById(JAVA_BASICS_COURSE.getId(), Map.of(GraphSemantic.LOAD.getJakartaHintName(), entityGraph));
        verify(courseReadMapper).mapFrom(JAVA_BASICS_COURSE);
    }

    @Test
    void deleteIsSuccessful() {
        boolean actualResult = courseService.delete(1L);

        assertTrue(actualResult);
        verify(courseRepository).delete(1L);
    }

    @Test
    void deleteIsFailed() {
        doThrow(NoSuchElementException.class).when(courseRepository).delete(anyLong());

        assertFalse(courseService.delete(1L));
    }
}