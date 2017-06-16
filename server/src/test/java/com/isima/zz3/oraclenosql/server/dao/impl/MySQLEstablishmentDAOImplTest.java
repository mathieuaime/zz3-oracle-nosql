/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.impl;

import com.isima.zz3.oraclenosql.server.entity.Establishment;
import com.isima.zz3.oraclenosql.server.entity.Laboratory;
import com.isima.zz3.oraclenosql.server.entity.Page;
import com.isima.zz3.oraclenosql.server.entity.University;
import java.io.File;
import java.util.List;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

/**
 *
 * @author mathieu
 */
public class MySQLEstablishmentDAOImplTest extends HibernateDbUnitTestCase {

    private static final String SAMPLE_TEST_XML
            = "src/test/resources/db-sample/db-sample-establishment.xml";

    private final Establishment univ1 = new University("Univ1", "");
    private final Establishment univ2 = new University("Univ2", "");
    private final Establishment univ3 = new University("Univ3", "");

    private final Establishment labo1 = new Laboratory("Labo1", "");
    private final Establishment labo2 = new Laboratory("Labo2", "");
    private final Establishment labo3 = new Laboratory("Labo3", "");

    private final MySQLEstablishmentDAOImpl instance
            = new MySQLEstablishmentDAOImpl();

    /**
     * Test of get method, of class MySQLEstablishmentDAOImpl.
     */
    public void testGet_0args() {
        System.out.println("get");
        List result = instance.get();
        assertEquals(6, result.size());
    }

    /**
     * Test of save method, of class MySQLEstablishmentDAOImpl.
     */
    public void testSave() {
        System.out.println("save");
        Establishment object = univ1;
        Establishment expResult = univ1;
        Establishment result = instance.save(object);
        assertEquals(expResult.getName(), result.getName());
    }

    /**
     * Test of delete method, of class MySQLEstablishmentDAOImpl.
     */
    public void testDelete() {
        System.out.println("delete");
        instance.delete(univ1);
    }

    /**
     * Test of get method, of class MySQLEstablishmentDAOImpl.
     */
    public void testGet_Search_Not_Found() {
        System.out.println("get");
        String search = "UUU";
        List result = instance.get(search);
        assertEquals(0, result.size());
    }

    /**
     * Test of get method, of class MySQLEstablishmentDAOImpl.
     */
    public void testGet_Search_Empty() {
        System.out.println("get");
        String search = "";
        List result = instance.get(search);
        assertEquals(6, result.size());
    }

    /**
     * Test of get method, of class MySQLEstablishmentDAOImpl.
     */
    public void testGet_Search() {
        System.out.println("get");
        String search = "Univ";
        List result = instance.get(search);
        assertEquals(3, result.size());
    }
    
    /**
     * Test of get method, of class MySQLEstablishmentDAOImpl.
     */
    public void testGet_Type() {
        System.out.println("get");
        String search = "";
        String type = "laboratory";
        List result = instance.get(search, type);
        assertEquals(3, result.size());
    }

    /**
     * Test of get method, of class MySQLEstablishmentDAOImpl.
     */
    public void testGet_long() {
        System.out.println("get");
        long id = 1L;
        Establishment result = instance.get(id);
        assertEquals(univ1.getName(), result.getName());
    }

    /**
     * Test of get method, of class MySQLEstablishmentDAOImpl.
     */
    public void testGet_Page() {
        System.out.println("get");
        Page page = new Page<>();
        Page<Establishment> result = instance.get(page);
        assertEquals(6, result.getObjects().size());
    }

    /**
     * Test of count method, of class MySQLEstablishmentDAOImpl.
     */
    public void testCount() {
        System.out.println("count");
        Page page = null;
        long expResult = 0L;
        long result = instance.count(page);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected IDataSet getDataSet() throws Exception {
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        IDataSet dataSet = builder.build(new File(SAMPLE_TEST_XML));
        return dataSet;
    }

}
