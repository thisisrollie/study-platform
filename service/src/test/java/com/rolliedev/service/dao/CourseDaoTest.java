package com.rolliedev.service.dao;

import com.rolliedev.service.dto.CourseFilter;
import com.rolliedev.service.dto.InstructorFilter;
import com.rolliedev.service.entity.Course;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseDaoTest extends AbstractDaoTest {

    private final CourseDao courseDao = CourseDao.getInstance();

    @Test
    void findAll() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Course> results = courseDao.findAll(session);
        assertThat(results).hasSize(5);

        List<String> titles = results.stream().map(Course::getTitle).toList();
        assertThat(titles).containsExactlyInAnyOrder("Java Basics", "Python", "Introduction To Databases", "Master English Grammar", "Advanced English Vocabulary");

        session.getTransaction().commit();
    }

    @Test
    void findAllByInstructor() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        CourseFilter filter = CourseFilter.builder()
                .instructor(InstructorFilter.builder()
                        .firstName("Leila")
                        .lastName("Valone")
                        .build())
                .build();
        List<Course> results = courseDao.findAllByInstructor(session, filter);
        assertThat(results).hasSize(3);

        List<String> titles = results.stream().map(Course::getTitle).toList();
        assertThat(titles).containsExactlyInAnyOrder("Java Basics", "Python", "Introduction To Databases");

        session.getTransaction().commit();
    }

    @Test
    void findAllByTitle() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Course> results = courseDao.findAllByTitle(session, "english");

        assertThat(results).hasSize(2);

        List<String> titles = results.stream().map(Course::getTitle).toList();
        assertThat(titles).containsExactlyInAnyOrder("Master English Grammar", "Advanced English Vocabulary");

        session.getTransaction().commit();
    }

    @Test
    void findAllInWhichStudentEnrolled() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Course> results = courseDao.findAllInWhichStudentEnrolled(session, "lanalang@gmail.com");
        assertThat(results).hasSize(3);

        List<String> titles = results.stream().map(Course::getTitle).toList();
        assertThat(titles).containsExactlyInAnyOrder("Python", "Introduction To Databases", "Advanced English Vocabulary");

        session.getTransaction().commit();
    }
}
