package com.rolliedev.service.repository;

import com.rolliedev.service.entity.Instructor;
import jakarta.persistence.EntityManager;

public class InstructorRepository extends RepositoryBase<Long, Instructor> {

    public InstructorRepository(EntityManager entityManager) {
        super(Instructor.class, entityManager);
    }
}
