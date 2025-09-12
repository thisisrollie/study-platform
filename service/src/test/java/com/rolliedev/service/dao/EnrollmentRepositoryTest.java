package com.rolliedev.service.dao;

import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Enrollment;
import com.rolliedev.service.entity.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EnrollmentRepositoryTest extends AbstractDaoTest {

    private final EnrollmentRepository enrollmentRepository = new EnrollmentRepository(entityManager);

    @Test
    void findAllByStudentId() {
        // given
        Student student = entityManager.createQuery("select s from Student s " +
                                                    "where s.email = :email", Student.class)
                .setParameter("email", "lanalang@gmail.com")
                .getSingleResult();
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
        Course course = entityManager.createQuery("select c from Course c " +
                                                  "where c.title = :title", Course.class)
                .setParameter("title", "Java Basics")
                .getSingleResult();
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