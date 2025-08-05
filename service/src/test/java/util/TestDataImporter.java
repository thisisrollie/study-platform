package util;

import com.rolliedev.service.entity.Course;
import com.rolliedev.service.entity.Enrollment;
import com.rolliedev.service.entity.Instructor;
import com.rolliedev.service.entity.Lesson;
import com.rolliedev.service.entity.LessonMaterial;
import com.rolliedev.service.entity.Question;
import com.rolliedev.service.entity.Quiz;
import com.rolliedev.service.entity.QuizAttempt;
import com.rolliedev.service.entity.Student;
import com.rolliedev.service.entity.enums.LessonMaterialType;
import com.rolliedev.service.entity.enums.Role;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.IntStream;

@UtilityClass
public class TestDataImporter {

    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Instructor teacherLeila = saveInstructor(session, "Leila", "Valone");
        Instructor teacherJarad = saveInstructor(session, "Jarad", "Yagin");

        Course javaCourse = saveCourse(session, "Java Basics", teacherLeila);
        saveLessons(session,
                List.of("OOP. Introduction", "Classes and objects", "Constructors", "Method overloading",
                        "Access modifiers", "Stack and Heap", "String class"),
                javaCourse
        );
        Quiz javaQuiz1 = saveQuiz(session, "OOP Basics",
                List.of("What is encapsulation in OOP?",
                        "What are the four pillars of OOP?",
                        "What's the difference between a class and an object?"),
                javaCourse
        );
        Quiz javaQuiz2 = saveQuiz(session, "Memory in Java: Stack vs Heap",
                List.of("Where are local variables stored in Java?",
                        "What is the heap memory used for?",
                        "Explain how garbage collection works in Java."),
                javaCourse
        );
        Quiz javaQuiz3 = saveQuiz(session, "String Class Deep Dive",
                List.of("Is String mutable in Java?",
                        "What is the difference between '==' and '.equals()' for Strings?",
                        "What is a String pool?"),
                javaCourse
        );

        Course pythonCourse = saveCourse(session, "Python", teacherLeila);
        saveLessons(session,
                List.of("Variables", "Strings", "String Methods", "Numbers", "Type Conversion", "Comparison Operators",
                        "Conditional Statements", "For Loops", "While Loops", "Iterables", "Defining Functions"),
                pythonCourse
        );
        Quiz pythonQuiz1 = saveQuiz(session, "Python Basics: Variables and Types",
                List.of(
                        "How do you declare a variable in Python?",
                        "What are the basic data types in Python?",
                        "What does dynamic typing mean?"
                ),
                pythonCourse
        );
        Quiz pythonQuiz2 = saveQuiz(session, "Working with Strings",
                List.of(
                        "How do you create a multi-line string in Python?",
                        "What is string concatenation and how is it done?",
                        "What does the `strip()` method do?"
                ),
                pythonCourse
        );
        Quiz pythonQuiz3 = saveQuiz(session, "Numbers and Type Conversion",
                List.of(
                        "How do you convert a string to an integer in Python?",
                        "What's the difference between `/` and `//` operators?",
                        "How does Python handle float precision?"
                ),
                pythonCourse
        );
        Quiz pythonQuiz4 = saveQuiz(session, "Control Flow: Conditionals and Loops",
                List.of(
                        "What is the syntax for an `if` statement in Python?",
                        "What's the difference between `for` and `while` loops?",
                        "How can you exit a loop early?"
                ),
                pythonCourse
        );

        Course dbCourse = saveCourse(session, "Introduction To Databases", teacherLeila);
        saveLessons(session,
                List.of("What is Database?", "Tables & Keys", "SQL Basics", "Creating Tables", "Inserting Data",
                        "Constraints", "Update & Delete", "Basic Queries", "Functions", "Union", "Joins", "Nested Queries", "Triggers"),
                dbCourse
        );
        Quiz dbQuiz1 = saveQuiz(session, "Database Fundamentals",
                List.of(
                        "What is a database?",
                        "What is the difference between a database and a spreadsheet?",
                        "What is a DBMS?"
                ),
                dbCourse
        );
        Quiz dbQuiz2 = saveQuiz(session, "Tables and Keys",
                List.of(
                        "What is a primary key?",
                        "What is a foreign key?",
                        "Can a table have more than one primary key?"
                ),
                dbCourse
        );
        Quiz dbQuiz3 = saveQuiz(session, "Constraints and Data Integrity",
                List.of(
                        "What are constraints in SQL?",
                        "What is a NOT NULL constraint?",
                        "What is the purpose of the UNIQUE constraint?"
                ),
                dbCourse
        );
        Quiz dbQuiz4 = saveQuiz(session, "Combining Data: Union and Joins",
                List.of(
                        "What is the difference between `UNION` and `UNION ALL`?",
                        "What is an INNER JOIN?",
                        "When would you use a LEFT JOIN?"
                ),
                dbCourse
        );

