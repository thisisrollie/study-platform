package com.rolliedev.service.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.entity.Lesson;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.rolliedev.service.entity.QLesson.lesson;

public class LessonRepository extends RepositoryBase<Long, Lesson> {

    public LessonRepository(EntityManager entityManager) {
        super(Lesson.class, entityManager);
    }

    public List<Lesson> findAllByCourseId(Long courseId) {
        return new JPAQuery<Lesson>(getEntityManager())
                .select(lesson)
                .from(lesson)
                .where(lesson.course.id.eq(courseId))
                .fetch();
    }
}
