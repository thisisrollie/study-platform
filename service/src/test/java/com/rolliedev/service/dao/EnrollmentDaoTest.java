package com.rolliedev.service.dao;

import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Enrollment;
import com.rolliedev.service.entity.Student;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EnrollmentDaoTest extends AbstractDaoTest {

    private final EnrollmentDao enrollmentDao = EnrollmentDao.getInstance();

    @Test
    void findAllByStudent() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Student student = session.createSelectionQuery("select s from Student s " +
                                                       "where s.email = :email", Student.class)
                .setParameter("email", "lanalang@gmail.com")
                .uniqueResultOptional()
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Enrollment> results = enrollmentDao.findAllByStudent(session, student.getId());

        assertThat(results).hasSize(3);
        List<String> titles = results.stream()
                .map(enrollment -> enrollment.getCourse().getTitle())
                .toList();
        assertThat(titles).containsExactlyInAnyOrder("Python", "Introduction To Databases", "Advanced English Vocabulary");

        session.getTransaction().commit();
    }

    @Test
    void findAllByCourse() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Course course = session.createSelectionQuery("select c from Course c " +
                                                     "where c.title = :title", Course.class)
                .setParameter("title", "Java Basics")
                .uniqueResultOptional()
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<Enrollment> results = enrollmentDao.findAllByCourse(session, course.getId());

        assertThat(results).hasSize(2);
        List<String> fullNames = results.stream()
                .map(Enrollment::getStudent)
                .map(this::getFullName)
                .toList();
        assertThat(fullNames).containsExactlyInAnyOrder("Phillip Malone", "Clark Kent");

        session.getTransaction().commit();
    }

    private String getFullName(Student student) {
        return student.getFirstName() + " " + student.getLastName();
    }
}