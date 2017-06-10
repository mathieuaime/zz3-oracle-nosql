/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.impl;

import com.isima.zz3.oraclenosql.server.entity.model.Entity;
import com.isima.zz3.oraclenosql.server.entity.model.Page;
import java.util.Set;
import junit.framework.TestCase;

/**
 *
 * @author mathieu
 */
public class MySQLEntityDAOImplTest extends TestCase {
    
    public MySQLEntityDAOImplTest(String testName) {
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
     * Test of get method, of class MySQLEntityDAOImpl.
     */
    public void testGet_0args() {
        System.out.println("get");
        MySQLEntityDAOImpl instance = new MySQLEntityDAOImpl();
        Set<Entity> expResult = null;
        Set<Entity> result = instance.get();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of save method, of class MySQLEntityDAOImpl.
     */
    public void testSave() throws Exception {
        System.out.println("save");
        Entity object = null;
        MySQLEntityDAOImpl instance = new MySQLEntityDAOImpl();
        Entity expResult = null;
        Entity result = instance.save(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class MySQLEntityDAOImpl.
     */
    public void testDelete() throws Exception {
        System.out.println("delete");
        Entity object = null;
        MySQLEntityDAOImpl instance = new MySQLEntityDAOImpl();
        instance.delete(object);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class MySQLEntityDAOImpl.
     */
    public void testGet_String() throws Exception {
        System.out.println("get");
        String search = "";
        MySQLEntityDAOImpl instance = new MySQLEntityDAOImpl();
        Entity expResult = null;
        Entity result = instance.get(search);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class MySQLEntityDAOImpl.
     */
    public void testGet_long() throws Exception {
        System.out.println("get");
        long id = 0L;
        MySQLEntityDAOImpl instance = new MySQLEntityDAOImpl();
        Entity expResult = null;
        Entity result = instance.get(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class MySQLEntityDAOImpl.
     */
    public void testGet_Page() {
        System.out.println("get");
        Page page = null;
        MySQLEntityDAOImpl instance = new MySQLEntityDAOImpl();
        Page<Entity> expResult = null;
        Page<Entity> result = instance.get(page);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of count method, of class MySQLEntityDAOImpl.
     */
    public void testCount() {
        System.out.println("count");
        Page page = null;
        MySQLEntityDAOImpl instance = new MySQLEntityDAOImpl();
        long expResult = 0L;
        long result = instance.count(page);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
