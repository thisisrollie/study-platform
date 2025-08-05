package com.rolliedev.service.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.dto.QuizFilter;
import com.rolliedev.service.entity.Quiz;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

import static com.rolliedev.service.entity.QCourse.course;
import static com.rolliedev.service.entity.QQuiz.quiz;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuizDao {

    private static final QuizDao INSTANCE = new QuizDao();

    public static QuizDao getInstance() {
        return INSTANCE;
    }

    public List<Quiz> findAllByCourse(Session session, QuizFilter filter) {
        Predicate predicate = QPredicate.builder()
                .add(filter.getCourseId(), course.id::eq)
                .add(filter.getCourseTitle(), course.title::eq)
                .buildAnd();

        return new JPAQuery<Quiz>(session)
                .select(quiz)
                .from(quiz)
                .join(quiz.course, course)
                .where(predicate)
                .fetch();
    }
}
