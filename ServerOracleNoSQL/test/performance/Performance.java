/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package performance;

import daos.AEcritDAO;
import daos.AEteEcritDAO;
import daos.ArticleDAO;
import daos.AuthorDAO;
import entities.AEcrit;
import entities.AEteEcrit;
import entities.Article;
import entities.Author;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ws.ArticleWS;
import ws.AuthorWS;
import ws.RestResponse;

/**
 *
 * @author Mathieu
 */
public class Performance {

    private static final int TAILLE = 500000;
    private static final int NB_IT = 50;

    private static final AEcritDAO A_ECRIT_DAO = new AEcritDAO();

    private static final AuthorWS AUTHOR_WS = new AuthorWS();
    private static final ArticleWS ARTICLE_WS = new ArticleWS();

    /*
    *
    * Requête 1 : ajout des auteurs + articles + relations
    *
    * Requête 2 : recherche idAuteur -> titreArticle
    *
    * Requête 3 : recherche nomAuteur -> titreArticle
    *
    * Requête 4 : modification du mail d'un auteur
    *
    * Requête 5 : modification du nom d'un auteur
    *
    * Requête 6 : supression d'un auteur
    *
     */
    public static void main(String[] args) throws ParseException, IOException {
        long requeteTime = 0;
        StringBuilder builder = new StringBuilder();
        FileWriter fw = new FileWriter("result.csv");

        builder.append("Taille");
        builder.append(';');
        builder.append("R1");
        builder.append(';');
        builder.append("R2");
        builder.append(';');
        builder.append("R3");
        builder.append(';');
        builder.append("R4");
        builder.append(';');
        builder.append("R5");
        builder.append(';');
        builder.append("R6");

        fw.append(builder);
        
        int[] tailles = {10000,50000,100000,200000};

        for (int t : tailles) {

            builder.append(t);
            builder.append(";");

            clean();
            requeteTime = requete1(t);
            builder.append(requeteTime);
            builder.append(";");
            System.out.println(requeteTime);

            requeteTime = requete2(t);
            builder.append(requeteTime);
            builder.append(";");
            System.out.println(requeteTime);

            requeteTime = requete3(t);
            builder.append(requeteTime);
            builder.append(";");
            System.out.println(requeteTime);

            requeteTime = requete4(t);
            builder.append(requeteTime);
            builder.append(";");
            System.out.println(requeteTime);

            requeteTime = requete5(t);
            builder.append(requeteTime);
            builder.append(";");
            System.out.println(requeteTime);

            requeteTime = requete6(t);
            builder.append(requeteTime);
            builder.append(";");
            System.out.println(requeteTime);

            builder.append('\n');

            fw.append(builder);
        }
        
        fw.close();
    }

    private static void clean() {
        AuthorDAO adao = new AuthorDAO();
        adao.deleteAll();
    }

    public static long requete1(int n) throws ParseException {
        long startTime = 0, endTime = 0, time = 0;
        System.out.println("Requête 1 :");
        startTime = System.currentTimeMillis();
        for (int j = 0; j < n; ++j) {
            AUTHOR_WS.addAuteur(new Author(j, "Nom" + j, "Prenom" + j, "Adresse" + j, "Phone" + j, "Fax" + j, "Mail" + j));
            ARTICLE_WS.addArticle(new Article(j, "Titre" + j, "Resume" + j, j));

            AUTHOR_WS.addArticle(j, new AEcrit("Nom" + j, 1, j));
            ARTICLE_WS.addAuteur(j, new AEteEcrit("Titre" + j, 1, j));
        }
        endTime = System.currentTimeMillis();
        time = (endTime - startTime);

        return time;
    }

    public static long requete2(int n) throws ParseException {
        long startTime = 0, endTime = 0, time = 0;
        System.out.println("Requête 2 :");
        Random rand = new Random();

        for (int i = 0; i < NB_IT; ++i) {
            startTime = System.currentTimeMillis();
            RestResponse<Article> listArticle = AUTHOR_WS.listArticle(rand.nextInt(n));
            endTime = System.currentTimeMillis();
            time += (endTime - startTime);
        }

        return time;
    }

    public static long requete3(int n) throws ParseException {
        long startTime = 0, endTime = 0, time = 0;
        System.out.println("Requête 3 :");
        Random rand = new Random();

        for (int i = 0; i < NB_IT; ++i) {
            startTime = System.currentTimeMillis();
            RestResponse<Article> listArticle = AUTHOR_WS.listArticle("Nom" + rand.nextInt(n));
            endTime = System.currentTimeMillis();
            time += (endTime - startTime);
        }

        return time;
    }

    public static long requete4(int n) {
        long startTime = 0, endTime = 0, time = 0;
        System.out.println("Requête 4 :");
        Random rand = new Random();

        for (int i = 0; i < NB_IT; ++i) {
            startTime = System.currentTimeMillis();
            int nextInt = rand.nextInt(n);
            AUTHOR_WS.updateAuteur(nextInt, new Author(nextInt, null, null, null, null, null, "newMail" + nextInt));
            endTime = System.currentTimeMillis();
            time += (endTime - startTime);
        }

        return time;
    }

    public static long requete5(int n) throws ParseException {
        long startTime = 0, endTime = 0, time = 0;
        System.out.println("Requête 5 :");
        Random rand = new Random();

        for (int i = 0; i < NB_IT; ++i) {
            startTime = System.currentTimeMillis();
            int nextInt = rand.nextInt(n);
            //authorDAO.update(nextInt, new Author(nextInt, "newNom" + nextInt, null, null, null, null, null));
            AUTHOR_WS.updateAuteur(nextInt, new Author(nextInt, "newNom" + nextInt, null, null, null, null, null));

            for (AEcrit a : A_ECRIT_DAO.read("Nom" + nextInt)) {
                A_ECRIT_DAO.delete(a.getAuteurNom(), a.getRank());
                A_ECRIT_DAO.create("newNom" + nextInt, a.getIdArticle(), a.getRank());
            }

            endTime = System.currentTimeMillis();
            time += (endTime - startTime);
        }

        return time;
    }

    public static long requete6(int n) {
        long startTime = 0, endTime = 0, time = 0;
        System.out.println("Requête 6 :");
        Random rand = new Random();

        for (int i = 0; i < NB_IT; ++i) {
            startTime = System.currentTimeMillis();
            int nextInt = rand.nextInt(n);
            AUTHOR_WS.deleteAuteur(nextInt);
            endTime = System.currentTimeMillis();
            time += (endTime - startTime);
        }

        return time;
    }
}
