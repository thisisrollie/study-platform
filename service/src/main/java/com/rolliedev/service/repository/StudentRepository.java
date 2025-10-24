package com.rolliedev.service.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.entity.Student;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.rolliedev.service.entity.QEnrollment.enrollment;
import static com.rolliedev.service.entity.QStudent.student;

public class StudentRepository extends RepositoryBase<Long, Student> {

    public StudentRepository(EntityManager entityManager) {
        super(Student.class, entityManager);
    }

    public List<Student> findAllByCourseId(Long courseId) {
        return new JPAQuery<Student>(getEntityManager())
                .select(student)
                .from(student)
                .join(student.enrollments, enrollment)
                .where(enrollment.course.id.eq(courseId))
                .fetch();
    }
}
