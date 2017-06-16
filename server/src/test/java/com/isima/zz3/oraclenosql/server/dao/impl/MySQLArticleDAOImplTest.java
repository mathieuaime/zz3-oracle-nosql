/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.impl;

import com.isima.zz3.oraclenosql.server.dao.interfaces.EntityDAO;
import com.isima.zz3.oraclenosql.server.entity.Article;
import com.isima.zz3.oraclenosql.server.entity.Page;
import java.io.File;
import java.util.List;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

/**
 *
 * @author mathieu
 */
public class MySQLArticleDAOImplTest extends HibernateDbUnitTestCase {

    private static final String SAMPLE_TEST_XML = "src/test/resources/db-sample/db-sample-article.xml";

    private final EntityDAO<Article> instance = new MySQLArticleDAOImpl();

    private final Article a1 = new Article.Builder("Title1").build();
    private final Article a2 = new Article.Builder("Title2").build();
    private final Article a3 = new Article.Builder("Title3").build();

    /**
     * Test of get method, of class MySQLArticleDAOImpl.
     */
    public void testGet_0args() {
        System.out.println("get");
        List<Article> result = instance.get();
        assertEquals(2, result.size());
    }

    /**
     * Test of save method, of class MySQLArticleDAOImpl.
     */
    public void testSave() {
        System.out.println("save");
        Article object = a3;
        Article result = instance.save(object);
        assertEquals(a3.getTitle(), result.getTitle());
    }

    /**
     * Test of save method, of class MySQLArticleDAOImpl.
     */
    public void testSave_Update() {
        System.out.println("save");
        Article object = new Article.Builder("NewTitle1").build();
        object.setId(1L);
        Article result = instance.save(object);
        assertEquals(object.getTitle(), result.getTitle());
    }

    /**
     * Test of delete method, of class MySQLArticleDAOImpl.
     */
    public void testDelete() {
        System.out.println("delete");
        Article object = a1;
        instance.delete(object);
    }

    /**
     * Test of get method, of class MySQLArticleDAOImpl.
     */
    public void testGet_String_Empty() {
        System.out.println("get");
        String search = "";
        List<Article> result = instance.get(search);
        assertEquals(2, result.size());
    }

    /**
     * Test of get method, of class MySQLArticleDAOImpl.
     */
    public void testGet_String_Not_Found() {
        System.out.println("get");
        String search = "TTT";
        List<Article> result = instance.get(search);
        assertEquals(0, result.size());
    }

    /**
     * Test of get method, of class MySQLArticleDAOImpl.
     */
    public void testGet_String() {
        System.out.println("get");
        String search = "Title1";
        List<Article> result = instance.get(search);
        assertEquals(1, result.size());
    }

    /**
     * Test of get method, of class MySQLArticleDAOImpl.
     */
    public void testGet_long() {
        System.out.println("get");
        long id = 1L;
        Article expResult = a1;
        Article result = instance.get(id);
        assertEquals(expResult.getTitle(), result.getTitle());
    }

    /**
     * Test of get method, of class MySQLArticleDAOImpl.
     */
    public void testGet_Page() {
        System.out.println("get");
        Page page = new Page();
        Page<Article> result = instance.get(page);
        assertEquals(2, result.getObjects().size());
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
     * {@inheritDoc}
     */
    @Override
    protected IDataSet getDataSet() throws Exception {
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        IDataSet dataSet = builder.build(new File(SAMPLE_TEST_XML));
        return dataSet;
    }
}
