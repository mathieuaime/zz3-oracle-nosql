/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql;

import java.util.Random;
import oracle.nosql.entities.AEcrit;
import oracle.nosql.entities.AEteEcrit;
import oracle.nosql.entities.Auteur;
import oracle.nosql.entities.Livre;
import oracle.nosql.factory.AEcritFactory;
import oracle.nosql.factory.AEteEcritFactory;
import oracle.nosql.factory.AuteurFactory;
import oracle.nosql.factory.LivreFactory;

/**
 *
 * @author mathieu
 */
public class OracleNoSQL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //genererTest(100000);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 50; ++i) {
            Random r = new Random();
            int n = r.nextInt(200000);
            rechercheAuteur("Le bateau"+n);
        }

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Recherche de 50 auteurs aléatoirement en "+ elapsedTime +" ms");
      
    }    
    
    public static void genererTest(int n) {
        //Création de 100000 auteurs
        AuteurFactory auteurFactory = new AuteurFactory();
        
        long startTime = System.currentTimeMillis();
        
        auteurFactory.genererTest(n);
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Création de "+n+" auteurs en "+ elapsedTime +" ms");
        
        
        //Création de 200000 livres                
        LivreFactory livreFactory = new LivreFactory();
        
        startTime = System.currentTimeMillis();
        
        livreFactory.genererTest(2*n); 
        
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("Création de "+(2*n)+" livres en "+ elapsedTime +" ms");
                
        //Création des relations a écrit
        AEcritFactory aEcritFactory = new AEcritFactory();
        
        startTime = System.currentTimeMillis();
        
        aEcritFactory.genererTest(n);
        
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("Création de "+(2*n)+" relations auteurs - > livres en "+ elapsedTime +" ms");
        
        //Création des relations a été écrit
        AEteEcritFactory aEteEcritFactory = new AEteEcritFactory();
        
        startTime = System.currentTimeMillis();
        
        aEteEcritFactory.genererTest(n);
        
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("Création de "+(2*n)+" relations livres - > auteurs en "+ elapsedTime +" ms"); 
    } 
    
    public static void rechercheAuteur(String livre) {
        
        LivreFactory livreFactory = new LivreFactory();
        AuteurFactory auteurFactory = new AuteurFactory();
        AEteEcritFactory aEteEcritFactory = new AEteEcritFactory();
        
        long startTime = System.currentTimeMillis();
        
        //Recherche de l'id du livre à partir du titre du livre        
        Livre l = livreFactory.read(livre);
        int idLivre = l.getLivreId();
        
        //Recherche de l'id de l'auteur à partir de l'id du livre        
        AEteEcrit aEteEcrit = aEteEcritFactory.read(idLivre);
        int idAuteur = aEteEcrit.getAuteurId();
        
        //Recherche du nom de l'auteur à partir de l'id de l'auteur        
        Auteur auteur = auteurFactory.read(idAuteur);
        String nomAuteur = auteur.getNom();
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;  
        
        System.out.println(livre + " a été écrit par " + nomAuteur + " (" + elapsedTime+" ms)"); 
        
    }

    private static void rechercheLivre(String auteur, int rang) {
        
        //Recherche de l'id de l'auteur à partir du nom de l'auteur
        LivreFactory livreFactory = new LivreFactory();
        AuteurFactory auteurFactory = new AuteurFactory();
        AEcritFactory aEcritFactory = new AEcritFactory();
        
        long startTime1 = System.currentTimeMillis();
        long startTime = System.currentTimeMillis();
        
        Auteur a = auteurFactory.read(auteur);
        int idAuteur = a.getAuteurId();
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Recherche de l'id de l'auteur en "+ elapsedTime +" ms");
        
        //Recherche de l'id du livre à partir de l'id de l'auteur
        
        startTime = System.currentTimeMillis();
        
        AEcrit aEcrit = aEcritFactory.read(idAuteur, rang);
        int idLivre = aEcrit.getLivreId();
        
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("Recherche de l'id du livre en "+ elapsedTime +" ms");
        
        //Recherche du nom du livre à partir de l'id du livre

        startTime = System.currentTimeMillis();
        
        Livre livre = livreFactory.read(idLivre);
        String titreLivre = livre.getTitre();
        
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("Recherche du nom du livre en "+ elapsedTime +" ms");
        
        System.out.println("Recherche totale en "+(stopTime - startTime1)+" ms");   
        
        System.out.println(auteur + " a écrit par " + titreLivre);
        
    }
}
