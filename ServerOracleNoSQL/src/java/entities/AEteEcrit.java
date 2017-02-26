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
public class AEteEcrit {

    /*
     * The MAJOR_KEY is used to construct
     * the Key's major path component.
     */
    public static final String MAJOR_KEY = "a_ete_ecrit";

    //key
    private String articleTitre;

    //value
    private int idAuteur;

    /**
     *
     * @param idAuteur value
     * @param articleTitre key
     */
    public AEteEcrit(String articleTitre, int idAuteur) {
        this.idAuteur = idAuteur;
        this.articleTitre = articleTitre;
    }

    public AEteEcrit(byte[] bytes) {
        String auteur = new String(bytes);
        String[] elt = auteur.split("/");

        this.articleTitre = elt[0];
        this.idAuteur = Integer.parseInt(elt[1]);
    }

    public AEteEcrit() {
        this(null, -1);
    }

    public int getIdAuteur() {
        return idAuteur;
    }

    public void setIdAuteur(int idAuteur) {
        this.idAuteur = idAuteur;
    }

    public String getArticleTitre() {
        return articleTitre;
    }

    public void setArticleTitre(String articleTitre) {
        this.articleTitre = articleTitre;
    }

    public Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY, articleTitre), minorKey);
    }

    public Value getStoreValue() {
        return Value.createValue(toString().getBytes());
    }

    @Override
    public String toString() {
        return articleTitre + "/" 
                + idAuteur;
    }
}
