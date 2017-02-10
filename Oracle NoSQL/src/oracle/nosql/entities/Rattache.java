/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql.entities;

import java.util.Arrays;
import oracle.kv.Key;
import oracle.kv.Value;

/**
 *
 * @author mathieu
 */
public class Rattache {
    /*
     * The MAJOR_KEY is used to construct
     * the Key's major path component.
     */
    public static final String MAJOR_KEY = "rattache";

    private String universiteNom;
    private String laboratoireNom;
    private int idAuteur;
    private int rang;

    public Rattache(String universiteNom, String laboratoireNom, int rang, int idAuteur) {
        this.universiteNom = universiteNom;
        this.laboratoireNom = laboratoireNom;
        this.idAuteur = idAuteur;
        this.rang = rang;
    }
    
    public Rattache(String laboratoireNom,String universiteNom, int rang, byte[] bytes) {
        
        this.laboratoireNom = laboratoireNom;
        this.universiteNom = universiteNom;
        this.rang = rang;
        String auteur = new String(bytes);

        this.idAuteur = (int) Float.parseFloat(auteur.trim());
        
    }
    public void setIdAuteur(int IdAuteur){
        this.idAuteur = idAuteur;
       
    }
    public int getIdAuteur(){
        return idAuteur;
    }
    
    public String getAuteurNom() {
        return universiteNom;
    }

    public void setAuteurNom(String universiteNom) {
        this.universiteNom = universiteNom;
    }

    public String getLivreTitre() {
        return laboratoireNom;
    }

    public void setLivreTitre(String laboratoireNom) {
        this.laboratoireNom = laboratoireNom;
    }    

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }
    
    public Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY,laboratoireNom, universiteNom, String.valueOf(rang)), minorKey);
    }

    public Value getStoreValue() {
        return Value.createValue(laboratoireNom.getBytes());
    }
    
    @Override
    public String toString() {
        return universiteNom + " / " + laboratoireNom + "/"+ idAuteur;
    }
    
}

