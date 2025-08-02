package com.rolliedev.service.entity;

import com.rolliedev.service.entity.enums.Role;
import com.rolliedev.service.integration.AbstractEntityIT;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnrollmentIT extends AbstractEntityIT {

    @Test
    void shouldPersistEnrollment() {
        // given
        Instructor instructor = buildInstructor();
        session.persist(instructor);

        Course course = Course.builder()
                .title("Java Basics")
                .instructor(instructor)
                .build();
        session.persist(course);

        Student student = buildStudent();
        session.persist(student);

        Enrollment enrollment = Enrollment.builder().build();
        enrollment.setCourse(course);
        enrollment.setStudent(student);

        // when
        session.persist(enrollment);
        flushAndClear();

        // then
        Enrollment fromDb = session.find(Enrollment.class, enrollment.getId());
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getCourse().getTitle()).isEqualTo("Java Basics");
        assertThat(fromDb.getStudent().getEmail()).isEqualTo("ivanov@gmail.com");
    }

    @Test
    void shouldDeleteEnrollment() {
        // given
        Student student = buildStudent();
        session.persist(student);

        Instructor instructor = buildInstructor();
        session.persist(instructor);

        Course course = Course.builder()
                .title("Java Basics")
                .instructor(instructor)
                .build();
        session.persist(course);

        Enrollment enrollment = Enrollment.builder().build();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        session.persist(enrollment);
        flushAndClear();

        // when
        Enrollment toRemove = session.find(Enrollment.class, enrollment.getId());
        session.remove(toRemove);
        flushAndClear();

        // then
        Enrollment removedEnrollment = session.find(Enrollment.class, enrollment.getId());
        assertThat(removedEnrollment).isNull();
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

    private Student buildStudent() {
        return Student.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .email("ivanov@gmail.com")
                .password("abc")
                .role(Role.STUDENT)
                .build();
    }
}
