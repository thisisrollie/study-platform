package com.rolliedev.service.entity;

import com.rolliedev.service.entity.enums.LessonMaterialType;
import com.rolliedev.service.entity.enums.Role;
import com.rolliedev.service.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HibernateEntityPersistenceTest {

    @Test
    void shouldPersistAndRetrieveUserById() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = buildUser();
            session.persist(user);
            session.flush();
            session.clear();

            User userFromDb = session.find(User.class, user.getId());

            assertThat(userFromDb).isNotNull();
            assertThat(userFromDb.getEmail()).isEqualTo("ivanov@gmail.com");

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldPersistAndRetrieveCourseWithInstructor() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = buildUser();
            session.persist(user);
            Course course = buildCourse(user);
            session.persist(course);
            session.flush();
            session.clear();

            Course courseFromDb = session.find(Course.class, course.getId());

            assertThat(courseFromDb).isNotNull();
            assertThat(courseFromDb.getTitle()).isEqualTo("Java Basics");
            assertThat(courseFromDb.getInstructorId()).isEqualTo(user.getId());

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldPersistAndRetrieveLessonForCourse() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = buildUser();
            session.persist(user);
            Course course = buildCourse(user);
            session.persist(course);

            Lesson lesson = Lesson.builder()
                    .title("Primitive data types")
                    .order(1)
                    .courseId(course.getId())
                    .build();
            session.persist(lesson);
            session.flush();
            session.clear();

            Lesson lessonFromDb = session.find(Lesson.class, lesson.getId());

            assertThat(lessonFromDb).isNotNull();
            assertThat(lessonFromDb.getTitle()).isEqualTo("Primitive data types");
            assertThat(lessonFromDb.getCourseId()).isEqualTo(course.getId());

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldPersistAndRetrieveLessonMaterialForLesson() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = buildUser();
            session.persist(user);
            Course course = buildCourse(user);
            session.persist(course);

            Lesson lesson = Lesson.builder()
                    .title("Primitive data types")
                    .order(1)
                    .courseId(course.getId())
                    .build();
            session.persist(lesson);

            LessonMaterial lessonMaterial = LessonMaterial.builder()
                    .contentUrl("fake_url")
                    .type(LessonMaterialType.IMAGE)
                    .order(1)
                    .lessonId(lesson.getId())
                    .build();
            session.persist(lessonMaterial);

            session.flush();
            session.clear();

            LessonMaterial lessonMaterialFromDb = session.find(LessonMaterial.class, lessonMaterial.getId());

            assertThat(lessonMaterialFromDb).isNotNull();
            assertThat(lessonMaterialFromDb.getOrder()).isEqualTo(1);
            assertThat(lessonMaterialFromDb.getType()).isEqualTo(LessonMaterialType.IMAGE);
            assertThat(lessonMaterialFromDb.getLessonId()).isEqualTo(lesson.getId());

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldPersistAndRetrieveQuestionForQuiz() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = buildUser();
            session.persist(user);
            Course course = buildCourse(user);
            session.persist(course);

            Quiz quiz = Quiz.builder()
                    .title("Primitive data types quiz")
                    .courseId(course.getId())
                    .build();
            session.persist(quiz);

            Question question = Question.builder()
                    .text("How many primitive data types are there in Java?")
                    .options(List.of("seven", "eight", "nine", "six"))
                    .answer("eight")
                    .quizId(quiz.getId())
                    .build();
            session.persist(question);

            session.flush();
            session.clear();

            Question questionFromDb = session.find(Question.class, question.getId());

            assertThat(questionFromDb).isNotNull();
            assertThat(questionFromDb.getText()).isEqualTo("How many primitive data types are there in Java?");
            assertThat(questionFromDb.getOptions()).hasSize(4);
            assertThat(questionFromDb.getAnswer()).isEqualTo("eight");

            session.getTransaction().rollback();
        }
    }

    private User buildUser() {
        return User.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .email("ivanov@gmail.com")
                .password("123")
                .role(Role.INSTRUCTOR)
                .build();
    }

    private Course buildCourse(User instructor) {
        return Course.builder()
                .title("Java Basics")
                .instructorId(instructor.getId())
                .build();
    }
}
