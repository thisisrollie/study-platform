package com.rolliedev.service.integration.repository;

import com.rolliedev.service.integration.TestDataHelper;
import com.rolliedev.service.repository.QuestionRepository;
import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Question;
import com.rolliedev.service.entity.Quiz;
import com.rolliedev.service.integration.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionRepositoryIT extends IntegrationTestBase {

    private QuestionRepository questionRepository;

    @BeforeEach
    void init() {
        questionRepository = new QuestionRepository(entityManager);
    }

    @Test
    void findAllByQuizId() {
        // given
        TestDataHelper dataHelper = new TestDataHelper(entityManager);
        Course dbCourse = dataHelper.findCourseByTitle("Introduction To Databases");
        Quiz dbQuiz = dataHelper.findQuizByTitleAndCourse("Database Fundamentals", dbCourse);
        // when
        List<Question> results = questionRepository.findAllByQuizId(dbQuiz.getId());
        // then
        assertThat(results).hasSize(3);
        List<String> questionTexts = results.stream().map(Question::getText).toList();
        assertThat(questionTexts).containsExactlyInAnyOrder(
                "What is a database?",
                "What is the difference between a database and a spreadsheet?",
                "What is a DBMS?"
        );
    }
}