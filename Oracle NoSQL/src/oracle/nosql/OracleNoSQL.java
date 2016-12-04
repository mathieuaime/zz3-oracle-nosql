/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql;

import java.util.Random;
import oracle.nosql.entities.AEteEcrit;
import oracle.nosql.entities.Auteur;
import oracle.nosql.daos.AEcritDAO;
import oracle.nosql.daos.AEteEcritDAO;
import oracle.nosql.daos.AuteurDAO;
import oracle.nosql.daos.LivreDAO;

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
        //genererTest(100000);
        //runTest(20);        
    }    
    
    public static void genererTest(int n) {
        //CrÃ©ation de 100000 auteurs
        AuteurDAO auteurDAO = new AuteurDAO();
        
        long startTime = System.currentTimeMillis();
        
        auteurDAO.genererTest(n);
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("CrÃ©ation de "+n+" auteurs en "+ elapsedTime +" ms");
        
        
        //CrÃ©ation de 200000 livres                
        LivreDAO livreDAO = new LivreDAO();
        
        startTime = System.currentTimeMillis();
        
        livreDAO.genererTest(2*n); 
        
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("CrÃ©ation de "+(2*n)+" livres en "+ elapsedTime +" ms");
                
        //CrÃ©ation des relations a Ã©crit
        AEcritDAO aEcritDAO = new AEcritDAO();
        
        startTime = System.currentTimeMillis();
        
        aEcritDAO.genererTest(n);
        
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("CrÃ©ation de "+(2*n)+" relations auteurs - > livres en "+ elapsedTime +" ms");
        
        //CrÃ©ation des relations a Ã©tÃ© Ã©crit
        AEteEcritDAO aEteEcritDAO = new AEteEcritDAO();
        
        startTime = System.currentTimeMillis();
        
        aEteEcritDAO.genererTest(n);
        
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("CrÃ©ation de "+(2*n)+" relations livres - > auteurs en "+ elapsedTime +" ms"); 
    } 
    
    public static void supprimerTest(int n) {
        AuteurDAO auteurDAO = new AuteurDAO();
        
        auteurDAO.supprimerTest(n);
                        
        LivreDAO livreDAO = new LivreDAO();
        
        livreDAO.supprimerTest(2*n); 
        
        AEcritDAO aEcritDAO = new AEcritDAO();
        
        aEcritDAO.supprimerTest(n);
        
        AEteEcritDAO aEteEcritDAO = new AEteEcritDAO();
        
        aEteEcritDAO.supprimerTest(n);
    } 
    
    public static void runTest(int nb) {
        long avgTime;
        
        System.out.println("Recherche V1 :\n");
        
        //Recherche Ã  partir du nom du livre
        avgTime = 0;
        
        System.out.println("Recherche du nom de l'auteur Ã  partir du nom du livre");

        for (int i = 0; i < nb; ++i) {
            Random r = new Random();
            int n = r.nextInt(200000);
            long time = rechercheAuteur("Le bateau"+n);
            avgTime = (i*avgTime + time) / (1 + i);
            System.out.println("Recherche " + (1 + i) + " : " + time + "ms; Temps moyen : " + avgTime + "ms");
        }
        
        System.out.println("Temps moyen de recherche : " + avgTime + "ms\n");
        
        //Mise Ã  jour Ã  partir de l'id de l'auteur
        
        avgTime = 0;
        
        System.out.println("Mise Ã  jour de l'auteur Ã  partir de son id");
        
        for (int i = 0; i < nb; ++i) {
            Random r = new Random();
            int n = r.nextInt(100000);
            long time = updateAuteur(n, "Cannes");
            avgTime = (i*avgTime + time) / (1 + i);
            System.out.println("MAJ " + (1 + i) + " : " + time + "ms; Temps moyen : " + avgTime + "ms");
        }
        
        System.out.println("Temps moyen de mise Ã  jour : " + avgTime + "ms\n");   
        
        //Mise Ã  jour Ã  partir du nom de l'auteur
        
        avgTime = 0;
        
        System.out.println("Mise Ã  jour de l'auteur Ã  partir de son nom");
        
        for (int i = 0; i < nb; ++i) {
            Random r = new Random();
            int n = r.nextInt(100000);
            long time = updateAuteur("AimÃ©"+n, "Cannes");
            avgTime = (i*avgTime + time) / (1 + i);
            System.out.println("MAJ " + (1 + i) + " : " + time + "ms; Temps moyen : " + avgTime + "ms");
        }
        
        System.out.println("Temps moyen de mise Ã  jour : " + avgTime + "ms\n"); 
        
        //Suppression Ã  partir de l'id de l'auteur
        
        avgTime = 0;
        
        System.out.println("Suppression de l'auteur Ã  partir de son id");
        
        for (int i = 0; i < nb; ++i) {
            Random r = new Random();
            int n = r.nextInt(100000);
            long time = deleteAuteur(n);
            avgTime = (i*avgTime + time) / (1 + i);
            System.out.println("Suppression " + (1 + i) + " : " + time + "ms; Temps moyen : " + avgTime + "ms");
        }
        
        System.out.println("Temps moyen de suppression : " + avgTime + "ms\n");   
        
        //Suppression Ã  partir du nom de l'auteur
        
        avgTime = 0;
        
        System.out.println("Suppression de l'auteur Ã  partir de son nom");
        
        for (int i = 0; i < nb; ++i) {
            Random r = new Random();
            int n = r.nextInt(100000);
            long time = deleteAuteur("AimÃ©"+n);
            avgTime = (i*avgTime + time) / (1 + i);
            System.out.println("Suppression " + (1 + i) + " : " + time + "ms; Temps moyen : " + avgTime + "ms");
        }
        
        System.out.println("Temps moyen de supression : " + avgTime + "ms\n");
    }

    public static long rechercheAuteur(String livre) {
        
        AEteEcritDAO aEteEcritDAO = new AEteEcritDAO();
        
        long startTime = System.currentTimeMillis();
        
        //Recherche du nom de l'auteur Ã  partir du nom du livre        
        AEteEcrit aEteEcrit = aEteEcritDAO.read(livre);
        String nomAuteur = aEteEcrit.getAuteurNom();
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        
        return elapsedTime;
        
    }
       
    public static long updateAuteur(int idAuteur, String newAdresse) {
        AuteurDAO auteurDAO = new AuteurDAO();
        
        long startTime = System.currentTimeMillis();
             
        auteurDAO.update(idAuteur, null, null, newAdresse, null);
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        
        return elapsedTime;
    }
    
    public static long updateAuteur(String nomAuteur, String newAdresse) {
        AuteurDAO auteurDAO = new AuteurDAO();
        
        long startTime = System.currentTimeMillis();
             
        Auteur auteur = auteurDAO.read(nomAuteur);
        int idAuteur = auteur.getAuteurId();
        
        //Mise Ã  jour de l'auteur
        auteurDAO.update(idAuteur, null, null, newAdresse, null);
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        
        return elapsedTime;
    }
    
    public static long deleteAuteur(int idAuteur) {
        AuteurDAO auteurDAO = new AuteurDAO();
        
        Auteur auteur = auteurDAO.read(idAuteur);
        
        long startTime = System.currentTimeMillis();
        
        //Suppression de l'auteur        
        auteurDAO.delete(idAuteur);
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        
        //rajout de l'auteur supprimÃ© pour les tests suivants
        auteurDAO.create(auteur);
        
        return elapsedTime;
    }
    
    public static long deleteAuteur(String nomAuteur) {
        AuteurDAO auteurDAO = new AuteurDAO();
        
        long startTime = System.currentTimeMillis();
        
        //Recherche de l'id de l'auteur Ã  partir de son nom       
        Auteur auteur = auteurDAO.read(nomAuteur);
        int idAuteur = auteur.getAuteurId();
        
        //Suppression de l'auteur
        auteurDAO.delete(idAuteur);
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        
        //rajout de l'auteur supprimÃ© pour les tests suivants
        auteurDAO.create(auteur);
        
        return elapsedTime;
    }
}
//test git
