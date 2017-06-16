/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.impl;

import com.isima.zz3.oraclenosql.server.dao.interfaces.EntityDAO;
import com.isima.zz3.oraclenosql.server.entity.Author;
import com.isima.zz3.oraclenosql.server.entity.Page;
import java.io.File;
import java.util.List;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

/**
 *
 * @author mathieu
 */
public class MySQLAuthorDAOImplTest extends HibernateDbUnitTestCase {

    private static final String SAMPLE_TEST_XML = "src/test/resources/db-sample/db-sample-author.xml";
    
    private final EntityDAO<Author> instance = new MySQLAuthorDAOImpl();
    
    private final Author a1 = new Author.Builder("Name1").build();
    private final Author a2 = new Author.Builder("Name2").build();
    private final Author a3 = new Author.Builder("Name3").build();

    /**
     * Test of get method, of class MySQLAuthorDAOImpl.
     */
    public void testGet_0args() {
        System.out.println("get");
        List<Author> result = instance.get();
        assertEquals(2, result.size());
    }

    /**
     * Test of save method, of class MySQLAuthorDAOImpl.
     */
    public void testSave() {
        System.out.println("save");
        Author object = a3;
        Author result = instance.save(object);
        assertEquals(a3.getName(), result.getName());
    }

    /**
     * Test of save method, of class MySQLAuthorDAOImpl.
     */
    public void testSave_Update() {
        System.out.println("save");
        Author object = new Author.Builder("NewName1").build();
        object.setId(1L);
        Author result = instance.save(object);
        assertEquals(object.getName(), result.getName());
    }

    /**
     * Test of delete method, of class MySQLAuthorDAOImpl.
     */
    public void testDelete() {
        System.out.println("delete");
        Author object = a1;
        instance.delete(object);
    }

    /**
     * Test of get method, of class MySQLAuthorDAOImpl.
     */
    public void testGet_String_Empty() {
        System.out.println("get");
        String search = "";
        List<Author> result = instance.get(search);
        assertEquals(2, result.size());
    }

    /**
     * Test of get method, of class MySQLAuthorDAOImpl.
     */
    public void testGet_String_Not_Found() {
        System.out.println("get");
        String search = "NNN";
        List<Author> result = instance.get(search);
        assertEquals(0, result.size());
    }

    /**
     * Test of get method, of class MySQLAuthorDAOImpl.
     */
    public void testGet_String() {
        System.out.println("get");
        String search = "Name1";
        List<Author> result = instance.get(search);
        System.out.println(result);
        assertEquals(1, result.size());
    }

    /**
     * Test of get method, of class MySQLAuthorDAOImpl.
     */
    public void testGet_long() {
        System.out.println("get");
        long id = 1L;
        Author expResult = a1;
        Author result = instance.get(id);
        assertEquals(expResult.getName(), result.getName());
    }

    /**
     * Test of get method, of class MySQLAuthorDAOImpl.
     */
    public void testGet_Page() {
        System.out.println("get");
        Page page = new Page();
        Page<Author> result = instance.get(page);
        assertEquals(2, result.getObjects().size());
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

    /**
     * {@inheritDoc}
     */
    protected IDataSet getDataSet() throws Exception {
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        IDataSet dataSet = builder.build(new File(SAMPLE_TEST_XML));
        return dataSet;
    }
    
}
