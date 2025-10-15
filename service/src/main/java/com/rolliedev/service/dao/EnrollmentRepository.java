package com.rolliedev.service.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.entity.Enrollment;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.rolliedev.service.entity.QEnrollment.enrollment;

public class EnrollmentRepository extends RepositoryBase<Long, Enrollment> {

    public EnrollmentRepository(EntityManager entityManager) {
        super(Enrollment.class, entityManager);
    }

    public List<Enrollment> findAllByStudentId(Long studentId) {
        return new JPAQuery<Enrollment>(getEntityManager())
                .select(enrollment)
                .from(enrollment)
                .where(enrollment.student.id.eq(studentId))
                .fetch();
    }

    public List<Enrollment> findAllByCourseId(Long courseId) {
        return new JPAQuery<Enrollment>(getEntityManager())
                .select(enrollment)
                .from(enrollment)
                .where(enrollment.course.id.eq(courseId))
                .fetch();
    }
}
