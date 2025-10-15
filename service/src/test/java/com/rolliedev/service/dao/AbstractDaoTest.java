package com.rolliedev.service.dao;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import util.HibernateTestUtil;
import util.TestDataImporter;

@Getter
public abstract class AbstractDaoTest {

    protected static SessionFactory sessionFactory;
    protected static EntityManager entityManager;

    @BeforeAll
    static void initDb() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
        entityManager = HibernateTestUtil.buildSessionProxy(sessionFactory);
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    static void finish() {
        sessionFactory.close();
    }

    @BeforeEach
    void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void rollbackTransaction() {
        entityManager.getTransaction().rollback();
    }
}
