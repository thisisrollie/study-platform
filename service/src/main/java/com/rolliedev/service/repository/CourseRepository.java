package com.rolliedev.service.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.dto.CourseFilter;
import com.rolliedev.service.dto.InstructorFilter;
import com.rolliedev.service.entity.Course;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.rolliedev.service.entity.QCourse.course;
import static com.rolliedev.service.entity.QEnrollment.enrollment;
import static com.rolliedev.service.entity.QInstructor.instructor;
import static com.rolliedev.service.entity.QStudent.student;

public class CourseRepository extends RepositoryBase<Long, Course> {

    public CourseRepository(EntityManager entityManager) {
        super(Course.class, entityManager);
    }

    public List<Course> findAllByInstructor(CourseFilter filter) {
        InstructorFilter instructorFilter = filter.getInstructor();
        var predicate = QPredicate.builder()
                .add(instructorFilter.getId(), instructor.id::eq)
                .add(instructorFilter.getFirstName(), instructor.firstName::eq)
                .add(instructorFilter.getLastName(), instructor.lastName::eq)
                .add(instructorFilter.getEmail(), instructor.email::eq)
                .buildAnd();

        return new JPAQuery<Course>(getEntityManager())
                .select(course)
                .from(course)
                .join(course.instructor, instructor).fetchJoin()
                .where(predicate)
                .fetch();
    }

    public List<Course> findAllByTitle(String title) {
        return new JPAQuery<Course>(getEntityManager())
                .select(course)
                .from(course)
                .where(course.title.containsIgnoreCase(title))
                .fetch();
    }

    public List<Course> findAllByStudentEmail(String studentEmail) {
        return new JPAQuery<Course>(getEntityManager())
                .select(course)
                .from(course)
                .join(course.enrollments, enrollment)
                .join(enrollment.student, student)
                .where(student.email.eq(studentEmail))
                .fetch();
    }
}
