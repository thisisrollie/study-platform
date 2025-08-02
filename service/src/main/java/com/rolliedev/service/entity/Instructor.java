package com.rolliedev.service.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = "coursesTaught")
@ToString(callSuper = true, exclude = "coursesTaught")
@SuperBuilder
@Entity
@DiscriminatorValue("INSTRUCTOR")
public class Instructor extends User {

    @Builder.Default
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Course> coursesTaught = new HashSet<>();

    public void addCourse(Course course) {
        coursesTaught.add(course);
        course.setInstructor(this);
    }
}
