package util;

import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Instructor;
import com.rolliedev.service.entity.enums.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestObjectUtils {

    public static final Instructor LEILA_INSTRUCTOR = Instructor.builder()
            .id(1L)
            .firstName("Leila")
            .lastName("Valone")
            .email("leilavalone@gmail.com")
            .password("abc")
            .role(Role.INSTRUCTOR)
            .build();

    public static final Course JAVA_BASICS_COURSE = Course.builder()
            .id(1L)
            .title("Java Basics")
            .description("java description")
            .instructor(LEILA_INSTRUCTOR)
            .build();

    public static final Course PYTHON_COURSE = Course.builder()
            .id(2L)
            .title("Python")
            .description("python description")
            .instructor(LEILA_INSTRUCTOR)
            .build();

    public static final Course INTRO_TO_DB_COURSE = Course.builder()
            .id(3L)
            .title("Introduction To Databases")
            .description("db description")
            .instructor(LEILA_INSTRUCTOR)
            .build();
}
