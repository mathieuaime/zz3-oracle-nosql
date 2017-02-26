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
import entities.Laboratoire;
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
import ws.LaboratoryWS;
import ws.RestResponse;

/**
 *
 * @author Mathieu
 */
public class LaboratoryWSTest extends TestCase {

    LaboratoryWS ws = new LaboratoryWS();

    AuthorWS wsAuthor = new AuthorWS();
    ArticleWS wsArticle = new ArticleWS();

    Laboratoire laboA = new Laboratoire(1, "laboA", "adresseA");
    Laboratoire laboB = new Laboratoire(1, "laboB", "adresseB");

    Author auteurA = new Author(1, "Nom1", "Prénom1", "Adresse1", "Tel1", "Fax1", "mail1");

    Article articleA = new Article(1, "Titre", "Resume", 12);

    EstRattache estRattacheA = new EstRattache(auteurA.getNom(), Universite.MAJOR_KEY, 1, laboA.getLaboratoireId());

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
        RestResponse<Laboratoire> response = ws.addLaboratoire(laboA);

        assertEquals(response.getResponseCode(), "201");

        response = ws.addLaboratoire(laboA);

        //double ajout
        assertEquals(response.getResponseCode(), "409");
        assertEquals(response.getCode(), 104);
    }

    @Test
    public void testGet() throws ParseException {
        RestResponse<Laboratoire> response = ws.getLaboratoire(-1);

        //laboratoire inexistant
        assertEquals(response.getResponseCode(), "404");
        assertEquals(response.getCode(), 154);

        ws.addLaboratoire(laboA);
        response = ws.getLaboratoire(1);
        assertEquals(response.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), laboA);
    }

    @Test
    public void testUpdate() throws ParseException {

        RestResponse<Laboratoire> response = ws.updateLaboratoire(1, laboA);

        //laboratoire inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 154);

        ws.addLaboratoire(laboA);

        response = ws.updateLaboratoire(1, laboB);

        assertEquals(response.getResponseCode(), "200");

        response = ws.getLaboratoire(1);

        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), laboB);
    }

    @Test
    public void testDelete() throws ParseException {
        RestResponse<Laboratoire> response = ws.deleteLaboratoire(-1);

        //laboratoire inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 154);

        ws.addLaboratoire(laboA);
        response = ws.deleteLaboratoire(1);
        assertEquals(response.getResponseCode(), "200");
    }

    @Test
    public void testGetAuteur() {
        RestResponse<Author> response = ws.listAuteur(laboA.getLaboratoireId());

        //université inexistante
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 154);

        ws.addLaboratoire(laboA);
        wsAuthor.addAuteur(auteurA);

        wsAuthor.addLaboratories(auteurA.getId(), estRattacheA);

        response = ws.listAuteur(laboA.getLaboratoireId());

        assertEquals(response.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), auteurA);
    }

    @Test
    public void testGetArticle() throws ParseException {
        RestResponse<Article> response = ws.listArticle(laboA.getLaboratoireId());

        //université inexistante
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 154);

        ws.addLaboratoire(laboA);
        wsAuthor.addAuteur(auteurA);
        wsArticle.addArticle(articleA);

        wsAuthor.addArticle(auteurA.getId(), aEcrit);
        wsArticle.addAuteur(articleA.getId(), aEteEcrit);

        wsAuthor.addLaboratories(auteurA.getId(), estRattacheA);

        response = ws.listArticle(laboA.getLaboratoireId());

        assertEquals(response.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), articleA);
    }
}
