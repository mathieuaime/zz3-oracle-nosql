/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

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

    //key
    private String laboratoireNom;
    private String universiteNom;
    private int rang;
    
    //value
    private int idAuteur;

    public Rattache(String laboratoireNom, String universiteNom, int rang, int idAuteur) {
        this.laboratoireNom = laboratoireNom;
        this.universiteNom = universiteNom;
        this.rang = rang;
        this.idAuteur = idAuteur;
    }
    
    public Rattache(byte[] bytes) {
        String rattache = new String(bytes);
        String[] elt = rattache.split("/");
        laboratoireNom = elt[0];
        universiteNom = elt[1];        
        rang = Integer.parseInt(elt[2]);        
        idAuteur = Integer.parseInt(elt[3]);        
    }
    public void setIdAuteur(int idAuteur){
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
        return Value.createValue(toString().getBytes());
    }
    
    @Override
    public String toString() {
        return laboratoireNom + "/" + universiteNom + "/" + rang + "/" + idAuteur;
    }
    
}

