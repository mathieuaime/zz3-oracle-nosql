/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import daos.AuthorDAO;
import entities.AEteEcrit;
import entities.Article;
import entities.Author;
import entities.Keyword;
import java.text.ParseException;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ws.ArticleWS;
import ws.AuthorWS;
import ws.RestResponse;

/**
 *
 * @author Mathieu
 */
public class ArticleWSTest extends TestCase {

    ArticleWS ws = new ArticleWS();
    AuthorWS wsAuthor = new AuthorWS();
    Article articleA = new Article(1, "Titre", "resume", 12);
    Article articleB = new Article(1, "Titre2", "resume2", 10);

    Author auteurA = new Author(1, "aimé", "mathieu", "cannes", "4444", "4422", "mathieu@isima.fr");
    Author auteurB = new Author(1, "a", "m", "clermont", "1111", "2222", "aime@isima.fr");
    Author auteurC = new Author(2, "a", "m", "clermont", "1111", "2222", "aime@isima.fr");

    AEteEcrit aEteEcrit1 = new AEteEcrit(articleA.getTitre(), 1, auteurA.getId());
    AEteEcrit aEteEcrit2 = new AEteEcrit(articleA.getTitre(), 1, auteurC.getId());

    Keyword keywordA = new Keyword("keywordA", 1, 1);
    Keyword keywordB = new Keyword("keywordB", 1, 1);

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
        RestResponse<Article> response = ws.addArticle(articleA);

        assertEquals(response.getResponseCode(), "201");

        response = ws.addArticle(articleA);

