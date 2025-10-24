package com.rolliedev.service.integration.repository;

import com.rolliedev.service.integration.TestDataHelper;
import com.rolliedev.service.repository.LessonRepository;
import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Lesson;
import com.rolliedev.service.integration.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LessonRepositoryIT extends IntegrationTestBase {

    private LessonRepository lessonRepository;

    @BeforeEach
    void init() {
        lessonRepository = new LessonRepository(entityManager);
    }

    @Test
    void findAllByCourseId() {
        // when
        TestDataHelper dataHelper = new TestDataHelper(entityManager);
        Course course = dataHelper.findCourseByTitle("Java Basics");
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