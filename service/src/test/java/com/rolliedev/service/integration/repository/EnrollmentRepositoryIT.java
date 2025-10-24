package com.rolliedev.service.integration.repository;

import com.rolliedev.service.integration.TestDataHelper;
import com.rolliedev.service.repository.EnrollmentRepository;
import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Enrollment;
import com.rolliedev.service.entity.Student;
import com.rolliedev.service.integration.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EnrollmentRepositoryIT extends IntegrationTestBase {

    private EnrollmentRepository enrollmentRepository;
    private TestDataHelper dataHelper;

    @BeforeEach
    void init() {
        enrollmentRepository = new EnrollmentRepository(entityManager);
        dataHelper = new TestDataHelper(entityManager);
    }

    @Test
    void findAllByStudentId() {
        // given
        Student student = dataHelper.findStudentByEmail("lanalang@gmail.com");
        // when
        List<Enrollment> results = enrollmentRepository.findAllByStudentId(student.getId());
        // then
        assertThat(results).hasSize(3);
        List<String> titles = results.stream()
                .map(enrollment -> enrollment.getCourse().getTitle())
                .toList();
        assertThat(titles).containsExactlyInAnyOrder("Python", "Introduction To Databases", "Advanced English Vocabulary");
    }

    @Test
    void findAllByCourseId() {
        // given
        Course course = dataHelper.findCourseByTitle("Java Basics");
        // when
        List<Enrollment> results = enrollmentRepository.findAllByCourseId(course.getId());
        // then
        assertThat(results).hasSize(2);
        List<String> fullNames = results.stream()
                .map(Enrollment::getStudent)
                .map(Student::getFullName)
                .toList();
        assertThat(fullNames).containsExactlyInAnyOrder("Phillip Malone", "Clark Kent");
    }
}