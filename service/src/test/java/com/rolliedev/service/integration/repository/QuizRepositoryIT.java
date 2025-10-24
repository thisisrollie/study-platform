package com.rolliedev.service.integration.repository;

import com.rolliedev.service.integration.TestDataHelper;
import com.rolliedev.service.repository.QuizRepository;
import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Quiz;
import com.rolliedev.service.integration.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuizRepositoryIT extends IntegrationTestBase {

    private QuizRepository quizRepository;

    @BeforeEach
    void init() {
        quizRepository = new QuizRepository(entityManager);
    }

    @Test
    void findAllByCourseId() {
        // given
        TestDataHelper dataHelper = new TestDataHelper(entityManager);
        Course course = dataHelper.findCourseByTitle("Introduction To Databases");
        // when
        List<Quiz> results = quizRepository.findAllByCourseId(course.getId());
        // then
        assertThat(results).hasSize(4);
        List<String> titles = results.stream().map(Quiz::getTitle).toList();
        assertThat(titles).containsExactlyInAnyOrder("Database Fundamentals", "Tables and Keys", "Constraints and Data Integrity", "Combining Data: Union and Joins");
    }
}