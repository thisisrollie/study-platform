package com.rolliedev.service.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.rolliedev.service.entity.User;
import jakarta.persistence.EntityManager;

import java.util.Optional;

import static com.rolliedev.service.entity.QUser.user;

public class UserRepository extends RepositoryBase<Long, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(new JPAQuery<User>(getEntityManager())
                .select(user)
                .from(user)
                .where(user.email.eq(email))
                .fetchOne());
    }
}
