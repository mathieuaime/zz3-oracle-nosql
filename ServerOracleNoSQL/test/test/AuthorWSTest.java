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
import entities.Keyword;
import entities.Laboratoire;
import entities.Rattache;
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
import ws.UniversityWS;

/**
 *
 * @author Mathieu
 */
public class AuthorWSTest extends TestCase {

    AuthorWS ws = new AuthorWS();
    ArticleWS wsArticle = new ArticleWS();
    UniversityWS wsUniversite = new UniversityWS();
    LaboratoryWS wsLaboratoire = new LaboratoryWS();
    Article articleA = new Article(1, "Titre", "resume", 12);
    Article articleB = new Article(1, "Titre2", "resume2", 10);
    Article articleC = new Article(2, "Titre3", "resume3", 11);

    Author auteurA = new Author(1, "aimé", "mathieu", "cannes", "4444", "4422", "mathieu@isima.fr");
    Author auteurB = new Author(1, "a", "m", "clermont", "1111", "2222", "aime@isima.fr");

    EstRattache estRattacheA = new EstRattache(auteurA.getNom(), Universite.MAJOR_KEY, 1, 1);
    EstRattache estRattacheB = new EstRattache(auteurA.getNom(), Universite.MAJOR_KEY, 2, 2);

    EstRattache estRattacheC = new EstRattache(auteurA.getNom(), Laboratoire.MAJOR_KEY, 1, 1);
    EstRattache estRattacheD = new EstRattache(auteurA.getNom(), Laboratoire.MAJOR_KEY, 2, 2);

    Universite universiteA = new Universite(1, "Universite1", "Adresse1");
    Universite universiteB = new Universite(2, "Universite2", "Adresse2");

    Laboratoire laboratoireA = new Laboratoire(1, "Laboratoire1", "Adresse1");
    Laboratoire laboratoireB = new Laboratoire(2, "Laboratoire2", "Adresse2");

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

        assertEquals(response.getResponseCode(), "201");

        response = ws.addAuteur(auteurA);

