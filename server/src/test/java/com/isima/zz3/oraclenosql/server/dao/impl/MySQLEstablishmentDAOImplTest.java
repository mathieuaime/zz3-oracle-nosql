/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.impl;

import com.isima.zz3.oraclenosql.server.entity.Establishment;
import com.isima.zz3.oraclenosql.server.entity.Page;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author mathieu
 */
public class MySQLEstablishmentDAOImplTest extends TestCase {
    
    public MySQLEstablishmentDAOImplTest(String testName) {
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
     * Test of get method, of class MySQLEstablishmentDAOImpl.
     */
    public void testGet_0args() {
        System.out.println("get");
        MySQLEstablishmentDAOImpl instance = new MySQLEstablishmentDAOImpl();
        List<Establishment> expResult = null;
        List<Establishment> result = instance.get();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of save method, of class MySQLEstablishmentDAOImpl.
     */
    public void testSave() {
        System.out.println("save");
        Establishment object = null;
        MySQLEstablishmentDAOImpl instance = new MySQLEstablishmentDAOImpl();
        Establishment expResult = null;
        Establishment result = instance.save(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class MySQLEstablishmentDAOImpl.
     */
    public void testDelete() {
        System.out.println("delete");
        Establishment object = null;
        MySQLEstablishmentDAOImpl instance = new MySQLEstablishmentDAOImpl();
        instance.delete(object);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class MySQLEstablishmentDAOImpl.
     */
    public void testGet_String() {
        System.out.println("get");
        String search = "";
        MySQLEstablishmentDAOImpl instance = new MySQLEstablishmentDAOImpl();
        List<Establishment> expResult = null;
        List<Establishment> result = instance.get(search);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class MySQLEstablishmentDAOImpl.
     */
    public void testGet_long() {
        System.out.println("get");
        long id = 0L;
        MySQLEstablishmentDAOImpl instance = new MySQLEstablishmentDAOImpl();
        Establishment expResult = null;
        Establishment result = instance.get(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class MySQLEstablishmentDAOImpl.
     */
    public void testGet_Page() {
        System.out.println("get");
        Page page = null;
        MySQLEstablishmentDAOImpl instance = new MySQLEstablishmentDAOImpl();
        Page<Establishment> expResult = null;
        Page<Establishment> result = instance.get(page);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of count method, of class MySQLEstablishmentDAOImpl.
     */
    public void testCount() {
        System.out.println("count");
        Page page = null;
        MySQLEstablishmentDAOImpl instance = new MySQLEstablishmentDAOImpl();
        long expResult = 0L;
        long result = instance.count(page);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
