/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import daos.AuthorDAO;
import entities.Universite;
import java.text.ParseException;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ws.UniversityWS;
import ws.RestResponse;

/**
 *
 * @author Mathieu
 */
public class UniversityWSTest extends TestCase {
    
    UniversityWS ws = new UniversityWS();
    
    Universite univA = new Universite(1, "univA", "adresseA");
    Universite univB = new Universite(1, "univB", "adresseB");

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
        RestResponse<Universite> response = ws.addUniversite(univA);
        
        assertEquals(response.getStatus(), "200");
        
        response = ws.addUniversite(univA);
        
        //double ajout
        assertEquals(response.getStatus(), "305");
    }
    
    @Test
    public void testGet() throws ParseException {
        RestResponse<Universite> response = ws.getUniversite(-1);
        
        //universite inexistant
        assertEquals(response.getStatus(), "405");
        
        ws.addUniversite(univA);
        response = ws.getUniversite(1);
        assertEquals(response.getStatus(), "200");
        assertEquals(response.getObjectList().get(0), univA);
    }
    
    @Test
    public void testUpdate() throws ParseException {
        
        RestResponse<Universite> response = ws.updateUniversite(1, univA);
        
        //universite inexistant
        assertEquals(response.getStatus(), "405");
        
        ws.addUniversite(univA);
        
        response = ws.updateUniversite(1, univB);
        
        assertEquals(response.getStatus(), "200");
        
        response = ws.getUniversite(1);
        
        assertEquals(response.getObjectList().get(0), univB);
    }
    
    @Test
    public void testDelete() throws ParseException {
        RestResponse<Universite> response = ws.deleteUniversite(-1);
        
        //universite inexistant
        assertEquals(response.getStatus(), "405");
        
        ws.addUniversite(univA);
        response = ws.deleteUniversite(1);
        assertEquals(response.getStatus(), "200");
    }
}
