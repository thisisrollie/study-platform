package com.rolliedev.service.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.dto.QuestionFilter;
import com.rolliedev.service.entity.Question;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

import static com.rolliedev.service.entity.QQuestion.question;
import static com.rolliedev.service.entity.QQuiz.quiz;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionDao {

    private static final QuestionDao INSTANCE = new QuestionDao();

    public static QuestionDao getInstance() {
        return INSTANCE;
    }

    public List<Question> findAllByQuiz(Session session, QuestionFilter filter) {
        Predicate predicate = QPredicate.builder()
                .add(filter.getQuizId(), quiz.id::eq)
                .add(filter.getQuizTitle(), quiz.title::eq)
                .buildAnd();

        return new JPAQuery<Question>(session)
                .select(question)
                .from(question)
                .join(question.quiz, quiz)
                .where(predicate)
                .fetch();
    }
}
