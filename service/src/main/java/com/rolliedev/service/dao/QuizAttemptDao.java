package com.rolliedev.service.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.dto.QuizAttemptFilter;
import com.rolliedev.service.entity.QuizAttempt;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static com.rolliedev.service.entity.QQuizAttempt.quizAttempt;
import static com.rolliedev.service.entity.QStudent.student;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuizAttemptDao {

    private static final QuizAttemptDao INSTANCE = new QuizAttemptDao();

    public static QuizAttemptDao getInstance() {
        return INSTANCE;
    }

    public List<QuizAttempt> findAll(Session session) {
        return session.createSelectionQuery("select q from QuizAttempt q", QuizAttempt.class)
                .list();
    }

    public Optional<QuizAttempt> findById(Session session, Long id) {
        return Optional.ofNullable(
                session.find(QuizAttempt.class, id)
        );
    }

    public List<QuizAttempt> findAllByStudent(Session session, QuizAttemptFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getStudentEmail(), student.email::eq)
                .add(filter.getFrom(), fromDate -> quizAttempt.attemptedAt.goe(fromDate.atStartOfDay().toInstant(ZoneOffset.UTC)))
                .add(filter.getTo(), toDate -> quizAttempt.attemptedAt.loe(toDate.atStartOfDay().toInstant(ZoneOffset.UTC)))
                .buildAnd();

        return new JPAQuery<QuizAttempt>(session)
                .select(quizAttempt)
                .from(quizAttempt)
                .join(quizAttempt.student, student)
                .where(predicate)
                .fetch();
    }
}
