/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import daos.AutorDAO;
import entities.Autor;
import java.util.List;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ws.AutorWS;
import ws.RestResponse;

/**
 *
 * @author Mathieu
 */
public class AutorWSTest extends TestCase {
    
    AutorWS ws = new AutorWS();

    @BeforeClass
    public static void setUpClass() throws Exception {
        AutorDAO adao = new AutorDAO();
        adao.deleteAll();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        AutorDAO adao = new AutorDAO();
        adao.deleteAll();
    }

    @Before
    @Override
    public void setUp() throws Exception {
        RestResponse<Autor> all = ws.getAll();
        for(Autor r : all.getObjectList()) {
            ws.deleteAuteur(r.getId());
        }
    }

    @After
    @Override
    public void tearDown() throws Exception {
        RestResponse<Autor> all = ws.getAll();
        for(Autor r : all.getObjectList()) {
            ws.deleteAuteur(r.getId());
        }
    }
    
    //CRUD auteur
    
    @Test
    public void testAdd() {
        Autor a = new Autor(1, "aimé", "mathieu", "cannes", "4444", "4422", "mathieu@isima.fr");
        RestResponse<Autor> response = ws.addAuteur(a);
        assertEquals(response.getStatus(), "200");
    }
    
    @Test
    public void testAddError() {
        Autor a = new Autor(1, "aimé", "mathieu", "cannes", "4444", "4422", "mathieu@isima.fr");
        ws.addAuteur(a);
        RestResponse<Autor> response = ws.addAuteur(a);
        assertEquals(response.getStatus(), "300");
    }
    
    @Test
    public void testGet() {
        Autor a = new Autor(1, "aimé", "mathieu", "cannes", "4444", "4422", "mathieu@isima.fr");
        ws.addAuteur(a);
        RestResponse<Autor> response = ws.getAuteur(1);
        assertEquals(response.getStatus(), "200");
        assertEquals(response.getObjectList().get(0), a);
    }
    
    @Test
    public void testGetError() {
        RestResponse<Autor> response = ws.getAuteur(-1);
        assertEquals(response.getStatus(), "400");
    }
    
    @Test
    public void testUpdate() {
        Autor a = new Autor(1, "aimé", "mathieu", "cannes", "4444", "4422", "mathieu@isima.fr");
        Autor b = new Autor(1, "a", "m", "clermont", "1111", "2222", "aime@isima.fr");
        ws.addAuteur(a);
        RestResponse<Autor> response = ws.updateAuteur(1, b);
        assertEquals(response.getStatus(), "200");
        response = ws.getAuteur(1);
        assertEquals(response.getObjectList().get(0), b);
    }
    
    @Test
    public void testUpdateError() {
        Autor b = new Autor(1, "a", "m", "clermont", "1111", "2222", "aime@isima.fr");
        RestResponse<Autor> response = ws.updateAuteur(1, b);
        assertEquals(response.getStatus(), "400");
    }
    
    @Test
    public void testDelete() {
        Autor a = new Autor(1, "aimé", "mathieu", "cannes", "4444", "4422", "mathieu@isima.fr");
        ws.addAuteur(a);
        RestResponse<Autor> response = ws.deleteAuteur(1);
        assertEquals(response.getStatus(), "200");
    }
    
    @Test
    public void testDeleteError() {
        RestResponse<Autor> response = ws.deleteAuteur(-1);
        assertEquals(response.getStatus(), "400");
    }
    
    //CRUD article
    
    @Test
    public void testAddArticle() {
        //TODO
    }
    
    @Test
    public void testAddArticleError() {
        //TODO
    }
    
    @Test
    public void testGetArticle() {
        //TODO
    }
    
    @Test
    public void testGetArticleError() {
        //TODO
    }
    
    @Test
    public void testUpdateArticle() {
        //TODO
    }
    
    @Test
    public void testUpdateArticleError() {
        //TODO
    }
    
    @Test
    public void testDeleteArticle() {
        //TODO
    }
    
    @Test
    public void testDeleteArticleError() {
        //TODO
    }
}
