package com.rolliedev.service.integration.entity;

import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Instructor;
import com.rolliedev.service.entity.Lesson;
import com.rolliedev.service.entity.LessonMaterial;
import com.rolliedev.service.entity.enums.LessonMaterialType;
import com.rolliedev.service.entity.enums.Role;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class LessonIT extends AbstractEntityIT {

    @Test
    void shouldPersistLesson() {
        // given
        Instructor instructor = buildInstructor();
        session.persist(instructor);

        Course course = Course.builder()
                .title("Java Basics")
                .instructor(instructor)
                .build();
        session.persist(course);

        Lesson lesson = Lesson.builder()
                .title("Primitive data types")
                .order(1)
                .course(course)
                .build();

        // when
        session.persist(lesson);
        flushAndClear();

        // then
        Lesson fromDb = session.find(Lesson.class, lesson.getId());
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getCourse().getTitle()).isEqualTo("Java Basics");
        assertThat(fromDb.getTitle()).isEqualTo("Primitive data types");
    }

    @Test
    void shouldUpdateLesson() {
        // given
        Instructor instructor = buildInstructor();
        session.persist(instructor);

        Course course = Course.builder()
                .title("Java Basics")
                .instructor(instructor)
                .build();
        session.persist(course);

        Lesson lesson = Lesson.builder()
                .title("Primitive data types")
                .order(1)
                .course(course)
                .build();
        session.persist(lesson);
        flushAndClear();

        // when
        Lesson toUpdate = session.find(Lesson.class, lesson.getId());
        toUpdate.setOrder(2);
        flushAndClear();

        // then
        Lesson updatedLesson = session.find(Lesson.class, toUpdate.getId());
        assertThat(updatedLesson.getOrder()).isEqualTo(2);
    }

    @Test
    void shouldDeleteLesson() {
        // given
        Instructor instructor = buildInstructor();
        session.persist(instructor);

        Course course = Course.builder()
                .title("Java Basics")
                .instructor(instructor)
                .build();
        session.persist(course);

        Lesson lesson = Lesson.builder()
                .title("Primitive data types")
                .order(1)
                .course(course)
                .build();
        session.persist(lesson);
        flushAndClear();

        // when
        Lesson toRemove = session.find(Lesson.class, lesson.getId());
        session.remove(toRemove);
        flushAndClear();

        // then
        Lesson removedLesson = session.find(Lesson.class, lesson.getId());
        assertThat(removedLesson).isNull();
    }

    @Test
    void lessonMaterialsWithinALessonShouldBeSorted() {
        // given
        Instructor instructor = buildInstructor();
        session.persist(instructor);

        Course course = Course.builder()
                .title("Java Basics")
                .instructor(instructor)
                .build();
        session.persist(course);

        Lesson lesson = Lesson.builder()
                .title("Primitive data types")
                .order(1)
                .course(course)
                .build();
        session.persist(lesson);

        List<LessonMaterial> lessonMaterials = buildLessonMaterials(lesson, 7);
        Collections.shuffle(lessonMaterials);
        lessonMaterials.forEach(session::persist);
        flushAndClear();

        // when
        Lesson fromDb = session.find(Lesson.class, lesson.getId());

        // then
        assertThat(fromDb.getLessonMaterials()).hasSize(7);
        assertThat(fromDb.getLessonMaterials()).isSorted();
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

    private List<LessonMaterial> buildLessonMaterials(Lesson lesson, int numberOfMaterials) {
        return IntStream.range(1, numberOfMaterials + 1)
                .mapToObj(i -> LessonMaterial.builder()
                        .contentUrl("url-" + i)
                        .type(LessonMaterialType.IMAGE)
                        .order(i)
                        .lesson(lesson)
                        .build())
                .collect(Collectors.toList());
    }
}
