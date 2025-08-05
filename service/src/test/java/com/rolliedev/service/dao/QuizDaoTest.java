package com.rolliedev.service.dao;

import com.rolliedev.service.dto.QuizFilter;
import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Quiz;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuizDaoTest extends AbstractDaoTest {

    private final QuizDao quizDao = QuizDao.getInstance();

    @Test
    void findAllByCourse() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Course course = session.createSelectionQuery("select c from Course c " +
                                                     "where c.title = :title", Course.class)
                .setParameter("title", "Introduction To Databases")
                .uniqueResultOptional()
                .orElseThrow(() -> new RuntimeException("Course not found"));
        QuizFilter filter = QuizFilter.builder()
                .courseId(course.getId())
                .build();

        List<Quiz> results = quizDao.findAllByCourse(session, filter);

        assertThat(results).hasSize(4);
        List<String> titles = results.stream().map(Quiz::getTitle).toList();
        assertThat(titles).containsExactlyInAnyOrder("Database Fundamentals", "Tables and Keys", "Constraints and Data Integrity", "Combining Data: Union and Joins");

        session.getTransaction().commit();
    }
}