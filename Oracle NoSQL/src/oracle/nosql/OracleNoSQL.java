/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql;

import oracle.nosql.entities.Auteur;
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
        
        AuteurFactory auteurFactory = new AuteurFactory();
        
        long startTime = System.currentTimeMillis();
        
        auteurFactory.genererTest(100000);
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Création de 100000 auteurs en "+ elapsedTime +" ms");
                
        
        LivreFactory livreFactory = new LivreFactory();
        
        startTime = System.currentTimeMillis();
        
        livreFactory.genererTest(200000);
        
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("Création de 200000 livres en "+ elapsedTime +" ms");
        
        auteurFactory.afficherTest(100000);
        livreFactory.afficherTest(200000);
        
        
    }
    
}
