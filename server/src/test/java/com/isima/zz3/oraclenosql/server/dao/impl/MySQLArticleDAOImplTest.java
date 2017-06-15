/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.impl;

import com.isima.zz3.oraclenosql.server.entity.Article;
import com.isima.zz3.oraclenosql.server.entity.Page;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author mathieu
 */
public class MySQLArticleDAOImplTest extends TestCase {

    public MySQLArticleDAOImplTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
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
        Article object = null;
        MySQLArticleDAOImpl instance = new MySQLArticleDAOImpl();
        Article expResult = null;
        Article result = instance.save(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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

}
