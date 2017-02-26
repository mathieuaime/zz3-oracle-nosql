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
public class EstRattache {

    /*
     * The MAJOR_KEY is used to construct
     * the Key's major path component.
     */
    public static final String MAJOR_KEY = "est_rattache";

    //key
    private String nomAuteur;
    private String type;
    private int rank;

    //value laboratoire ou univ
    private int value;

    public EstRattache(String nomAuteur, String type, int rank, int value) {
        this.nomAuteur = nomAuteur;
        this.type = type;
        this.rank = rank;
        this.value = value;
    }

    public EstRattache(byte[] bytes) {
        String rattache = new String(bytes);
        String[] elt = rattache.split("/");
        nomAuteur = elt[0];
        type = elt[1];
        rank = Integer.parseInt(elt[2]);
        value = Integer.parseInt(elt[3]);
    }

    public String getNomAuteur() {
        return nomAuteur;
    }

    public void setNomAuteur(String nomAuteur) {
        this.nomAuteur = nomAuteur;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY, nomAuteur, type, String.valueOf(rank)), minorKey);
    }

    public Value getStoreValue() {
        return Value.createValue(toString().getBytes());
    }

    @Override
    public String toString() {
        return nomAuteur + "/"
                + type + "/"
                + rank + "/"
                + value;
    }
}
