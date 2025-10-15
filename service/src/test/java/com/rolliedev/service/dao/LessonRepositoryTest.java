package com.rolliedev.service.dao;

import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Lesson;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LessonRepositoryTest extends AbstractDaoTest {

    private final LessonRepository lessonRepository = new LessonRepository(entityManager);

    @Test
    void findAllByCourseId() {
        // when
        Course course = entityManager.createQuery("select c from Course c " +
                                                  "where c.title = :title", Course.class)
                .setParameter("title", "Java Basics")
                .getSingleResult();
        // when
        List<Lesson> results = lessonRepository.findAllByCourseId(course.getId());
        // then
        assertThat(results).hasSize(7);
        List<String> titles = results.stream().map(Lesson::getTitle).toList();
        assertThat(titles).containsExactlyInAnyOrder(
                "OOP. Introduction", "Classes and objects", "Constructors", "Method overloading",
                "Access modifiers", "Stack and Heap", "String class"
        );
    }
}