        Course englishCourse = saveCourse(session, "Master English Grammar", teacherJarad);
        saveLessons(session,
                List.of("English Consonants + Vowels", "a / an + Noun", "Singular + Plural Nouns", "Subjective Pronouns + 'Be' Verb",
                        "Subjective Pronouns + Be + Not", "This / That", "These / Those", "Articles + Nouns", "Prepositions in / on / under"),
                englishCourse
        );
        Quiz engQuiz1 = saveQuiz(session, "English Sounds: Consonants & Vowels",
                List.of(
                        "What is the difference between a consonant and a vowel?",
                        "Give three examples of English vowel sounds.",
                        "Which letter can function as both a vowel and a consonant?"
                ),
                englishCourse
        );
        Quiz engQuiz2 = saveQuiz(session, "Singular and Plural Nouns",
                List.of(
                        "What is the plural of 'child'?",
                        "How do you form the plural of most regular nouns?",
                        "What is the rule for making words ending in 'y' plural?"
                ),
                englishCourse
        );
        Quiz engQuiz3 = saveQuiz(session, "Subjective Pronouns + Be Verb",
                List.of(
                        "What are the subjective pronouns in English?",
                        "Fill in the blank: 'She ___ a doctor.'",
                        "What is the correct form of 'be' for 'they'?"
                ),
                englishCourse
        );
        Quiz engQuiz4 = saveQuiz(session, "Demonstratives: This / That",
                List.of(
                        "What is the difference between 'this' and 'that'?",
                        "Choose the correct word: '___ is my bag over there.'",
                        "Is 'this' used for things near or far?"
                ),
                englishCourse
        );
        Quiz engQuiz5 = saveQuiz(session, "Demonstratives: These / Those",
                List.of(
                        "When do you use 'these' instead of 'those'?",
                        "Complete the sentence: '___ are my friends.'",
                        "What's the plural form of 'this'?"
                ),
                englishCourse
        );
        Quiz engQuiz6 = saveQuiz(session, "Prepositions: in / on / under",
                List.of(
                        "Choose the correct preposition: 'The book is ___ the table.'",
                        "What preposition do you use when something is inside something else?",
                        "Where is the cat? It's ___ the bed."
                ),
                englishCourse
        );

        Course englishVocabCourse = saveCourse(session, "Advanced English Vocabulary", teacherJarad);
        saveLessons(session,
                List.of("Introduction", "Emotions", "Traits", "Sensations", "Colours", "Textures", "Age/Time", "Weather", "Taste", "Appearance"),
                englishVocabCourse
        );
        Quiz engVocabQuiz1 = saveQuiz(session, "Vocabulary: Emotions",
                List.of(
                        "What’s a stronger word for 'happy'?",
                        "Give three synonyms for 'angry'.",
                        "What is the difference between 'nervous' and 'anxious'?"
                ),
                englishVocabCourse
        );
        Quiz engVocabQuiz2 = saveQuiz(session, "Vocabulary: Sensations",
                List.of(
                        "What word describes the sensation of something very hot?",
                        "Give a word to describe a light touch.",
                        "What does 'numb' mean?"
                ),
                englishVocabCourse
        );
        Quiz engVocabQuiz3 = saveQuiz(session, "Vocabulary: Colours & Textures",
                List.of(
                        "What is a synonym for 'light blue'?",
                        "Define 'glossy' and 'matte'.",
                        "What does 'velvety' mean?"
                ),
                englishVocabCourse
        );
        Quiz engVocabQuiz4 = saveQuiz(session, "Vocabulary: Weather",
                List.of(
                        "What’s the difference between 'drizzle' and 'shower'?",
                        "What is a 'gust'?",
                        "Define 'humid' and 'muggy'."
                ),
                englishVocabCourse
        );
        Quiz engVocabQuiz5 = saveQuiz(session, "Vocabulary: Taste",
                List.of(
                        "Give three words to describe sweet food.",
                        "What does 'tangy' mean?",
                        "What’s the difference between 'sour' and 'bitter'?"
                ),
                englishVocabCourse
        );

        Student studentPhillip = saveStudent(session, "Phillip", "Malone");
        Student studentLana = saveStudent(session, "Lana", "Lang");
        Student studentClark = saveStudent(session, "Clark", "Kent");

        saveEnrollment(session, studentPhillip, javaCourse);
        saveEnrollment(session, studentPhillip, englishCourse);

        saveQuizAttempt(session, studentPhillip, javaQuiz1, 3, LocalDate.of(2025, Month.MAY, 17));
        saveQuizAttempt(session, studentPhillip, javaQuiz2, 1, LocalDate.of(2025, Month.MAY, 28));
        saveQuizAttempt(session, studentPhillip, engQuiz1, 3, LocalDate.of(2025, Month.JUNE, 3));

        saveEnrollment(session, studentLana, pythonCourse);
        saveEnrollment(session, studentLana, dbCourse);
        saveEnrollment(session, studentLana, englishVocabCourse);

