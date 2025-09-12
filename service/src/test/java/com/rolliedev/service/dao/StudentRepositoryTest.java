package com.rolliedev.service.dao;

import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StudentRepositoryTest extends AbstractDaoTest {

    private final StudentRepository studentRepository = new StudentRepository(entityManager);

    @Test
    void findAllByCourseId() {
        // given
        Course course = entityManager.find(Course.class, 1L);
        assertNotNull(course);
        // when
        List<Student> results = studentRepository.findAllByCourseId(course.getId());
        // then
        assertThat(results).hasSize(2);
        List<String> studentFullNames = results.stream()
                .map(Student::getFullName)
                .toList();
        assertThat(studentFullNames).containsExactlyInAnyOrder("Phillip Malone", "Clark Kent");
    }
}