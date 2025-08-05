package com.rolliedev.service.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.dto.LessonFilter;
import com.rolliedev.service.entity.Lesson;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

import static com.rolliedev.service.entity.QCourse.course;
import static com.rolliedev.service.entity.QLesson.lesson;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LessonDao {

    private static final LessonDao INSTANCE = new LessonDao();

    public static LessonDao getInstance() {
        return INSTANCE;
    }

    public List<Lesson> findAllByFilter(Session session, LessonFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getTitle(), lesson.title::eq)
                .add(filter.getCourseId(), course.id::eq)
                .add(filter.getCourseTitle(), course.title::eq)
                .add(filter.getInstructorId(), course.instructor.id::eq)
                .buildAnd();

        return new JPAQuery<Lesson>(session)
                .select(lesson)
                .from(lesson)
                .join(lesson.course, course)
                .where(predicate)
                .fetch();
    }
}
