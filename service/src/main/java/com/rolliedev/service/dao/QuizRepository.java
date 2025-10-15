package com.rolliedev.service.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.entity.Quiz;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.rolliedev.service.entity.QQuiz.quiz;

public class QuizRepository extends RepositoryBase<Long, Quiz> {

    public QuizRepository(EntityManager entityManager) {
        super(Quiz.class, entityManager);
    }

    public List<Quiz> findAllByCourseId(Long courseId) {
        return new JPAQuery<Quiz>(getEntityManager())
                .select(quiz)
                .from(quiz)
                .where(quiz.course.id.eq(courseId))
                .fetch();
    }
}
