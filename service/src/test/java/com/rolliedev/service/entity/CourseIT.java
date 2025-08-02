package com.rolliedev.service.entity;

import com.rolliedev.service.entity.enums.Role;
import com.rolliedev.service.integration.AbstractEntityIT;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseIT extends AbstractEntityIT {

    @Test
    void shouldPersistCourse() {
        Instructor instructor = buildInstructor();
        Course course = Course.builder()
                .title("Java Basics")
                .build();
        instructor.addCourse(course);

        session.persist(instructor);
        flushAndClear();

        Course dbCourse = session.find(Course.class, course.getId());
        assertThat(dbCourse).isNotNull();
        assertThat(dbCourse.getTitle()).isEqualTo("Java Basics");
    }

    @Test
    void shouldUpdateCourseTitle() {
        Instructor instructor = buildInstructor();
        Course course = Course.builder()
                .title("Java Basics")
                .build();
        instructor.addCourse(course);
        session.persist(instructor);
        flushAndClear();

        Course toUpdate = session.find(Course.class, course.getId());
        toUpdate.setTitle("Java Basics Course");
        flushAndClear();

        Course updatedCourse = session.find(Course.class, toUpdate.getId());
        assertThat(updatedCourse.getTitle()).isEqualTo("Java Basics Course");
    }

    @Test
    void shouldDeleteCourse() {
        Instructor instructor = buildInstructor();
        Course course = Course.builder()
                .title("Java Basics")
                .build();
        instructor.addCourse(course);
        session.persist(instructor);
        flushAndClear();

        Course toRemove = session.find(Course.class, course.getId());
        session.remove(toRemove);
        flushAndClear();

        Course removedCourse = session.find(Course.class, course.getId());
        assertThat(removedCourse).isNull();
    }

    @Test
    void lessonsWithinACourseShouldBeSorted() {
        // given
        Instructor instructor = buildInstructor();
        session.persist(instructor);

        Course course = Course.builder()
                .title("Java Basics")
                .instructor(instructor)
                .build();
        session.persist(course);

        List<Lesson> lessons = buildLessons(course, 10);
        Collections.shuffle(lessons);
        lessons.forEach(session::persist);
        flushAndClear();

        // when
        var fromDb = session.find(Course.class, course.getId());

        // then
        assertThat(fromDb.getLessons()).hasSize(10);
        assertThat(fromDb.getLessons()).isSorted();
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

    private List<Lesson> buildLessons(Course course, int numberOfLessons) {
        return IntStream.range(1, numberOfLessons + 1)
                .mapToObj(i -> Lesson.builder()
                        .title("Lesson-" + i)
                        .order(i)
                        .course(course)
                        .build())
                .collect(Collectors.toList());
    }
}
