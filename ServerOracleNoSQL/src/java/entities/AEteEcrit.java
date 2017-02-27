/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Arrays;
import oracle.kv.Key;
import oracle.kv.Value;

/**
 *
 * @author mathieu
 */
public class AEteEcrit implements Serializable, Comparable {

    /*
     * The MAJOR_KEY is used to construct
     * the Key's major path component.
     */
    public static final String MAJOR_KEY = "a_ete_ecrit";

    //key
    private String articleTitre;
    private int rank;

    //value
    private int idAuteur;

    /**
     *
     * @param idAuteur value
     * @param articleTitre key
     */
    public AEteEcrit(String articleTitre, int rank, int idAuteur) {
        this.idAuteur = idAuteur;
        this.rank = rank;
        this.articleTitre = articleTitre;
    }

    public AEteEcrit(byte[] bytes) {
        String auteur = new String(bytes);
        String[] elt = auteur.split("/");

        this.articleTitre = elt[0];
        this.rank = Integer.parseInt(elt[1]);
        this.idAuteur = Integer.parseInt(elt[2]);
    }

    public AEteEcrit() {
        this(null, -1, -1);
    }

    public int getIdAuteur() {
        return idAuteur;
    }

    public void setIdAuteur(int idAuteur) {
        this.idAuteur = idAuteur;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getArticleTitre() {
        return articleTitre;
    }

    public void setArticleTitre(String articleTitre) {
        this.articleTitre = articleTitre;
    }

    public Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY, articleTitre, String.valueOf(rank)), minorKey);
    }

    public Value getStoreValue() {
        return Value.createValue(toString().getBytes());
    }

    @Override
    public String toString() {
        return articleTitre + "/"
                + rank + "/"
                + idAuteur;
    }
    
    @Override
    public int compareTo(Object obj) {
        AEteEcrit a = (AEteEcrit) obj;
        if (articleTitre.equals(a.articleTitre)) { // achieving uniqueness
            return (rank > a.rank ? 1 : rank < a.rank ? -1 : 0);
        } else {
            return articleTitre.compareTo(a.articleTitre);
        }
    }
}
