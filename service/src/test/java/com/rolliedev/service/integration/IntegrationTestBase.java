package com.rolliedev.service.integration;

import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import util.HibernateTestUtil;
import util.TestDataImporter;

public abstract class IntegrationTestBase {

    private static SessionFactory sessionFactory;

    protected EntityManager entityManager;

    @BeforeAll
    static void initDb() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    static void finish() {
        sessionFactory.close();
    }

    @BeforeEach
    void beginTransaction() {
        entityManager = HibernateTestUtil.buildSessionProxy(sessionFactory);
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void rollbackTransaction() {
        entityManager.getTransaction().rollback();
    }
}
