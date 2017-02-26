/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import daos.AuthorDAO;
import entities.AEcrit;
import entities.AEteEcrit;
import entities.Article;
import entities.Author;
import entities.EstRattache;
import entities.Universite;
import java.text.ParseException;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ws.ArticleWS;
import ws.AuthorWS;
import ws.UniversityWS;
import ws.RestResponse;

/**
 *
 * @author Mathieu
 */
public class UniversityWSTest extends TestCase {

    UniversityWS ws = new UniversityWS();
    AuthorWS wsAuthor = new AuthorWS();
    ArticleWS wsArticle = new ArticleWS();

    Universite univA = new Universite(1, "univA", "adresseA");
    Universite univB = new Universite(1, "univB", "adresseB");

    Author auteurA = new Author(1, "Nom1", "Prénom1", "Adresse1", "Tel1", "Fax1", "mail1");

    Article articleA = new Article(1, "Titre", "Resume", 12);

    EstRattache estRattacheA = new EstRattache(auteurA.getNom(), Universite.MAJOR_KEY, 1, univA.getUniversiteId());

    AEcrit aEcrit = new AEcrit(auteurA.getNom(), 1, articleA.getId());
    AEteEcrit aEteEcrit = new AEteEcrit(articleA.getTitre(), auteurA.getId());

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

        assertEquals(response.getResponseCode(), "201");

        response = ws.addUniversite(univA);

        //double ajout
        assertEquals(response.getResponseCode(), "409");
        assertEquals(response.getCode(), 105);
    }

    @Test
    public void testGet() {
        RestResponse<Universite> response = ws.getUniversite(-1);

        //universite inexistant
        assertEquals(response.getResponseCode(), "404");
        assertEquals(response.getCode(), 155);

        ws.addUniversite(univA);
        response = ws.getUniversite(1);
        assertEquals(response.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), univA);
    }

    @Test
    public void testUpdate() {

        RestResponse<Universite> response = ws.updateUniversite(1, univA);

        //universite inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 155);

        ws.addUniversite(univA);

        response = ws.updateUniversite(1, univB);

        assertEquals(response.getResponseCode(), "200");

        response = ws.getUniversite(1);

        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), univB);
    }

    @Test
    public void testDelete() {
        RestResponse<Universite> response = ws.deleteUniversite(-1);

        //universite inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 155);

        ws.addUniversite(univA);
        response = ws.deleteUniversite(1);
        assertEquals(response.getResponseCode(), "200");
    }

    @Test
    public void testGetAuteur() {
        RestResponse<Author> response = ws.listAuteur(univA.getUniversiteId());

        //université inexistante
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 155);

        ws.addUniversite(univA);
        wsAuthor.addAuteur(auteurA);

        wsAuthor.addUniversities(auteurA.getId(), estRattacheA);

        response = ws.listAuteur(univA.getUniversiteId());

        assertEquals(response.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), auteurA);
    }

    @Test
    public void testGetArticle() throws ParseException {
        RestResponse<Article> response = ws.listArticle(univA.getUniversiteId());

        //université inexistante
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 155);

        ws.addUniversite(univA);
        wsAuthor.addAuteur(auteurA);
        wsArticle.addArticle(articleA);

        wsAuthor.addArticle(auteurA.getId(), aEcrit);
        wsArticle.addAuteur(articleA.getId(), aEteEcrit);

        wsAuthor.addUniversities(auteurA.getId(), estRattacheA);

        response = ws.listArticle(univA.getUniversiteId());

        assertEquals(response.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), articleA);
    }
}
