/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.impl;

import com.isima.zz3.oraclenosql.server.dao.util.HSQLServerUtil;
import com.isima.zz3.oraclenosql.server.dao.util.HibernateUtil;
import com.isima.zz3.oraclenosql.server.entity.Article;
import com.isima.zz3.oraclenosql.server.entity.Page;
import java.util.List;
import org.dbunit.DBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;

/**
 *
 * @author mathieu
 */
public class MySQLArticleDAOImplTest extends DBTestCase {

    private static SessionFactory sessionFactory;
    protected Session session;

    private static final String SAMPLE_TEST_XML = "src/test/resources/db-sample.xml";

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MySQLArticleDAOImplTest.class);

    public MySQLArticleDAOImplTest(String testName) {
        super(testName);
    }

    /**
     * Test of get method, of class MySQLArticleDAOImpl.
     */
    public void testGet_0args() {
        System.out.println("get");
        MySQLArticleDAOImpl instance = new MySQLArticleDAOImpl();
        List<Article> expResult = null;
        List<Article> result = instance.get();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of save method, of class MySQLArticleDAOImpl.
     */
    public void testSave() {
        System.out.println("save");
        Article object = new Article.Builder("Title1").build();
        MySQLArticleDAOImpl instance = new MySQLArticleDAOImpl();
        Article expResult = object;
        Article result = instance.save(object);
        assertEquals(expResult, result);
    }

    /**
     * Test of delete method, of class MySQLArticleDAOImpl.
     */
    public void testDelete() {
        System.out.println("delete");
        Article object = null;
        MySQLArticleDAOImpl instance = new MySQLArticleDAOImpl();
        instance.delete(object);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class MySQLArticleDAOImpl.
     */
    public void testGet_String() {
        System.out.println("get");
        String search = "";
        MySQLArticleDAOImpl instance = new MySQLArticleDAOImpl();
        List<Article> expResult = null;
        List<Article> result = instance.get(search);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class MySQLArticleDAOImpl.
     */
    public void testGet_long() {
        System.out.println("get");
        long id = 0L;
        MySQLArticleDAOImpl instance = new MySQLArticleDAOImpl();
        Article expResult = null;
        Article result = instance.get(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class MySQLArticleDAOImpl.
     */
    public void testGet_Page() {
        System.out.println("get");
        Page page = null;
        MySQLArticleDAOImpl instance = new MySQLArticleDAOImpl();
        Page<Article> expResult = null;
        Page<Article> result = instance.get(page);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of count method, of class MySQLArticleDAOImpl.
     */
    public void testCount() {
        System.out.println("count");
        Page page = null;
        MySQLArticleDAOImpl instance = new MySQLArticleDAOImpl();
        long expResult = 0L;
        long result = instance.count(page);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Start the server.
     *
     * @throws Exception in case of startup failure.
     */
    @Before
    public void setUp() throws Exception {
        HSQLServerUtil.getInstance().start("DBNAME");

        LOGGER.info("Loading hibernate...");
        if (sessionFactory == null) {
            sessionFactory = HibernateUtil.newSessionFactory("hibernate.test.cfg.xml");
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

    @Override
    protected IDataSet getDataSet() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
        return DatabaseOperation.NONE;
    }

}