        //double ajout
        assertEquals(response.getResponseCode(), "409");
        assertEquals(response.getCode(), 101);
    }

    @Test
    public void testGet() throws ParseException {
        RestResponse<Article> response = ws.getArticle(-1);

        //article inexistant
        assertEquals(response.getResponseCode(), "404");
        assertEquals(response.getCode(), 151);

        ws.addArticle(articleA);
        response = ws.getArticle(1);
        assertEquals(response.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), articleA);
    }

    @Test
    public void testUpdate() throws ParseException {

        RestResponse<Article> response = ws.updateArticle(1, articleA);

        //article inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 151);

        ws.addArticle(articleA);
        response = ws.updateArticle(1, articleB);

        assertEquals(response.getResponseCode(), "200");

        response = ws.getArticle(1);

        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), articleB);
    }

    @Test
    public void testDelete() throws ParseException {
        RestResponse<Article> response = ws.deleteArticle(-1);

        //article inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 151);

        ws.addArticle(articleA);
        response = ws.deleteArticle(1);
        assertEquals(response.getResponseCode(), "200");
    }

    //CRUD auteur
    @Test
    public void testAddAuthor() throws ParseException {
        RestResponse<AEteEcrit> response = ws.addAuteur(articleA.getId(), aEteEcrit1);

        //livre inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 151);

        ws.addArticle(articleA);
        response = ws.addAuteur(articleA.getTitre(), aEteEcrit1);

        //auteur inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 150);

        wsAuthor.addAuteur(auteurA);
        response = ws.addAuteur(articleA.getId(), aEteEcrit1);

        //ajout
        assertEquals(response.getResponseCode(), "201");

        response = ws.addAuteur(articleA.getTitre(), aEteEcrit1);

        //double ajout
        assertEquals(response.getResponseCode(), "409");
        assertEquals(response.getCode(), 103);
    }

    @Test
    public void testGetAuthor() throws ParseException {

        RestResponse<Author> response = ws.listAuteur(articleA.getId());

        //livre inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 151);

        ws.addArticle(articleA);
        wsAuthor.addAuteur(auteurA);

        ws.addAuteur(articleA.getId(), aEteEcrit1);

        response = ws.listAuteur(articleA.getTitre());

        assertEquals(response.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), auteurA);

    }

    @Test
    public void testUpdateAuthor() throws ParseException {
        RestResponse<AEteEcrit> responseUpdate = ws.updateAuteur(articleA.getId(), auteurA.getId(), aEteEcrit2);

        //article inexistant
        assertEquals(responseUpdate.getResponseCode(), "204");
        assertEquals(responseUpdate.getCode(), 151);

        ws.addArticle(articleA);
        wsAuthor.addAuteur(auteurA);

        responseUpdate = ws.updateAuteur(articleA.getId(), auteurA.getId(), aEteEcrit2);

        //relation AEteEcrit inconnue
        assertEquals(responseUpdate.getResponseCode(), "204");
        assertEquals(responseUpdate.getCode(), 153);

        ws.addAuteur(articleA.getTitre(), aEteEcrit1);

        responseUpdate = ws.updateAuteur(articleA.getId(), auteurA.getId(), aEteEcrit2);

        //nouvel auteur inexistant
        assertEquals(responseUpdate.getResponseCode(), "204");
        assertEquals(responseUpdate.getCode(), 150);

        wsAuthor.addAuteur(auteurC);

        responseUpdate = ws.updateAuteur(articleA.getId(), auteurA.getId(), aEteEcrit2);

        RestResponse<Author> response = ws.listAuteur(articleA.getId());

        assertEquals(responseUpdate.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), auteurC);
    }

    @Test
    public void testDeleteAuthor() throws ParseException {
        RestResponse<AEteEcrit> response = ws.deleteAuteur(articleA.getId(), auteurA.getId());

        //article inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 151);

        ws.addArticle(articleA);
        wsAuthor.addAuteur(auteurA);

        response = ws.deleteAuteur(articleA.getId(), auteurA.getId());

        //relation AEteEcrit inconnue
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 153);

        wsAuthor.addAuteur(auteurC);

        ws.addAuteur(articleA.getId(), aEteEcrit1);

        RestResponse<AEteEcrit> responseUpdate = ws.deleteAuteur(articleA.getId(), auteurA.getId());

        assertEquals(responseUpdate.getResponseCode(), "200");
    }

    @Test
    public void testAddKeyword() throws ParseException {
        RestResponse<Keyword> response = ws.addKeywords(articleA.getId(), keywordA);

        //livre inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 151);

        ws.addArticle(articleA);

        response = ws.addKeywords(articleA.getId(), keywordA);

        //ajout
        assertEquals(response.getResponseCode(), "201");

        response = ws.addKeywords(articleA.getId(), keywordA);

        //double ajout
        assertEquals(response.getResponseCode(), "409");
        assertEquals(response.getCode(), 107);
    }

    @Test
    public void testGetKeyword() throws ParseException {
        RestResponse<String> response = ws.listKeywords(articleA.getId());

        //livre inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 151);

        ws.addArticle(articleA);

        ws.addKeywords(articleA.getId(), keywordA);

        response = ws.listKeywords(articleA.getId());

        assertEquals(response.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), keywordA.getKeyword());
    }

    @Test
    public void testUpdateKeyword() throws ParseException {
        RestResponse<Keyword> responseUpdate = ws.updateKeywords(articleA.getId(), keywordA.getKeyword(), 1, keywordB);

        //article inexistant
        assertEquals(responseUpdate.getResponseCode(), "204");
        assertEquals(responseUpdate.getCode(), 151);

        ws.addArticle(articleA);

        responseUpdate = ws.updateKeywords(articleA.getId(), keywordA.getKeyword(), 1, keywordB);

        //keyword inconnu
        assertEquals(responseUpdate.getResponseCode(), "204");
        assertEquals(responseUpdate.getCode(), 157);

        ws.addKeywords(articleA.getId(), keywordA);

        responseUpdate = ws.updateKeywords(articleA.getId(), keywordA.getKeyword(), 1, keywordB);

        RestResponse<String> response = ws.listKeywords(articleA.getId());

        assertEquals(responseUpdate.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), keywordB.getKeyword());
    }

    @Test
    public void testDeleteKeyword() throws ParseException {
        RestResponse<Keyword> response = ws.deleteKeywords(articleA.getId(), keywordA.getKeyword(), 1);

        //article inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 151);

        ws.addArticle(articleA);

        response = ws.deleteKeywords(articleA.getId(), keywordA.getKeyword(), 1);

        //keyword inconnu
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 157);

        ws.addKeywords(articleA.getId(), keywordA);

        response = ws.deleteKeywords(articleA.getId(), keywordA.getKeyword(), 1);

        assertEquals(response.getResponseCode(), "200");
    }
}
