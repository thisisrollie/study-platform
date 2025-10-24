package com.rolliedev.service.integration.entity;

import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Instructor;
import com.rolliedev.service.entity.Question;
import com.rolliedev.service.entity.Quiz;
import com.rolliedev.service.entity.enums.Role;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionIT extends AbstractEntityIT {

    @Test
    void shouldPersistQuestion() {
        // given
        Instructor instructor = buildInstructor();
        session.persist(instructor);

        Course course = Course.builder()
                .title("Java Basics")
                .instructor(instructor)
                .build();
        session.persist(course);

        Quiz quiz = Quiz.builder()
                .title("Primitive data types quiz")
                .course(course)
                .build();
        session.persist(quiz);

        Question question = Question.builder()
                .text("How many primitive data types in Java?")
                .options(List.of("eight", "seven", "nine", "ten"))
                .answer("eight")
                .quiz(quiz)
                .build();

        // when
        session.persist(question);
        flushAndClear();

        // then
        Question fromDb = session.find(Question.class, question.getId());
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getQuiz().getTitle()).isEqualTo("Primitive data types quiz");
        assertThat(fromDb.getOptions()).hasSize(4);
        assertThat(fromDb.getOptions()).contains("eight", "seven", "nine", "ten");
    }

    private Instructor buildInstructor() {
        return Instructor.builder()
                .firstName("Petr")
                .lastName("Petrov")
                .email("petrov@gmail.com")
                .password("123")
                .role(Role.INSTRUCTOR)
                .build();
    }
}
