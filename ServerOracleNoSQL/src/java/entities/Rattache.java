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
public class Rattache implements Serializable, Comparable {

    /*
     * The MAJOR_KEY is used to construct
     * the Key's major path component.
     */
    public static final String MAJOR_KEY = "rattache";

    private String type;
    private String value;
    private int rank;

    //value
    private int idAuteur;

    public Rattache(String value, String type, int rank, int idAuteur) {
        this.value = value;
        this.type = type;
        this.rank = rank;
        this.idAuteur = idAuteur;
    }

    public Rattache(byte[] bytes) {
        String rattache = new String(bytes);
        String[] elt = rattache.split("/");
        type = elt[0];
        value = elt[1];
        rank = Integer.parseInt(elt[2]);
        idAuteur = Integer.parseInt(elt[3]);
    }

    public Rattache() {
        this(null, null, -1, -1);
    }

    public void setIdAuteur(int idAuteur) {
        this.idAuteur = idAuteur;

    }

    public int getIdAuteur() {
        return idAuteur;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY, type, value, String.valueOf(rank)), minorKey);
    }

    public Value getStoreValue() {
        return Value.createValue(toString().getBytes());
    }

    @Override
    public String toString() {
        return type + "/"
                + value + "/"
                + rank + "/"
                + idAuteur;
    }
    
    @Override
    public int compareTo(Object obj) {
        Rattache a = (Rattache) obj;
        if (type.equals(a.type)) { // achieving uniqueness
            if (value.equals(a.value)) { // achieving uniqueness
                return (rank > a.rank ? 1 : rank < a.rank ? -1 : 0);
            } else {
                return value.compareTo(a.value);
            }
        } else {
            return type.compareTo(a.type);
        }
    }
}
