package com.rolliedev.service.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.entity.Question;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.rolliedev.service.entity.QQuestion.question;

public class QuestionRepository extends RepositoryBase<Long, Question> {

    public QuestionRepository(EntityManager entityManager) {
        super(Question.class, entityManager);
    }

    public List<Question> findAllByQuizId(Long quizId) {
        return new JPAQuery<Question>(getEntityManager())
                .select(question)
                .from(question)
                .where(question.quiz.id.eq(quizId))
                .fetch();
    }
}
