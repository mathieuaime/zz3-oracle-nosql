/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import daos.AEteEcritDAO;
import daos.AuthorDAO;
import entities.AEcrit;
import entities.AEteEcrit;
import entities.Article;
import entities.Author;
import entities.Author;
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
import ws.AuthorWS;
import ws.RestResponse;

/**
 *
 * @author Mathieu
 */
public class AuthorWSTest extends TestCase {
    
    AuthorWS ws = new AuthorWS();
    ArticleWS wsArticle = new ArticleWS();
    Article articleA = new Article(1, "Titre", "resume", 12);
    Article articleB = new Article(1, "Titre2", "resume2", 10);
    Article articleC = new Article(2, "Titre3", "resume3", 11);
    
    Author auteurA = new Author(1, "aimé", "mathieu", "cannes", "4444", "4422", "mathieu@isima.fr");
    Author auteurB = new Author(1, "a", "m", "clermont", "1111", "2222", "aime@isima.fr");

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
        RestResponse<Author> response = ws.addAuteur(auteurA);
        
        assertEquals(response.getStatus(), "200");
        
        ws.addAuteur(auteurA);
        response = ws.addAuteur(auteurA);
        
        //double ajout
        assertEquals(response.getStatus(), "300");
    }
    
    @Test
    public void testGet() {
        RestResponse<Author> response = ws.getAuteur(-1);
        
        //auteur inexistant
        assertEquals(response.getStatus(), "400");
        
        ws.addAuteur(auteurA);
        response = ws.getAuteur(1);
        assertEquals(response.getStatus(), "200");
        assertEquals(response.getObjectList().get(0), auteurA);
    }
    
    @Test
    public void testUpdate() {
        
        RestResponse<Author> response = ws.updateAuteur(1, auteurA);
        
        //auteur inexistant
        assertEquals(response.getStatus(), "400");
        
        ws.addAuteur(auteurA);
        response = ws.updateAuteur(1, auteurB);
        
        assertEquals(response.getStatus(), "200");
        
        response = ws.getAuteur(1);
        
        assertEquals(response.getObjectList().get(0), auteurB);
    }
    
    @Test
    public void testDelete() {
        RestResponse<Author> response = ws.deleteAuteur(-1);
        
        //auteur inexistant
        assertEquals(response.getStatus(), "400");
        
        ws.addAuteur(auteurA);
        response = ws.deleteAuteur(1);
        assertEquals(response.getStatus(), "200");
    }
    
    //CRUD auteur
    
    @Test
    public void testAddArticle() throws ParseException {
        AEcrit ae = new AEcrit(auteurA.getNom(), 1, articleA.getId());
        RestResponse<AEcrit> response = ws.addArticle(auteurA.getId(), ae);
        
        //auteur inexistant
        assertEquals(response.getStatus(), "400");
        
        ws.addAuteur(auteurA);
        response = ws.addArticle(auteurA.getNom(), ae);
        
        //article inexistant
        assertEquals(response.getStatus(), "401");
        
        wsArticle.addArticle(articleA);
        response = ws.addArticle(auteurA.getId(), ae);
        
        //ajout
        assertEquals(response.getStatus(), "200");
        
        response = ws.addArticle(auteurA.getNom(), ae);
        
        //double ajout
        assertEquals(response.getStatus(), "302");
    }
    
    @Test
    public void testGetArticle() throws ParseException {
        
        RestResponse<Article> response = ws.listArticle(auteurA.getId());
        
        //auteur inexistant
        assertEquals(response.getStatus(), "400");
        
        ws.addAuteur(auteurA);
        wsArticle.addArticle(articleA);
        
        AEcrit aEcrit = new AEcrit(auteurA.getNom(), 1, articleA.getId());
        
        ws.addArticle(auteurA.getId(), aEcrit);
        
        response = ws.listArticle(auteurA.getNom());
        
        assertEquals(response.getStatus(), "200");
        assertEquals(response.getObjectList().get(0), articleA);
        
    }
    
    @Test
    public void testUpdateArticle() throws ParseException {
        AEcrit aEcrit1 = new AEcrit(auteurA.getNom(), 1, articleA.getId());
        AEcrit aEcrit2 = new AEcrit(auteurA.getNom(), 2, articleC.getId());
        
        RestResponse<AEcrit> responseUpdate = ws.updateArticle(auteurA.getId(), articleA.getId(), aEcrit1);
        
        //auteur inexistant
        assertEquals(responseUpdate.getStatus(), "400");
        
        ws.addAuteur(auteurA);
        wsArticle.addArticle(articleA);
        
        responseUpdate = ws.updateArticle(auteurA.getId(), articleA.getId(), aEcrit2);
        
        //relation AEcrit inconnue
        assertEquals(responseUpdate.getStatus(), "402");
        
        ws.addArticle(auteurA.getNom(), aEcrit1);
        
        responseUpdate = ws.updateArticle(auteurA.getId(), articleA.getId(), aEcrit2);
        
        //nouvel article inexistant
        assertEquals(responseUpdate.getStatus(), "401");
        
        wsArticle.addArticle(articleC);
        
        responseUpdate = ws.updateArticle(auteurA.getId(), articleA.getId(), aEcrit2);
        
        RestResponse<Article> response = ws.listArticle(auteurA.getId());
        
        assertEquals(responseUpdate.getStatus(), "200");
        assertEquals(response.getObjectList().get(0), articleC);
    }
    
    @Test
    public void testDeleteArticle() throws ParseException {
                
        AEcrit aEcrit1 = new AEcrit(auteurA.getNom(), 1, articleA.getId());
        
        RestResponse<AEcrit> response = ws.deleteArticle(auteurA.getId(), articleA.getId());
        
        //auteur inexistant
        assertEquals(response.getStatus(), "400");
        
        ws.addAuteur(auteurA);
        wsArticle.addArticle(articleA);
        
        response = ws.deleteArticle(auteurA.getId(), articleA.getId());
        
        //relation AEcrit inconnue
        assertEquals(response.getStatus(), "402");
        
        ws.addArticle(auteurA.getNom(), aEcrit1);
        
        response = ws.deleteArticle(auteurA.getId(), articleA.getId());
        
        assertEquals(response.getStatus(), "200");
    }
}
