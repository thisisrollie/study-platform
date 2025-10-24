package com.rolliedev.service.integration.service;

import com.rolliedev.service.integration.IntegrationTestBase;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractServiceIT extends IntegrationTestBase {

    protected static ValidatorFactory validatorFactory;
    protected static Validator validator;

    @BeforeAll
    static void initValidatorFactory() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidatorFactory() {
        validatorFactory.close();
    }
}
