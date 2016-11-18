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
public class AEcrit {
    /*
     * The MAJOR_KEY is used to construct
     * the Key's major path component.
     */
    public static final String MAJOR_KEY = "a_ecrit";

    private String auteurNom;
    private String livreTitre;
    private int rang;

    public AEcrit(String auteurNom, String livreTitre, int rang) {
        this.auteurNom = auteurNom;
        this.livreTitre = livreTitre;
        this.rang = rang;
    }
    
    public AEcrit(String auteurNom, int rang, byte[] bytes) {
        this.auteurNom = auteurNom;
        this.rang = rang;
        String titre = new String(bytes);
        this.livreTitre = titre;
    }

    public String getAuteurNom() {
        return auteurNom;
    }

    public void setAuteurNom(String auteurNom) {
        this.auteurNom = auteurNom;
    }

    public String getLivreTitre() {
        return livreTitre;
    }

    public void setLivreTitre(String livreTitre) {
        this.livreTitre = livreTitre;
    }    

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }
    
    public Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY,auteurNom, String.valueOf(rang)), minorKey);
    }

    public Value getStoreValue() {
        return Value.createValue(livreTitre.getBytes());
    }
    
    @Override
    public String toString() {
        return auteurNom + " / " + livreTitre;
    }
    
}
