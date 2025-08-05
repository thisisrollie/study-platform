package com.rolliedev.service.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.entity.Enrollment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

import static com.rolliedev.service.entity.QEnrollment.enrollment;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnrollmentDao {

    private static final EnrollmentDao INSTANCE = new EnrollmentDao();

    public static EnrollmentDao getInstance() {
        return INSTANCE;
    }

    public List<Enrollment> findAllByStudent(Session session, Long studentId) {
        return new JPAQuery<Enrollment>(session)
                .select(enrollment)
                .from(enrollment)
                .where(enrollment.student.id.eq(studentId))
                .fetch();
    }

    public List<Enrollment> findAllByCourse(Session session, Long courseId) {
        return new JPAQuery<Enrollment>(session)
                .select(enrollment)
                .from(enrollment)
                .where(enrollment.course.id.eq(courseId))
                .fetch();
    }
}
