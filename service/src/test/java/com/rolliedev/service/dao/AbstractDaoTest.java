package com.rolliedev.service.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import util.HibernateTestUtil;
import util.TestDataImporter;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public abstract class AbstractDaoTest {

    protected final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();

    @BeforeAll
    void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    void finish() {
        sessionFactory.close();
    }
}
