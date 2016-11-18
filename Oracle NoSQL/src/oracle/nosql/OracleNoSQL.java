/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql;

import java.util.Random;
import oracle.nosql.entities.AEteEcrit;
import oracle.nosql.entities.Auteur;
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
        
        //supprimerTest(100000);
        genererTest(100000);
        //runTest(20);        
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
    
    public static void supprimerTest(int n) {
        AuteurFactory auteurFactory = new AuteurFactory();
        
        auteurFactory.supprimerTest(n);
                        
        LivreFactory livreFactory = new LivreFactory();
        
        livreFactory.supprimerTest(2*n); 
        
        AEcritFactory aEcritFactory = new AEcritFactory();
        
        aEcritFactory.supprimerTest(n);
        
        AEteEcritFactory aEteEcritFactory = new AEteEcritFactory();
        
        aEteEcritFactory.supprimerTest(n);
    } 
    
    public static void runTest(int nb) {
        long avgTime;
        
        System.out.println("Recherche V1 :\n");
        
        //Recherche à partir du nom du livre
        avgTime = 0;
        
        System.out.println("Recherche du nom de l'auteur à partir du nom du livre");

        for (int i = 0; i < nb; ++i) {
            Random r = new Random();
            int n = r.nextInt(200000);
            long time = rechercheAuteur("Le bateau"+n);
            avgTime = (i*avgTime + time) / (1 + i);
            System.out.println("Recherche " + (1 + i) + " : " + time + "ms; Temps moyen : " + avgTime + "ms");
        }
        
        System.out.println("Temps moyen de recherche : " + avgTime + "ms\n");
        
        //Mise à jour à partir de l'id de l'auteur
        
        avgTime = 0;
        
        System.out.println("Mise à jour de l'auteur à partir de son id");
        
        for (int i = 0; i < nb; ++i) {
            Random r = new Random();
            int n = r.nextInt(100000);
            long time = updateAuteur(n, "Cannes");
            avgTime = (i*avgTime + time) / (1 + i);
            System.out.println("MAJ " + (1 + i) + " : " + time + "ms; Temps moyen : " + avgTime + "ms");
        }
        
        System.out.println("Temps moyen de mise à jour : " + avgTime + "ms\n");   
        
        //Mise à jour à partir du nom de l'auteur
        
        avgTime = 0;
        
        System.out.println("Mise à jour de l'auteur à partir de son nom");
        
        for (int i = 0; i < nb; ++i) {
            Random r = new Random();
            int n = r.nextInt(100000);
            long time = updateAuteur("Aimé"+n, "Cannes");
            avgTime = (i*avgTime + time) / (1 + i);
            System.out.println("MAJ " + (1 + i) + " : " + time + "ms; Temps moyen : " + avgTime + "ms");
        }
        
        System.out.println("Temps moyen de mise à jour : " + avgTime + "ms\n"); 
        
        //Suppression à partir de l'id de l'auteur
        
        avgTime = 0;
        
        System.out.println("Suppression de l'auteur à partir de son id");
        
        for (int i = 0; i < nb; ++i) {
            Random r = new Random();
            int n = r.nextInt(100000);
            long time = deleteAuteur(n);
            avgTime = (i*avgTime + time) / (1 + i);
            System.out.println("Suppression " + (1 + i) + " : " + time + "ms; Temps moyen : " + avgTime + "ms");
        }
        
        System.out.println("Temps moyen de suppression : " + avgTime + "ms\n");   
        
        //Suppression à partir du nom de l'auteur
        
        avgTime = 0;
        
        System.out.println("Suppression de l'auteur à partir de son nom");
        
        for (int i = 0; i < nb; ++i) {
            Random r = new Random();
            int n = r.nextInt(100000);
            long time = deleteAuteur("Aimé"+n);
            avgTime = (i*avgTime + time) / (1 + i);
            System.out.println("Suppression " + (1 + i) + " : " + time + "ms; Temps moyen : " + avgTime + "ms");
        }
        
        System.out.println("Temps moyen de supression : " + avgTime + "ms\n");
    }

    public static long rechercheAuteur(String livre) {
        
        AuteurFactory auteurFactory = new AuteurFactory();
        AEteEcritFactory aEteEcritFactory = new AEteEcritFactory();
        
        long startTime = System.currentTimeMillis();
        
        
        //Recherche de l'id de l'auteur à partir du nom du livre        
        AEteEcrit aEteEcrit = aEteEcritFactory.read(livre);
        int idAuteur = aEteEcrit.getAuteurId();
        
        //Recherche du nom de l'auteur à partir de l'id de l'auteur        
        Auteur auteur = auteurFactory.read(idAuteur);
        String nomAuteur = auteur.getNom();
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        
        return elapsedTime;
        
    }
       
    public static long updateAuteur(int idAuteur, String newAdresse) {
        AuteurFactory auteurFactory = new AuteurFactory();
        
        long startTime = System.currentTimeMillis();
        
        //Recherche du nom de l'auteur à partir de l'id de l'auteur        
        auteurFactory.update(idAuteur, null, null, newAdresse, null);
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        
        return elapsedTime;
    }
    
    public static long updateAuteur(String nomAuteur, String newAdresse) {
        AuteurFactory auteurFactory = new AuteurFactory();
        
        long startTime = System.currentTimeMillis();
        
        //Recherche de l'id de l'auteur à partie de son nom       
        Auteur auteur = auteurFactory.read(nomAuteur);
        int idAuteur = auteur.getAuteurId();
        
        //Mise à jour de l'auteur
        auteurFactory.update(idAuteur, null, null, newAdresse, null);
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        
        return elapsedTime;
    }
    
    public static long deleteAuteur(int idAuteur) {
        AuteurFactory auteurFactory = new AuteurFactory();
        
        Auteur auteur = auteurFactory.read(idAuteur);
        
        long startTime = System.currentTimeMillis();
        
        //Suppression de l'auteur        
        auteurFactory.delete(idAuteur);
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        
        //rajout de l'auteur supprimé pour les tests suivants
        auteurFactory.create(auteur);
        
        return elapsedTime;
    }
    
    public static long deleteAuteur(String nomAuteur) {
        AuteurFactory auteurFactory = new AuteurFactory();
        
        long startTime = System.currentTimeMillis();
        
        //Recherche de l'id de l'auteur à partir de son nom       
        Auteur auteur = auteurFactory.read(nomAuteur);
        int idAuteur = auteur.getAuteurId();
        
        //Suppression de l'auteur
        auteurFactory.delete(idAuteur);
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        
        //rajout de l'auteur supprimé pour les tests suivants
        auteurFactory.create(auteur);
        
        return elapsedTime;
    }
}
