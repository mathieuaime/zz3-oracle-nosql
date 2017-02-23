/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import daos.AEteEcritDAO;
import daos.AuthorDAO;
import entities.AEteEcrit;
import entities.Article;
import entities.Author;
import entities.Keyword;
import entities.Laboratoire;
import java.text.ParseException;
import java.util.List;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ws.ArticleWS;
import ws.AuthorWS;
import ws.LaboratoryWS;
import ws.RestResponse;

/**
 *
 * @author Mathieu
 */
public class LaboratoryWSTest extends TestCase {
    
    LaboratoryWS ws = new LaboratoryWS();
    
    Laboratoire laboA = new Laboratoire(1, "laboA", "adresseA");
    Laboratoire laboB = new Laboratoire(1, "laboB", "adresseB");

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    @Override
    public void setUp() throws Exception {
        AuthorDAO adao = new AuthorDAO();
        adao.deleteAll();
    }

    @After
    @Override
    public void tearDown() throws Exception {
    }
    
    //CRUD auteur
    
    @Test
    public void testAdd() {
        RestResponse<Laboratoire> response = ws.addLaboratoire(laboA);
        
        assertEquals(response.getStatus(), "200");
        
        response = ws.addLaboratoire(laboA);
        
        //double ajout
        assertEquals(response.getStatus(), "304");
    }
    
    @Test
    public void testGet() throws ParseException {
        RestResponse<Laboratoire> response = ws.getLaboratoire(-1);
        
        //laboratoire inexistant
        assertEquals(response.getStatus(), "404");
        
        ws.addLaboratoire(laboA);
        response = ws.getLaboratoire(1);
        assertEquals(response.getStatus(), "200");
        assertEquals(response.getObjectList().get(0), laboA);
    }
    
    @Test
    public void testUpdate() throws ParseException {
        
        RestResponse<Laboratoire> response = ws.updateLaboratoire(1, laboA);
        
        //laboratoire inexistant
        assertEquals(response.getStatus(), "404");
        
        ws.addLaboratoire(laboA);
        
        response = ws.updateLaboratoire(1, laboB);
        
        assertEquals(response.getStatus(), "200");
        
        response = ws.getLaboratoire(1);
        
        assertEquals(response.getObjectList().get(0), laboB);
    }
    
    @Test
    public void testDelete() throws ParseException {
        RestResponse<Laboratoire> response = ws.deleteLaboratoire(-1);
        
        //laboratoire inexistant
        assertEquals(response.getStatus(), "404");
        
        ws.addLaboratoire(laboA);
        response = ws.deleteLaboratoire(1);
        assertEquals(response.getStatus(), "200");
    }
}
