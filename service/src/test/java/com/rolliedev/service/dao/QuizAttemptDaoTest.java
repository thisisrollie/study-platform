package com.rolliedev.service.dao;

import com.rolliedev.service.dto.QuizAttemptFilter;
import com.rolliedev.service.entity.QuizAttempt;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.assertj.core.api.Assertions.assertThat;

class QuizAttemptDaoTest extends AbstractDaoTest {

    private final QuizAttemptDao quizAttemptDao = QuizAttemptDao.getInstance();

    @Test
    void findAll() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<QuizAttempt> results = quizAttemptDao.findAll(session);

        assertThat(results).hasSize(17);

        session.getTransaction().commit();
    }

    @Test
    void findAllByStudent() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        // given
        QuizAttemptFilter filter = QuizAttemptFilter.builder()
                .studentEmail("lanalang@gmail.com")
                .from(LocalDate.of(2025, Month.MARCH, 1))
                .to(LocalDate.of(2025, Month.APRIL, 1))
                .build();

        // when
        List<QuizAttempt> results = quizAttemptDao.findAllByStudent(session, filter);

        // then
        assertThat(results).hasSize(4);
        Map<String, Long> attemptCountByQuizTitle = results.stream()
                .map(quizAttempt -> quizAttempt.getQuiz().getTitle())
                .collect(groupingBy(identity(), counting()));
        assertThat(attemptCountByQuizTitle).containsKeys("Python Basics: Variables and Types", "Working with Strings", "Database Fundamentals");
        assertThat(attemptCountByQuizTitle.get("Working with Strings")).isEqualTo(2);

        session.getTransaction().commit();
    }
}