package com.rolliedev.service.integration.repository;

import com.rolliedev.service.repository.UserRepository;
import com.rolliedev.service.integration.IntegrationTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryIT extends IntegrationTestBase {

    private UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository = new UserRepository(entityManager);
    }

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