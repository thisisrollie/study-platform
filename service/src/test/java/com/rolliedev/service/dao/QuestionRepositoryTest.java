package com.rolliedev.service.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Question;
import com.rolliedev.service.entity.Quiz;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.rolliedev.service.entity.QCourse.course;
import static com.rolliedev.service.entity.QQuiz.quiz;
import static org.assertj.core.api.Assertions.assertThat;

class QuestionRepositoryTest extends AbstractDaoTest {

    private final QuestionRepository questionRepository = new QuestionRepository(entityManager);

    @Test
    void findAllByQuizId() {
        // given
        Course dbCourse = findCourseByTitle("Introduction To Databases");
        assertThat(dbCourse).isNotNull();

        Quiz dbQuiz = findQuizByCourseAndTitle(dbCourse, "Database Fundamentals");
        assertThat(dbQuiz).isNotNull();
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

    private Course findCourseByTitle(String title) {
        return new JPAQuery<Course>(entityManager)
                .select(course)
                .from(course)
                .where(course.title.eq(title))
                .fetchOne();
    }

    private Quiz findQuizByCourseAndTitle(Course course, String quizTitle) {
        return new JPAQuery<Quiz>(entityManager)
                .select(quiz)
                .from(quiz)
                .where(
                        quiz.course.id.eq(course.getId()),
                        quiz.title.eq(quizTitle)
                )
                .fetchOne();
    }
}