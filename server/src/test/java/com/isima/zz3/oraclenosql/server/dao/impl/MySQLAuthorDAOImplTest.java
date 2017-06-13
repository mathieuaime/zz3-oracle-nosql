/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.impl;

import com.isima.zz3.oraclenosql.server.entity.Author;
import com.isima.zz3.oraclenosql.server.entity.Page;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author mathieu
 */
public class MySQLAuthorDAOImplTest extends TestCase {
    
    public MySQLAuthorDAOImplTest(String testName) {
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
     * Test of get method, of class MySQLAuthorDAOImpl.
     */
    public void testGet_0args() {
        System.out.println("get");
        MySQLAuthorDAOImpl instance = new MySQLAuthorDAOImpl();
        List<Author> expResult = null;
        List<Author> result = instance.get();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of save method, of class MySQLAuthorDAOImpl.
     */
    public void testSave() {
        System.out.println("save");
        Author object = null;
        MySQLAuthorDAOImpl instance = new MySQLAuthorDAOImpl();
        Author expResult = null;
        Author result = instance.save(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class MySQLAuthorDAOImpl.
     */
    public void testDelete() {
        System.out.println("delete");
        Author object = null;
        MySQLAuthorDAOImpl instance = new MySQLAuthorDAOImpl();
        instance.delete(object);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class MySQLAuthorDAOImpl.
     */
    public void testGet_String() {
        System.out.println("get");
        String search = "";
        MySQLAuthorDAOImpl instance = new MySQLAuthorDAOImpl();
        List<Author> expResult = null;
        List<Author> result = instance.get(search);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class MySQLAuthorDAOImpl.
     */
    public void testGet_long() {
        System.out.println("get");
        long id = 0L;
        MySQLAuthorDAOImpl instance = new MySQLAuthorDAOImpl();
        Author expResult = null;
        Author result = instance.get(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class MySQLAuthorDAOImpl.
     */
    public void testGet_Page() {
        System.out.println("get");
        Page page = null;
        MySQLAuthorDAOImpl instance = new MySQLAuthorDAOImpl();
        Page<Author> expResult = null;
        Page<Author> result = instance.get(page);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of count method, of class MySQLAuthorDAOImpl.
     */
    public void testCount() {
        System.out.println("count");
        Page page = null;
        MySQLAuthorDAOImpl instance = new MySQLAuthorDAOImpl();
        long expResult = 0L;
        long result = instance.count(page);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
