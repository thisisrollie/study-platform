package com.rolliedev.service.integration;

import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Instructor;
import com.rolliedev.service.entity.Quiz;
import com.rolliedev.service.entity.Student;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class TestDataHelper {

    private final EntityManager entityManager;

    public Quiz findQuizByTitleAndCourse(String title, Course course) {
        return entityManager.createQuery("select q from Quiz q " +
                                         "where q.title = :title and q.course.id = :courseId", Quiz.class)
                .setParameter("title", title)
                .setParameter("courseId", course.getId())
                .getSingleResult();
    }

    public Course findCourseById(Long id) {
        return Optional.ofNullable(entityManager.find(Course.class, id))
                .orElseThrow(() -> new RuntimeException(String.format("Course with id=%d was not found", id)));
    }

    public Instructor findInstructorById(Long id) {
        return Optional.ofNullable(entityManager.find(Instructor.class, id))
                .orElseThrow(() -> new RuntimeException(String.format("Instructor with id=%d was not found", id)));
    }

    public Course findCourseByTitle(String title) {
        return entityManager.createQuery("select c from Course c " +
                                         "where c.title = :title", Course.class)
                .setParameter("title", title)
                .getSingleResult();
    }

    public Student findStudentByEmail(String email) {
        return entityManager.createQuery("select s from Student s " +
                                         "where s.email = :email", Student.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
