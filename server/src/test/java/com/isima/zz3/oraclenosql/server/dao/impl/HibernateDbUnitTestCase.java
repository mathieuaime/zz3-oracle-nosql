/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.impl;

import com.isima.zz3.oraclenosql.server.dao.util.HSQLServerUtil;
import com.isima.zz3.oraclenosql.server.dao.util.HibernateUtil;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author excilys
 */
public abstract class HibernateDbUnitTestCase extends DBTestCase {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateDbUnitTestCase.class);

    private static final String DB_NAME = "zz3-oraclenosql-db-test";

    private static SessionFactory sessionFactory;
    protected Session session;

    /**
     * system properties initializing constructor.
     */
    public HibernateDbUnitTestCase() {
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3306/"+DB_NAME);  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");  
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "root");  
    }

    /**
     * Start the server.
     *
     * @throws Exception in case of startup failure.
     */
    @Before
    public void setUp() throws Exception {
        HSQLServerUtil.getInstance().start(DB_NAME);

        LOG.info("Loading hibernate...");
        if (sessionFactory == null) {
            sessionFactory = HibernateUtil.newSessionFactory();
        }

        session = sessionFactory.openSession();

        super.setUp();
    }

    /**
     * shutdown the server.
     *
     * @throws Exception in case of errors.
     */
    @After
    public void tearDown() throws Exception {
        session.close();
        super.tearDown();
        HSQLServerUtil.getInstance().stop();
    }

    /**
     * {@inheritDoc}
     */
    protected abstract IDataSet getDataSet() throws Exception;

    /**
     * {@inheritDoc}
     */
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    /**
     * {@inheritDoc}
     */
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }

}
