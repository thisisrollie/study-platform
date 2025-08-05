package com.rolliedev.service.dao;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.dto.CourseFilter;
import com.rolliedev.service.dto.InstructorFilter;
import com.rolliedev.service.entity.Course;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

import static com.rolliedev.service.entity.QCourse.course;
import static com.rolliedev.service.entity.QEnrollment.enrollment;
import static com.rolliedev.service.entity.QInstructor.instructor;
import static com.rolliedev.service.entity.QStudent.student;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseDao {

    private static final CourseDao INSTANCE = new CourseDao();

    public static CourseDao getInstance() {
        return INSTANCE;
    }

    public List<Course> findAll(Session session) {
        return new JPAQuery<Course>(session)
                .select(course)
                .from(course)
                .fetch();
    }

    public Optional<Course> findById(Session session, Long id) {
        return Optional.ofNullable(
                session.find(Course.class, id)
        );
    }

    public List<Course> findAllByInstructor(Session session, CourseFilter filter) {
        InstructorFilter instructorFilter = filter.getInstructor();
        var predicate = QPredicate.builder()
                .add(instructorFilter.getFirstName(), instructor.firstName::eq)
                .add(instructorFilter.getLastName(), instructor.lastName::eq)
                .add(instructorFilter.getEmail(), instructor.email::eq)
                .buildAnd();

        return new JPAQuery<Course>(session)
                .select(course)
                .from(course)
                .join(course.instructor, instructor)
                .where(predicate)
                .fetch();
    }

    public List<Course> findAllByTitle(Session session, String title) {
        return new JPAQuery<Course>(session)
                .select(course)
                .from(course)
                .where(course.title.containsIgnoreCase(title))
                .fetch();
    }

    public List<Course> findAllInWhichStudentEnrolled(Session session, String studentEmail) {
        return new JPAQuery<Course>(session)
                .select(course)
                .from(course)
                .join(course.enrollments, enrollment)
                .join(enrollment.student, student)
                .where(student.email.eq(studentEmail))
                .fetch();
    }
}
