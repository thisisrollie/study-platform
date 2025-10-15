package com.rolliedev.service.dao;

import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Quiz;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuizRepositoryTest extends AbstractDaoTest {

    private final QuizRepository quizRepository = new QuizRepository(entityManager);

    @Test
    void findAllByCourseId() {
        // given
        Course course = entityManager.createQuery("select c from Course c " +
                                                  "where c.title = :title", Course.class)
                .setParameter("title", "Introduction To Databases")
                .getSingleResult();
        // when
        List<Quiz> results = quizRepository.findAllByCourseId(course.getId());
        // then
        assertThat(results).hasSize(4);
        List<String> titles = results.stream().map(Quiz::getTitle).toList();
        assertThat(titles).containsExactlyInAnyOrder("Database Fundamentals", "Tables and Keys", "Constraints and Data Integrity", "Combining Data: Union and Joins");
    }
}