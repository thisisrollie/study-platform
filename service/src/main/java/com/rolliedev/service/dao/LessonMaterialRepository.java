package com.rolliedev.service.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.entity.LessonMaterial;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.rolliedev.service.entity.QLessonMaterial.lessonMaterial;

public class LessonMaterialRepository extends RepositoryBase<Long, LessonMaterial> {

    public LessonMaterialRepository(EntityManager entityManager) {
        super(LessonMaterial.class, entityManager);
    }

    public List<LessonMaterial> findAllByLessonId(Long lessonId) {
        return new JPAQuery<LessonMaterial>(getEntityManager())
                .select(lessonMaterial)
                .from(lessonMaterial)
                .where(lessonMaterial.lesson.id.eq(lessonId))
                .fetch();
    }
}
