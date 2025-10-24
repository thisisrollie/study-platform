package com.rolliedev.service.integration.repository;

import com.rolliedev.service.integration.TestDataHelper;
import com.rolliedev.service.repository.StudentRepository;
import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Student;
import com.rolliedev.service.integration.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudentRepositoryIT extends IntegrationTestBase {

    private StudentRepository studentRepository;

    @BeforeEach
    void init() {
        studentRepository = new StudentRepository(entityManager);
    }

    @Test
    void findAllByCourseId() {
        // given
        TestDataHelper dataHelper = new TestDataHelper(entityManager);
        Course course = dataHelper.findCourseById(1L);
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