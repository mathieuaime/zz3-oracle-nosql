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
public class AEteEcrit {
    /*
     * The MAJOR_KEY is used to construct
     * the Key's major path component.
     */
    public static final String MAJOR_KEY = "a_ete_ecrit";

    //key
    private String livreTitre;
    
    //value
    private int idAuteur;

    /**
     *
     * @param idAuteur value
     * @param livreTitre key
     */
    public AEteEcrit(String livreTitre, int idAuteur) {
        this.idAuteur = idAuteur;
        this.livreTitre = livreTitre;
    }
    
    public AEteEcrit(byte[] bytes) {        
        String auteur = new String(bytes);
        String[] elt = auteur.split("/");
        
        this.livreTitre = elt[0];
        this.idAuteur = Integer.parseInt(elt[1]);
    }
    
    public AEteEcrit() {
        this(null,-1);
    }

    public int getIdAuteur() {
        return idAuteur;
    }

    public void setIdAuteur(int idAuteur) {
        this.idAuteur = idAuteur;
    }

    public String getLivreTitre() {
        return livreTitre;
    }

    public void setLivreTitre(String livreTitre) {
        this.livreTitre = livreTitre;
    }    
    
    public Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY,livreTitre), minorKey);
    }

    public Value getStoreValue() {
        return Value.createValue(toString().getBytes());
    }
    
    @Override
    public String toString() {
        return livreTitre + "/" + idAuteur;
    }
    
}
