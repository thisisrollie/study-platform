package com.rolliedev.service.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.entity.Quiz;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

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

    public Optional<Quiz> findByCourseAndTitle(Long courseId, String title) {
        return Optional.ofNullable(new JPAQuery<Quiz>(getEntityManager())
                .select(quiz)
                .from(quiz)
                .where(quiz.course.id.eq(courseId)
                        .and(quiz.title.eq(title)))
                .fetchOne());
    }
}
