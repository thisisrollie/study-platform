package com.rolliedev.service.dao;

import com.rolliedev.service.dto.QuizAttemptFilter;
import com.rolliedev.service.entity.QuizAttempt;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.assertj.core.api.Assertions.assertThat;

class QuizAttemptRepositoryTest extends AbstractDaoTest {

    private final QuizAttemptRepository quizAttemptRepository = new QuizAttemptRepository(entityManager);

    @Test
    void findAllByQuizAndStudent() {
        // given
        QuizAttemptFilter filter = QuizAttemptFilter.builder()
                .quizId(9L)
                .studentId(4L)
//                .studentEmail("lanalang@gmail.com")
                .from(LocalDate.of(2025, Month.APRIL, 6))
                .to(LocalDate.of(2025, Month.APRIL, 11))
                .build();
        // when
        List<QuizAttempt> results = quizAttemptRepository.findAllByQuizAndStudent(filter);
        // then
        assertThat(results).hasSize(2);
        Map<String, Long> attemptCountByQuizTitle = results.stream()
                .map(quizAttempt -> quizAttempt.getQuiz().getTitle())
                .collect(groupingBy(identity(), counting()));
        assertThat(attemptCountByQuizTitle).containsKeys("Tables and Keys");
        assertThat(attemptCountByQuizTitle.get("Tables and Keys")).isEqualTo(2);
    }
}