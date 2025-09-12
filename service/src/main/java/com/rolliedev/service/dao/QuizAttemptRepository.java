package com.rolliedev.service.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.dto.QuizAttemptFilter;
import com.rolliedev.service.entity.QuizAttempt;
import jakarta.persistence.EntityManager;

import java.time.ZoneOffset;
import java.util.List;

import static com.rolliedev.service.entity.QQuiz.quiz;
import static com.rolliedev.service.entity.QQuizAttempt.quizAttempt;
import static com.rolliedev.service.entity.QStudent.student;

public class QuizAttemptRepository extends RepositoryBase<Long, QuizAttempt> {

    public QuizAttemptRepository(EntityManager entityManager) {
        super(QuizAttempt.class, entityManager);
    }

    public List<QuizAttempt> findAllByQuizAndStudent(QuizAttemptFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getQuizId(), quiz.id::eq)
                .add(filter.getStudentId(), student.id::eq)
                .add(filter.getStudentEmail(), student.email::eq)
                .add(filter.getFrom(), fromDate -> quizAttempt.attemptedAt.goe(fromDate.atStartOfDay().toInstant(ZoneOffset.UTC)))
                .add(filter.getTo(), toDate -> quizAttempt.attemptedAt.loe(toDate.atStartOfDay().toInstant(ZoneOffset.UTC)))
                .buildAnd();

        var jpaQuery = new JPAQuery<QuizAttempt>(getEntityManager())
                .select(quizAttempt)
                .from(quizAttempt);
        if (filter.getStudentEmail() != null) {
            jpaQuery = jpaQuery.join(quizAttempt.student, student);
        }
        return jpaQuery.where(predicate)
                .fetch();
    }
}
