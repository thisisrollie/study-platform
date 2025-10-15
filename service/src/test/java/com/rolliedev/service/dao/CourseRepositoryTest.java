package com.rolliedev.service.dao;

import com.rolliedev.service.dto.CourseFilter;
import com.rolliedev.service.dto.InstructorFilter;
import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Instructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseRepositoryTest extends AbstractDaoTest {

    private final CourseRepository courseRepository = new CourseRepository(entityManager);

    @Test
    void save() {
        // given
        Instructor instructor = Optional.ofNullable(entityManager.find(Instructor.class, 1L))
                .orElseThrow(() -> new RuntimeException("Instructor with id=1 was not found"));
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
    void delete() {
        // given
        Course courseToDelete = Optional.ofNullable(entityManager.find(Course.class, 1L))
                .orElseThrow(() -> new RuntimeException("Course with id=1 was not found"));
        // when
        courseRepository.delete(courseToDelete.getId());
        // then
        Course actualResult = entityManager.find(Course.class, courseToDelete.getId());
        assertThat(actualResult).isNull();
    }

    @Test
    void update() {
        // given
        Course courseToUpdate = Optional.ofNullable(entityManager.find(Course.class, 1L))
                .orElseThrow(() -> new RuntimeException("Course with id=1 was not found"));
        // when
        courseToUpdate.setTitle("abc");
        courseRepository.update(courseToUpdate);
        entityManager.flush();
        entityManager.clear();
        // then
        Course actualResult = entityManager.find(Course.class, courseToUpdate.getId());
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getTitle()).isEqualTo("abc");
    }

    @Test
    void findAll() {
        // when
        List<Course> results = courseRepository.findAll();
        // then
        assertThat(results).hasSize(5);
        List<String> titles = results.stream().map(Course::getTitle).toList();
        assertThat(titles).containsExactlyInAnyOrder("Java Basics", "Python", "Introduction To Databases", "Master English Grammar", "Advanced English Vocabulary");
    }

    @Test
    void findAllByInstructor() {
        // given
        CourseFilter filter = CourseFilter.builder()
                .instructor(InstructorFilter.builder()
                        .firstName("Leila")
                        .lastName("Valone")
                        .build())
                .build();
        // when
        List<Course> results = courseRepository.findAllByInstructor(filter);
        // then
        assertThat(results).hasSize(3);
        List<String> titles = results.stream().map(Course::getTitle).toList();
        assertThat(titles).containsExactlyInAnyOrder("Java Basics", "Python", "Introduction To Databases");
    }

    @Test
    void findAllByTitle() {
        // when
        List<Course> results = courseRepository.findAllByTitle("english");
        // then
        assertThat(results).hasSize(2);
        List<String> titles = results.stream().map(Course::getTitle).toList();
        assertThat(titles).containsExactlyInAnyOrder("Master English Grammar", "Advanced English Vocabulary");
    }

    @Test
    void findAllInWhichStudentEnrolled() {
        // when
        List<Course> results = courseRepository.findAllInWhichStudentEnrolled("lanalang@gmail.com");
        // then
        assertThat(results).hasSize(3);
        List<String> titles = results.stream().map(Course::getTitle).toList();
        assertThat(titles).containsExactlyInAnyOrder("Python", "Introduction To Databases", "Advanced English Vocabulary");
    }
}
