/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import daos.AEteEcritDAO;
import daos.AutorDAO;
import entities.AEteEcrit;
import entities.Article;
import entities.Autor;
import java.text.ParseException;
import java.util.List;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ws.ArticleWS;
import ws.AutorWS;
import ws.RestResponse;

/**
 *
 * @author Mathieu
 */
public class ArticleWSTest extends TestCase {
    
    ArticleWS ws = new ArticleWS();
    AutorWS wsAutor = new AutorWS();
    Article articleA = new Article(1, "Titre", "resume", 12);;
    Article articleB = new Article(1, "Titre2", "resume2", 10);;
    
    Autor auteurA = new Autor(1, "aimé", "mathieu", "cannes", "4444", "4422", "mathieu@isima.fr");
    Autor auteurB = new Autor(1, "a", "m", "clermont", "1111", "2222", "aime@isima.fr");
    Autor auteurC = new Autor(2, "a", "m", "clermont", "1111", "2222", "aime@isima.fr");

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    @Override
    public void setUp() throws Exception {
        AutorDAO adao = new AutorDAO();
        adao.deleteAll();
    }

    @After
    @Override
    public void tearDown() throws Exception {
    }
    
    //CRUD auteur
    
    @Test
    public void testAdd() {
        RestResponse<Article> response = ws.addArticle(articleA);
        
        assertEquals(response.getStatus(), "200");
        
        ws.addArticle(articleA);
        response = ws.addArticle(articleA);
        
        //double ajout
        assertEquals(response.getStatus(), "301");
    }
    
    @Test
    public void testGet() throws ParseException {
        RestResponse<Article> response = ws.getArticle(-1);
        
        //article inexistant
        assertEquals(response.getStatus(), "401");
        
        ws.addArticle(articleA);
        response = ws.getArticle(1);
        assertEquals(response.getStatus(), "200");
        assertEquals(response.getObjectList().get(0), articleA);
    }
    
    @Test
    public void testUpdate() throws ParseException {
        
        RestResponse<Article> response = ws.updateArticle(1, articleA);
        
        //article inexistant
        assertEquals(response.getStatus(), "401");
        
        ws.addArticle(articleA);
        response = ws.updateArticle(1, articleB);
        
        assertEquals(response.getStatus(), "200");
        
        response = ws.getArticle(1);
        
        assertEquals(response.getObjectList().get(0), articleB);
    }
    
    @Test
    public void testDelete() throws ParseException {
        RestResponse<Article> response = ws.deleteArticle(-1);
        
        //article inexistant
        assertEquals(response.getStatus(), "401");
        
        ws.addArticle(articleA);
        response = ws.deleteArticle(1);
        assertEquals(response.getStatus(), "200");
    }
    
    //CRUD auteur
    
    @Test
    public void testAddAutor() throws ParseException {
        AEteEcrit ae = new AEteEcrit(articleA.getTitre(), auteurA.getId());
        RestResponse<AEteEcrit> response = ws.addAuteur(articleA.getId(), ae);
        
        //livre inexistant
        assertEquals(response.getStatus(), "401");
        
        ws.addArticle(articleA);
        response = ws.addAuteur(articleA.getTitre(), ae);
        
        //auteur inexistant
        assertEquals(response.getStatus(), "400");
        
        wsAutor.addAuteur(auteurA);
        response = ws.addAuteur(articleA.getId(), ae);
        
        //ajout
        assertEquals(response.getStatus(), "200");
        
        response = ws.addAuteur(articleA.getTitre(), ae);
        
        //double ajout
        assertEquals(response.getStatus(), "303");
    }
    
    @Test
    public void testGetAutor() throws ParseException {
        
        RestResponse<Autor> response = ws.listAuteur(articleA.getId());
        
        //livre inexistant
        assertEquals(response.getStatus(), "401");
        
        ws.addArticle(articleA);
        wsAutor.addAuteur(auteurA);
        
        AEteEcrit aEteEcrit = new AEteEcrit(articleA.getTitre(), auteurA.getId());
        
        ws.addAuteur(articleA.getId(), aEteEcrit);
        
        response = ws.listAuteur(articleA.getTitre());
        
        assertEquals(response.getStatus(), "200");
        assertEquals(response.getObjectList().get(0), auteurA);
        
    }
    
    @Test
    public void testUpdateAutor() throws ParseException {
        AEteEcrit aEteEcrit1 = new AEteEcrit(articleA.getTitre(), auteurA.getId());
        AEteEcrit aEteEcrit2 = new AEteEcrit(articleA.getTitre(), auteurC.getId());
        
        RestResponse<AEteEcrit> responseUpdate = ws.updateAuteur(articleA.getId(), auteurA.getId(), aEteEcrit2);
        
        //article inexistant
        assertEquals(responseUpdate.getStatus(), "401");
        
        ws.addArticle(articleA);
        wsAutor.addAuteur(auteurA);
        
        responseUpdate = ws.updateAuteur(articleA.getId(), auteurA.getId(), aEteEcrit2);
        
        //relation AEteEcrit inconnue
        assertEquals(responseUpdate.getStatus(), "403");
        
        ws.addAuteur(articleA.getTitre(), aEteEcrit1);
        
        responseUpdate = ws.updateAuteur(articleA.getId(), auteurA.getId(), aEteEcrit2);
        
        //nouvel auteur inexistant
        assertEquals(responseUpdate.getStatus(), "400");
        
        wsAutor.addAuteur(auteurC);
        
        responseUpdate = ws.updateAuteur(articleA.getId(), auteurA.getId(), aEteEcrit2);
        
        RestResponse<Autor> response = ws.listAuteur(articleA.getId());
        
        assertEquals(responseUpdate.getStatus(), "200");
        assertEquals(response.getObjectList().get(0), auteurC);
    }
    
    @Test
    public void testDeleteAutor() throws ParseException {
        RestResponse<AEteEcrit> response = ws.deleteAuteur(articleA.getId(), auteurA.getId());
        
        //article inexistant
        assertEquals(response.getStatus(), "401");
        
        ws.addArticle(articleA);
        wsAutor.addAuteur(auteurA);
        
        response = ws.deleteAuteur(articleA.getId(), auteurA.getId());
        
        //relation AEteEcrit inconnue
        assertEquals(response.getStatus(), "403");
        
        wsAutor.addAuteur(auteurC);
        
        AEteEcrit aEteEcrit1 = new AEteEcrit(articleA.getTitre(), auteurA.getId());
        
        ws.addAuteur(articleA.getId(), aEteEcrit1);
        
        RestResponse<AEteEcrit> responseUpdate = ws.deleteAuteur(articleA.getId(), auteurA.getId());
        
        assertEquals(responseUpdate.getStatus(), "200");
    }
}
