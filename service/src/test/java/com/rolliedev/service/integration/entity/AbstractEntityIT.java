package com.rolliedev.service.integration.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import util.HibernateTestUtil;

public abstract class AbstractEntityIT {

    private static SessionFactory sessionFactory;

    protected Session session;

    @BeforeAll
    static void beforeAll() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @AfterAll
    static void afterAll() {
        sessionFactory.close();
    }

    @BeforeEach
    void openSessionAndTransaction() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void rollbackAndClose() {
        session.getTransaction().rollback();
        session.close();
    }

    protected void flushAndClear() {
        session.flush();
        session.clear();
    }
}