        //double ajout
        assertEquals(response.getResponseCode(), "409");
        assertEquals(response.getCode(), 100);
    }

    @Test
    public void testGet() {
        RestResponse<Author> response = ws.getAuteur(-1);

        //auteur inexistant
        assertEquals(response.getResponseCode(), "404");
        assertEquals(response.getCode(), 150);

        ws.addAuteur(auteurA);
        response = ws.getAuteur(1);
        assertEquals(response.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), auteurA);
    }

    @Test
    public void testUpdate() {

        RestResponse<Author> response = ws.updateAuteur(1, auteurA);

        //auteur inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 150);

        ws.addAuteur(auteurA);
        response = ws.updateAuteur(1, auteurB);

        assertEquals(response.getResponseCode(), "200");

        response = ws.getAuteur(1);

        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), auteurB);
    }

    @Test
    public void testDelete() {
        RestResponse<Author> response = ws.deleteAuteur(-1);

        //auteur inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 150);

        ws.addAuteur(auteurA);
        response = ws.deleteAuteur(1);
        assertEquals(response.getResponseCode(), "200");
    }

    //CRUD auteur
    @Test
    public void testAddArticle() throws ParseException {
        AEcrit ae = new AEcrit(auteurA.getNom(), 1, articleA.getId());
        RestResponse<AEcrit> response = ws.addArticle(auteurA.getId(), ae);

        //auteur inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 150);

        ws.addAuteur(auteurA);
        response = ws.addArticle(auteurA.getNom(), ae);

        //article inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 151);

        wsArticle.addArticle(articleA);
        response = ws.addArticle(auteurA.getId(), ae);

        //ajout
        assertEquals(response.getResponseCode(), "201");

        response = ws.addArticle(auteurA.getNom(), ae);

        //double ajout
        assertEquals(response.getResponseCode(), "409");
        assertEquals(response.getCode(), 102);
    }

    @Test
    public void testGetArticle() throws ParseException {

        RestResponse<Article> response = ws.listArticle(auteurA.getId());

        //auteur inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 150);

        ws.addAuteur(auteurA);
        wsArticle.addArticle(articleA);

        AEcrit aEcrit = new AEcrit(auteurA.getNom(), 1, articleA.getId());

        ws.addArticle(auteurA.getId(), aEcrit);

        response = ws.listArticle(auteurA.getNom());

        assertEquals(response.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), articleA);

    }

    @Test
    public void testUpdateArticle() throws ParseException {
        AEcrit aEcrit1 = new AEcrit(auteurA.getNom(), 1, articleA.getId());
        AEcrit aEcrit2 = new AEcrit(auteurA.getNom(), 2, articleC.getId());

        RestResponse<AEcrit> responseUpdate = ws.updateArticle(auteurA.getId(), articleA.getId(), aEcrit1);

        //auteur inexistant
        assertEquals(responseUpdate.getResponseCode(), "204");
        assertEquals(responseUpdate.getCode(), 150);

        ws.addAuteur(auteurA);
        wsArticle.addArticle(articleA);

        responseUpdate = ws.updateArticle(auteurA.getId(), articleA.getId(), aEcrit2);

        //relation AEcrit inconnue
        assertEquals(responseUpdate.getResponseCode(), "204");
        assertEquals(responseUpdate.getCode(), 152);

        ws.addArticle(auteurA.getNom(), aEcrit1);

        responseUpdate = ws.updateArticle(auteurA.getId(), articleA.getId(), aEcrit2);

        //nouvel article inexistant
        assertEquals(responseUpdate.getResponseCode(), "204");
        assertEquals(responseUpdate.getCode(), 151);

        wsArticle.addArticle(articleC);

        responseUpdate = ws.updateArticle(auteurA.getId(), articleA.getId(), aEcrit2);

        RestResponse<Article> response = ws.listArticle(auteurA.getId());

        assertEquals(responseUpdate.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), articleC);
    }

    @Test
    public void testDeleteArticle() throws ParseException {

        AEcrit aEcrit1 = new AEcrit(auteurA.getNom(), 1, articleA.getId());

        RestResponse<AEcrit> response = ws.deleteArticle(auteurA.getId(), articleA.getId());

        //auteur inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 150);

        ws.addAuteur(auteurA);
        wsArticle.addArticle(articleA);

        response = ws.deleteArticle(auteurA.getId(), articleA.getId());

        //relation AEcrit inconnue
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 152);

        ws.addArticle(auteurA.getNom(), aEcrit1);

        response = ws.deleteArticle(auteurA.getId(), articleA.getId());

        assertEquals(response.getResponseCode(), "200");
    }

    @Test
    public void testAddUniversite() {
        RestResponse<Rattache> response = ws.addUniversities(auteurA.getId(), estRattacheA);

        //auteur inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 150);

        ws.addAuteur(auteurA);

        response = ws.addUniversities(auteurA.getId(), estRattacheA);

        //universite inconnue
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 155);

        wsUniversite.addUniversite(universiteA);

        response = ws.addUniversities(auteurA.getId(), estRattacheA);

        //ajout
        assertEquals(response.getResponseCode(), "201");

        response = ws.addUniversities(auteurA.getId(), estRattacheA);

        //double ajout
        assertEquals(response.getResponseCode(), "409");
        assertEquals(response.getCode(), 106);
    }

    @Test
    public void testGetUniversite() {
        RestResponse<Universite> response = ws.listUniversities(auteurA.getId());

        //auteur inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 150);

        ws.addAuteur(auteurA);

        wsUniversite.addUniversite(universiteA);

        ws.addUniversities(auteurA.getId(), estRattacheA);

        response = ws.listUniversities(auteurA.getId());

        assertEquals(response.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), universiteA);
    }

    @Test
    public void testUpdateUniversite() {

        RestResponse<Universite> responseUpdate = ws.updateUniversities(auteurA.getId(), universiteA.getUniversiteId(), 1, estRattacheB);

        //auteur inexistant
        assertEquals(responseUpdate.getResponseCode(), "204");
        assertEquals(responseUpdate.getCode(), 150);

        ws.addAuteur(auteurA);

        responseUpdate = ws.updateUniversities(auteurA.getId(), universiteA.getUniversiteId(), 1, estRattacheB);

        //ancienne université inconnue
        assertEquals(responseUpdate.getResponseCode(), "204");
        assertEquals(responseUpdate.getCode(), 155);

        wsUniversite.addUniversite(universiteA);

        responseUpdate = ws.updateUniversities(auteurA.getId(), universiteA.getUniversiteId(), 1, estRattacheB);

        //nouvelle université inconnue
        assertEquals(responseUpdate.getResponseCode(), "204");
        assertEquals(responseUpdate.getCode(), 155);

        wsUniversite.addUniversite(universiteB);

        responseUpdate = ws.updateUniversities(auteurA.getId(), universiteA.getUniversiteId(), 1, estRattacheB);

        //relation rattache inconnue
        assertEquals(responseUpdate.getResponseCode(), "204");
        assertEquals(responseUpdate.getCode(), 156);

        ws.addUniversities(auteurA.getId(), estRattacheA);

        responseUpdate = ws.updateUniversities(auteurA.getId(), universiteA.getUniversiteId(), 1, estRattacheB);

        RestResponse<Universite> response = ws.listUniversities(auteurA.getId());

        assertEquals(responseUpdate.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), universiteB);
    }

    @Test
    public void testDeleteUniversite() {
        RestResponse<Universite> response = ws.deleteUniversities(auteurA.getId(), universiteA.getUniversiteId(), 1);

        //auteur inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 150);

        ws.addAuteur(auteurA);

        response = ws.deleteUniversities(auteurA.getId(), universiteA.getUniversiteId(), 1);

        //universite inconnue
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 155);

        wsUniversite.addUniversite(universiteA);

        response = ws.deleteUniversities(auteurA.getId(), universiteA.getUniversiteId(), 1);

        //relation rattache inconnue
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 156);

        ws.addUniversities(auteurA.getId(), estRattacheA);

        response = ws.deleteUniversities(auteurA.getId(), universiteA.getUniversiteId(), 1);

        assertEquals(response.getResponseCode(), "200");
    }

    @Test
    public void testAddLaboratoire() {
        RestResponse<Rattache> response = ws.addLaboratories(auteurA.getId(), estRattacheC);

        //auteur inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 150);

        ws.addAuteur(auteurA);

        response = ws.addLaboratories(auteurA.getId(), estRattacheC);

        //laboratoire inconnue
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 154);

        wsLaboratoire.addLaboratoire(laboratoireA);

        response = ws.addLaboratories(auteurA.getId(), estRattacheC);

        //ajout
        assertEquals(response.getResponseCode(), "201");

        response = ws.addLaboratories(auteurA.getId(), estRattacheC);

        //double ajout
        assertEquals(response.getResponseCode(), "409");
        assertEquals(response.getCode(), 106);
    }

    @Test
    public void testGetLaboratoire() {
        RestResponse<Laboratoire> response = ws.listLaboratories(auteurA.getId());

        //auteur inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 150);

        ws.addAuteur(auteurA);

        wsLaboratoire.addLaboratoire(laboratoireA);

        ws.addLaboratories(auteurA.getId(), estRattacheC);

        response = ws.listLaboratories(auteurA.getId());

        assertEquals(response.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), laboratoireA);
    }

    @Test
    public void testUpdateLaboratoire() {

        RestResponse<Laboratoire> responseUpdate = ws.updateLaboratories(auteurA.getId(), laboratoireA.getLaboratoireId(), 1, estRattacheD);

        //auteur inexistant
        assertEquals(responseUpdate.getResponseCode(), "204");
        assertEquals(responseUpdate.getCode(), 150);

        ws.addAuteur(auteurA);

        responseUpdate = ws.updateLaboratories(auteurA.getId(), laboratoireA.getLaboratoireId(), 1, estRattacheD);

        //ancien laboratoire inconnu
        assertEquals(responseUpdate.getResponseCode(), "204");
        assertEquals(responseUpdate.getCode(), 154);

        wsLaboratoire.addLaboratoire(laboratoireA);

        responseUpdate = ws.updateLaboratories(auteurA.getId(), laboratoireA.getLaboratoireId(), 1, estRattacheD);

        //nouveau laboratoire inconnu
        assertEquals(responseUpdate.getResponseCode(), "204");
        assertEquals(responseUpdate.getCode(), 154);

        wsLaboratoire.addLaboratoire(laboratoireB);

        responseUpdate = ws.updateLaboratories(auteurA.getId(), laboratoireA.getLaboratoireId(), 1, estRattacheD);

        //relation rattache inconnue
        assertEquals(responseUpdate.getResponseCode(), "204");
        assertEquals(responseUpdate.getCode(), 156);
        ws.addLaboratories(auteurA.getId(), estRattacheC);

        responseUpdate = ws.updateLaboratories(auteurA.getId(), laboratoireA.getLaboratoireId(), 1, estRattacheD);

        RestResponse<Laboratoire> response = ws.listLaboratories(auteurA.getId());

        assertEquals(responseUpdate.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), laboratoireB);
    }

    @Test
    public void testDeleteLaboratoire() {
        RestResponse<Laboratoire> response = ws.deleteLaboratories(auteurA.getId(), laboratoireA.getLaboratoireId(), 1);

        //auteur inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 150);

        ws.addAuteur(auteurA);

        response = ws.deleteLaboratories(auteurA.getId(), laboratoireA.getLaboratoireId(), 1);

        //laboratoire inconnue
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 154);

        wsLaboratoire.addLaboratoire(laboratoireA);

        response = ws.deleteLaboratories(auteurA.getId(), laboratoireA.getLaboratoireId(), 1);

        //relation rattache inconnue
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 156);

        ws.addLaboratories(auteurA.getId(), estRattacheC);

        response = ws.deleteLaboratories(auteurA.getId(), laboratoireA.getLaboratoireId(), 1);

        assertEquals(response.getResponseCode(), "200");
    }

    @Test
    public void testGetKeyword() throws ParseException {
        RestResponse<String> response = ws.listKeywords(auteurA.getId());

        //auteur inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 150);

        ws.addAuteur(auteurA);
        wsArticle.addArticle(articleA);

        AEcrit aEcrit = new AEcrit(auteurA.getNom(), 1, articleA.getId());
        AEteEcrit aEteEcrit = new AEteEcrit(articleA.getTitre(), auteurA.getId());
        Keyword keyword = new Keyword("Keyword", 1, articleA.getId());

        response = ws.listKeywords(auteurA.getId());

        //article inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 151);

        ws.addArticle(auteurA.getId(), aEcrit);
        wsArticle.addAuteur(articleA.getId(), aEteEcrit);

        response = ws.listKeywords(auteurA.getId());

        //keyword inexistant
        assertEquals(response.getResponseCode(), "204");
        assertEquals(response.getCode(), 157);

        wsArticle.addKeywords(articleA.getId(), keyword);

        response = ws.listKeywords(auteurA.getId());

        assertEquals(response.getResponseCode(), "200");
        assertEquals(response.getObjectList().size(), 1);
        assertEquals(response.getObjectList().get(0), keyword.getKeyword());
    }
}
