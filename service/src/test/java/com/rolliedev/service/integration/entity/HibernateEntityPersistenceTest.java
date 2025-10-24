package com.rolliedev.service.integration.entity;

import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Instructor;
import com.rolliedev.service.entity.Lesson;
import com.rolliedev.service.entity.LessonMaterial;
import com.rolliedev.service.entity.Question;
import com.rolliedev.service.entity.Quiz;
import com.rolliedev.service.entity.User;
import com.rolliedev.service.entity.enums.LessonMaterialType;
import com.rolliedev.service.entity.enums.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import util.HibernateTestUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HibernateEntityPersistenceTest {

    @Test
    void shouldPersistAndRetrieveUserById() {
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Instructor instructor = buildInstructor();
            session.persist(instructor);
            session.flush();
            session.clear();

            User userFromDb = session.find(Instructor.class, instructor.getId());

            assertThat(userFromDb).isNotNull();
            assertThat(userFromDb.getEmail()).isEqualTo("ivanov@gmail.com");

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldPersistAndRetrieveCourseWithInstructor() {
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Instructor instructor = buildInstructor();
            session.persist(instructor);
            Course course = buildCourse();
            instructor.addCourse(course);
            session.persist(course);
            session.flush();
            session.clear();

            Course courseFromDb = session.find(Course.class, course.getId());

            assertThat(courseFromDb).isNotNull();
            assertThat(courseFromDb.getTitle()).isEqualTo("Java Basics");
            assertThat(courseFromDb.getInstructor().getId()).isEqualTo(instructor.getId());

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldPersistAndRetrieveLessonForCourse() {
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Instructor instructor = buildInstructor();
            session.persist(instructor);
            Course course = buildCourse();
            instructor.addCourse(course);
            session.persist(course);

            Lesson lesson = Lesson.builder()
                    .title("Primitive data types")
                    .order(1)
                    .build();
            course.addLesson(lesson);
            session.persist(lesson);
            session.flush();
            session.clear();

            Lesson lessonFromDb = session.find(Lesson.class, lesson.getId());

            assertThat(lessonFromDb).isNotNull();
            assertThat(lessonFromDb.getTitle()).isEqualTo("Primitive data types");
            assertThat(lessonFromDb.getCourse().getId()).isEqualTo(course.getId());

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldPersistAndRetrieveLessonMaterialForLesson() {
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Instructor instructor = buildInstructor();
            session.persist(instructor);
            Course course = buildCourse();
            instructor.addCourse(course);
            session.persist(course);

            Lesson lesson = Lesson.builder()
                    .title("Primitive data types")
                    .order(1)
                    .build();
            course.addLesson(lesson);
            session.persist(lesson);

            LessonMaterial lessonMaterial = LessonMaterial.builder()
                    .contentUrl("fake_url")
                    .type(LessonMaterialType.IMAGE)
                    .order(1)
                    .build();
            lesson.addLessonMaterial(lessonMaterial);
            session.persist(lessonMaterial);

            session.flush();
            session.clear();

            LessonMaterial lessonMaterialFromDb = session.find(LessonMaterial.class, lessonMaterial.getId());

            assertThat(lessonMaterialFromDb).isNotNull();
            assertThat(lessonMaterialFromDb.getOrder()).isEqualTo(1);
            assertThat(lessonMaterialFromDb.getType()).isEqualTo(LessonMaterialType.IMAGE);
            assertThat(lessonMaterialFromDb.getLesson().getId()).isEqualTo(lesson.getId());

            session.getTransaction().rollback();
        }
    }

    @Test
    void shouldPersistAndRetrieveQuestionForQuiz() {
        try (SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Instructor instructor = buildInstructor();
            session.persist(instructor);
            Course course = buildCourse();
            instructor.addCourse(course);
            session.persist(course);

            Quiz quiz = Quiz.builder()
                    .title("Primitive data types quiz")
                    .build();
            course.addQuiz(quiz);
            session.persist(quiz);

            Question question = Question.builder()
                    .text("How many primitive data types are there in Java?")
                    .options(List.of("seven", "eight", "nine", "six"))
                    .answer("eight")
                    .build();
            quiz.addQuestion(question);
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

    private Instructor buildInstructor() {
        return Instructor.builder()
                .firstName("Ivan")
                .lastName("Ivanov")
                .email("ivanov@gmail.com")
                .password("123")
                .role(Role.INSTRUCTOR)
                .build();
    }

    private Course buildCourse() {
        return Course.builder()
                .title("Java Basics")
                .build();
    }
}
