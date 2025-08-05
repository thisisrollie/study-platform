package com.rolliedev.service.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.dto.StudentFilter;
import com.rolliedev.service.entity.Student;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

import static com.rolliedev.service.entity.QCourse.course;
import static com.rolliedev.service.entity.QEnrollment.enrollment;
import static com.rolliedev.service.entity.QStudent.student;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudentDao {

    private static final StudentDao INSTANCE = new StudentDao();

    public static StudentDao getInstance() {
        return INSTANCE;
    }

    public List<Student> findAllByCourse(Session session, StudentFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getCourseId(), course.id::eq)
                .add(filter.getCourseTitle(), course.title::eq)
                .buildAnd();

        return new JPAQuery<Student>(session)
                .select(student)
                .from(student)
                .join(student.enrollments, enrollment)
                .join(enrollment.course, course)
                .where(predicate)
                .fetch();
    }


}
