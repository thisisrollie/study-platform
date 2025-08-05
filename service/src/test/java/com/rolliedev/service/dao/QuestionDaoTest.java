package com.rolliedev.service.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.dto.QuestionFilter;
import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Question;
import com.rolliedev.service.entity.Quiz;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.rolliedev.service.entity.QCourse.course;
import static com.rolliedev.service.entity.QQuiz.quiz;
import static org.assertj.core.api.Assertions.assertThat;

class QuestionDaoTest extends AbstractDaoTest {

    private final QuestionDao questionDao = QuestionDao.getInstance();

    @Test
    void findAllByQuiz() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Course dbCourse = findCourseByTitle(session, "Introduction To Databases");
        assertThat(dbCourse).isNotNull();

        Quiz dbQuiz = findQuizByCourseAndTitle(session, dbCourse, "Database Fundamentals");
        assertThat(dbQuiz).isNotNull();

        QuestionFilter filter = QuestionFilter.builder()
                .quizId(dbQuiz.getId())
                .build();

        List<Question> results = questionDao.findAllByQuiz(session, filter);

        assertThat(results).hasSize(3);
        List<String> questionTexts = results.stream().map(Question::getText).toList();
        assertThat(questionTexts).containsExactlyInAnyOrder(
                "What is a database?",
                "What is the difference between a database and a spreadsheet?",
                "What is a DBMS?"
        );

        session.getTransaction().commit();
    }

    private Course findCourseByTitle(Session session, String title) {
        return new JPAQuery<Course>(session)
                .select(course)
                .from(course)
                .where(course.title.eq(title))
                .fetchOne();
    }

    private Quiz findQuizByCourseAndTitle(Session session, Course course, String quizTitle) {
        return new JPAQuery<Quiz>(session)
                .select(quiz)
                .from(quiz)
                .where(
                        quiz.course.id.eq(course.getId()),
                        quiz.title.eq(quizTitle)
                )
                .fetchOne();
    }
}