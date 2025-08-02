package com.rolliedev.service.entity;

import com.rolliedev.service.entity.enums.Role;
import com.rolliedev.service.integration.AbstractEntityIT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserIT extends AbstractEntityIT {

    @Test
    void shouldPersistUser() {
        User user = Student.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .email("ivanov@gmail.com")
                .password("123")
                .build();

        session.persist(user);
        flushAndClear();

        User dbUser = session.find(User.class, user.getId());
        assertThat(dbUser).isNotNull();
        assertThat(dbUser.getId()).isEqualTo(user.getId());
        assertThat(dbUser.getFirstName()).isEqualTo("Ivan");
    }

    @Test
    void shouldPersistUsersWithProperRole() {
        User admin = Admin.builder()
                .firstName("Petr")
                .lastName("Petrov")
                .email("admin@gmail")
                .password("12345")
                .build();
        User instructor = Instructor.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .email("ivanov@gmail.com")
                .password("123")
                .build();
        User student = Student.builder()
                .firstName("Sveta")
                .lastName("Svetikova")
                .email("sveta@gmail.com")
                .password("123")
                .build();

        session.persist(admin);
        session.persist(instructor);
        session.persist(student);
        flushAndClear();

        User user1 = session.find(User.class, admin.getId());
        User user2 = session.find(User.class, instructor.getId());
        User user3 = session.find(User.class, student.getId());
        Assertions.assertAll(
                () -> assertThat(user1.getRole()).isEqualTo(Role.ADMIN),
                () -> assertThat(user2.getRole()).isEqualTo(Role.INSTRUCTOR),
                () -> assertThat(user3.getRole()).isEqualTo(Role.STUDENT)
        );
    }

    @Test
    void shouldFindUserById() {
        User user = Student.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .email("ivanov@gmail.com")
                .password("123")
                .build();
        session.persist(user);
        flushAndClear();

        User foundUser = session.find(User.class, user.getId());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("ivanov@gmail.com");
    }

    @Test
    void shouldUpdateUserPassword() {
        User user = Student.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .email("ivanov@gmail.com")
                .password("123")
                .build();
        session.persist(user);
        flushAndClear();

        User dbUser = session.find(User.class, user.getId());
        dbUser.setPassword("abc");
        User mergedUser = session.merge(dbUser);
        flushAndClear();

        User updatedUser = session.find(User.class, mergedUser.getId());
        assertThat(updatedUser.getPassword()).isEqualTo("abc");
    }

    @Test
    void shouldDeleteUser() {
        User user = Student.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .email("ivanov@gmail.com")
                .password("123")
                .build();
        session.persist(user);
        flushAndClear();

        User toRemove = session.find(User.class, user.getId());
        session.remove(toRemove);
        flushAndClear();

        User removedUser = session.find(User.class, user.getId());
        assertThat(removedUser).isNull();
    }
}
