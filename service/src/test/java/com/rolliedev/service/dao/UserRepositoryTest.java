package com.rolliedev.service.dao;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends AbstractDaoTest {

    private final UserRepository userRepository = new UserRepository(entityManager);

    @Test
    void shouldFindUserByEmail() {
        // when
        var actualResult = userRepository.findByEmail("lanalang@gmail.com");
        // then
        assertThat(actualResult.isPresent()).isTrue();
    }

    @Test
    void findByEmailShouldReturnEmptyOptional() {
        // when
        var actualResult = userRepository.findByEmail("dummy");
        // then
        assertThat(actualResult.isEmpty()).isTrue();
    }
}