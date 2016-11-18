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

    private int auteurId;
    private String livreTitre;
    private int rang;

    public AEcrit(int auteurId, String livreTitre, int rang) {
        this.auteurId = auteurId;
        this.livreTitre = livreTitre;
        this.rang = rang;
    }
    
    public AEcrit(int auteurId, int rang, byte[] bytes) {
        this.auteurId = auteurId;
        this.rang = rang;
        String titre = new String(bytes);
        this.livreTitre = titre;
    }

    public int getAuteurId() {
        return auteurId;
    }

    public void setAuteurId(int auteurId) {
        this.auteurId = auteurId;
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
        return Key.createKey(Arrays.asList(MAJOR_KEY,String.valueOf(auteurId), String.valueOf(rang)), minorKey);
    }

    public Value getStoreValue() {
        return Value.createValue(livreTitre.getBytes());
    }
    
    @Override
    public String toString() {
        return auteurId + " / " + livreTitre;
    }
    
}