        saveQuizAttempt(session, studentLana, pythonQuiz1, 3, LocalDate.of(2025, Month.MARCH, 12));
        saveQuizAttempt(session, studentLana, pythonQuiz2, 2, LocalDate.of(2025, Month.MARCH, 18));
        saveQuizAttempt(session, studentLana, pythonQuiz2, 3, LocalDate.of(2025, Month.MARCH, 20));
        saveQuizAttempt(session, studentLana, dbQuiz1, 3, LocalDate.of(2025, Month.MARCH, 27));
        saveQuizAttempt(session, studentLana, dbQuiz2, 1, LocalDate.of(2025, Month.APRIL, 5));
        saveQuizAttempt(session, studentLana, dbQuiz2, 2, LocalDate.of(2025, Month.APRIL, 10));
        saveQuizAttempt(session, studentLana, dbQuiz2, 3, LocalDate.of(2025, Month.APRIL, 11));

        saveEnrollment(session, studentClark, javaCourse);
        saveEnrollment(session, studentClark, dbCourse);

        saveQuizAttempt(session, studentClark, javaQuiz1, 3, LocalDate.of(2025, Month.MAY, 17));
        saveQuizAttempt(session, studentClark, javaQuiz2, 3, LocalDate.of(2025, Month.MAY, 30));
        saveQuizAttempt(session, studentClark, javaQuiz3, 3, LocalDate.of(2025, Month.JUNE, 12));
        saveQuizAttempt(session, studentClark, dbQuiz1, 3, LocalDate.of(2025, Month.MAY, 20));
        saveQuizAttempt(session, studentClark, dbQuiz2, 2, LocalDate.of(2025, Month.MAY, 30));
        saveQuizAttempt(session, studentClark, dbQuiz3, 1, LocalDate.of(2025, Month.JUNE, 7));
        saveQuizAttempt(session, studentClark, dbQuiz3, 2, LocalDate.of(2025, Month.JUNE, 10));

        session.getTransaction().commit();
    }

    private Instructor saveInstructor(Session session,
                                      String firstName,
                                      String lastName) {
        Instructor instructor = Instructor.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email((firstName + lastName).toLowerCase() + "gmail.com")
                .password("abc")
                .role(Role.INSTRUCTOR)
                .build();
        session.persist(instructor);

        return instructor;
    }

    private Student saveStudent(Session session,
                                String firstName,
                                String lastName) {
        Student student = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email((firstName + lastName).toLowerCase() + "@gmail.com")
                .password("123")
                .role(Role.STUDENT)
                .build();
        session.persist(student);

        return student;
    }

    private Course saveCourse(Session session,
                              String title,
                              Instructor instructor) {
        Course course = Course.builder()
                .title(title)
                .instructor(instructor)
                .build();
        session.persist(course);


        return course;
    }

    private void saveLessons(Session session, List<String> titles, Course course) {
        IntStream.range(0, titles.size())
                .mapToObj(i -> Lesson.builder()
                        .title(titles.get(i))
                        .order(i + 1)
                        .course(course)
                        .build())
                .forEach(lesson -> {
                    session.persist(lesson);
                    for (int i = 1; i <= RandomUtil.nextIntFromOne(5); i++) {
                        LessonMaterial lessonMaterial = buildLessonMaterial(lesson, i);
                        session.persist(lessonMaterial);
                    }
                });
    }

    private LessonMaterial buildLessonMaterial(Lesson lesson, Integer order) {
        return LessonMaterial.builder()
                .contentUrl("fake_url")
                .type(LessonMaterialType.values()[RandomUtil.nextInt(LessonMaterialType.values().length)])
                .order(order)
                .lesson(lesson)
                .build();
    }

    private Quiz saveQuiz(Session session, String title, List<String> questionTexts, Course course) {
        Quiz quiz = Quiz.builder()
                .title(title)
                .course(course)
                .build();

        for (String questionText : questionTexts) {
            var question = buildQuestion(questionText, List.of("A", "B", "C", "D"), "dummy_answer");
            quiz.addQuestion(question);
        }
        session.persist(quiz);
        return quiz;
    }

    private Question buildQuestion(String text, List<String> options, String answer) {
        return Question.builder()
                .text(text)
                .options(options)
                .answer(answer)
                .build();
    }

    private void saveEnrollment(Session session,
                                Student student,
                                Course course) {
        var enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .build();
        session.persist(enrollment);
    }

    private void saveQuizAttempt(Session session,
                                 Student student,
                                 Quiz quiz,
                                 Integer score,
                                 LocalDate attemptedAt) {
        var quizAttempt = QuizAttempt.builder()
                .student(student)
                .quiz(quiz)
                .score(score)
                .attemptedAt(attemptedAt.atStartOfDay().toInstant(ZoneOffset.UTC))
                .build();
        session.persist(quizAttempt);
    }
}
