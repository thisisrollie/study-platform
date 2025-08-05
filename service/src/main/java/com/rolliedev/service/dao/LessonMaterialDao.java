package com.rolliedev.service.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.dto.LessonMaterialFilter;
import com.rolliedev.service.entity.LessonMaterial;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

import static com.rolliedev.service.entity.QLesson.lesson;
import static com.rolliedev.service.entity.QLessonMaterial.lessonMaterial;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LessonMaterialDao {

    private static final LessonMaterialDao INSTANCE = new LessonMaterialDao();

    public static LessonMaterialDao getInstance() {
        return INSTANCE;
    }

    public List<LessonMaterial> findAllByLesson(Session session, LessonMaterialFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getLessonId(), lesson.id::eq)
                .add(filter.getLessonTitle(), lesson.title::eq)
                .buildAnd();

        return new JPAQuery<LessonMaterial>(session)
                .select(lessonMaterial)
                .from(lessonMaterial)
                .join(lessonMaterial.lesson, lesson)
                .where(predicate)
                .fetch();
    }
}
