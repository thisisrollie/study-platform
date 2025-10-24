package com.rolliedev.service.integration.repository;

import com.rolliedev.service.integration.TestDataHelper;
import com.rolliedev.service.repository.CourseRepository;
import com.rolliedev.service.dto.CourseFilter;
import com.rolliedev.service.dto.InstructorFilter;
import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Instructor;
import com.rolliedev.service.integration.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourseRepositoryIT extends IntegrationTestBase {

    private CourseRepository courseRepository;
    private TestDataHelper dataHelper;

    @BeforeEach
    void init() {
        courseRepository = new CourseRepository(entityManager);
        dataHelper = new TestDataHelper(entityManager);
    }

    @Test
    void shouldSaveCorrectEntity() {
        // given
        Instructor instructor = dataHelper.findInstructorById(1L);
        Course course = Course.builder()
                .title("fake_title")
                .instructor(instructor)
                .build();
        // when
        Course savedEntity = courseRepository.save(course);
        entityManager.clear();
        // then
        Course fromDb = entityManager.find(Course.class, savedEntity.getId());
        assertThat(fromDb).isNotNull();
    }

    @Test
    void shouldDeleteExistingEntity() {
        // given
        Course courseToDelete = dataHelper.findCourseById(1L);
        // when
        courseRepository.delete(courseToDelete.getId());
        // then
        Course actualResult = entityManager.find(Course.class, courseToDelete.getId());
        assertThat(actualResult).isNull();
    }

    @Test
    void shouldThrowExceptionIfDeleteNotExistingEntity() {
        assertThrows(NoSuchElementException.class, () -> courseRepository.delete(999L));
    }

    @Test
    void shouldUpdateExistingEntity() {
        // given
        Course expectedCourse = dataHelper.findCourseById(1L);
        expectedCourse.setTitle("abc");
        expectedCourse.setDescription("changed description");
        // when
        courseRepository.update(expectedCourse);
        entityManager.flush();
        entityManager.clear();
        // then
        Course actualCourse = entityManager.find(Course.class, expectedCourse.getId());
        assertEquals(expectedCourse.getTitle(), actualCourse.getTitle());
        assertEquals(expectedCourse.getDescription(), actualCourse.getDescription());
    }

    @Test
    void shouldFindExistingEntity() {
        Optional<Course> actualCourse = courseRepository.findById(1L);

        assertThat(actualCourse).isPresent();
        assertEquals(1L, actualCourse.get().getId());
    }

    @Test
    void shouldReturnEmptyIfEntityDoesNotExist() {
        Optional<Course> actualCourse = courseRepository.findById(999L);

        assertThat(actualCourse).isEmpty();
    }

    @Test
    void shouldFindAll() {
        // when
        List<Course> results = courseRepository.findAll();
        // then
        assertThat(results).hasSize(5);
        List<String> titles = results.stream()
                .map(Course::getTitle)
                .toList();
        assertThat(titles).containsExactlyInAnyOrder("Java Basics", "Python", "Introduction To Databases", "Master English Grammar", "Advanced English Vocabulary");
    }

    @Test
    void shouldFindAllByInstructor() {
        // given
        CourseFilter filter = CourseFilter.builder()
                .instructor(InstructorFilter.builder()
                        .firstName("Leila")
                        .lastName("Valone")
                        .email("leilavalone@gmail.com")
                        .build())
                .build();
        // when
        List<Course> results = courseRepository.findAllByInstructor(filter);
        // then
        assertThat(results).hasSize(3);
        List<String> titles = results.stream()
                .map(Course::getTitle)
                .toList();
        assertThat(titles).containsExactlyInAnyOrder("Java Basics", "Python", "Introduction To Databases");
    }

    @Test
    void shouldFindAllIfFilterIsEmpty() {
        // given
        CourseFilter filter = CourseFilter.builder()
                .build();
        // when
        List<Course> results = courseRepository.findAllByInstructor(filter);
        // then
        assertThat(results).hasSize(5);
    }

    @Test
    void shouldFindAllByTitleSubstring() {
        // when
        List<Course> results = courseRepository.findAllByTitle("english");
        // then
        assertThat(results).hasSize(2);
        List<String> titles = results.stream()
                .map(Course::getTitle)
                .toList();
        assertThat(titles).containsExactlyInAnyOrder("Master English Grammar", "Advanced English Vocabulary");
    }

    @Test
    void shouldFindAllByStudentEmail() {
        // when
        List<Course> results = courseRepository.findAllByStudentEmail("lanalang@gmail.com");
        // then
        assertThat(results).hasSize(3);
        List<String> titles = results.stream()
                .map(Course::getTitle)
                .toList();
        assertThat(titles).containsExactlyInAnyOrder("Python", "Introduction To Databases", "Advanced English Vocabulary");
    }

    @Test
    void shouldReturnEmptyListIfSuchEmailDoesNotExist() {
        List<Course> results = courseRepository.findAllByStudentEmail("fake_email@gmail.com");

        assertThat(results).isEmpty();
    }
}
