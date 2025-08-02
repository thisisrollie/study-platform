package com.rolliedev.service.util;

import com.rolliedev.service.entity.Admin;
import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Enrollment;
import com.rolliedev.service.entity.Instructor;
import com.rolliedev.service.entity.Lesson;
import com.rolliedev.service.entity.LessonMaterial;
import com.rolliedev.service.entity.Question;
import com.rolliedev.service.entity.Quiz;
import com.rolliedev.service.entity.QuizAttempt;
import com.rolliedev.service.entity.Student;
import com.rolliedev.service.entity.User;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfiguration();
        configuration.configure();

        return configuration.buildSessionFactory();
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Course.class);
        configuration.addAnnotatedClass(Enrollment.class);
        configuration.addAnnotatedClass(Lesson.class);
        configuration.addAnnotatedClass(LessonMaterial.class);
        configuration.addAnnotatedClass(Question.class);
        configuration.addAnnotatedClass(Quiz.class);
        configuration.addAnnotatedClass(QuizAttempt.class);
        configuration.addAnnotatedClass(Instructor.class);
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Admin.class);

        return configuration;
    }
}
