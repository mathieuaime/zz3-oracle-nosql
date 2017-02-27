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
public class AEcrit implements Serializable, Comparable {

    /*
     * The MAJOR_KEY is used to construct
     * the Key's major path component.
     */
    public static final String MAJOR_KEY = "a_ecrit";

    //key
    private String auteurNom;
    private int rank;

    //value
    private int idArticle;

    public AEcrit(String auteurNom, int rank, int idArticle) {
        this.auteurNom = auteurNom;
        this.idArticle = idArticle;
        this.rank = rank;
    }

    public AEcrit(byte[] bytes) {
        String titre = new String(bytes);

        String[] elt = titre.split("/");
        this.auteurNom = elt[0];
        this.rank = Integer.parseInt(elt[1]);
        this.idArticle = Integer.parseInt(elt[2]);
    }

    public AEcrit() {
        this("", -1, -1);
    }

    public String getAuteurNom() {
        return auteurNom;
    }

    public void setAuteurNom(String auteurNom) {
        this.auteurNom = auteurNom;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY, auteurNom, String.valueOf(rank)), minorKey);
    }

    public Value getStoreValue() {
        return Value.createValue(toString().getBytes());
    }

    @Override
    public String toString() {
        return auteurNom + "/"
                + rank + "/"
                + idArticle;
    }

    @Override
    public int compareTo(Object obj) {
        AEcrit a = (AEcrit) obj;
        if (auteurNom.equals(a.auteurNom)) { // achieving uniqueness
            return (rank > a.rank ? 1 : rank < a.rank ? -1 : 0);
        } else {
            return auteurNom.compareTo(a.auteurNom);
        }
    }
}
