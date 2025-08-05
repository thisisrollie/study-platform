package com.rolliedev.service.dao;

import com.rolliedev.service.dto.LessonFilter;
import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Lesson;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LessonDaoTest extends AbstractDaoTest {

    private final LessonDao lessonDao = LessonDao.getInstance();

    @Test
    void findAllByFilter() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Course course = session.createSelectionQuery("select c from Course c " +
                                                     "where c.title = :title", Course.class)
                .setParameter("title", "Java Basics")
                .uniqueResultOptional()
                .orElseThrow(() -> new RuntimeException("Course not found"));
        LessonFilter filter = LessonFilter.builder()
                .courseId(course.getId())
                .build();

        List<Lesson> results = lessonDao.findAllByFilter(session, filter);

        assertThat(results).hasSize(7);
        List<String> titles = results.stream().map(Lesson::getTitle).toList();
        assertThat(titles).containsExactlyInAnyOrder(
                "OOP. Introduction", "Classes and objects", "Constructors", "Method overloading",
                "Access modifiers", "Stack and Heap", "String class"
        );

        session.getTransaction().commit();
    }
}