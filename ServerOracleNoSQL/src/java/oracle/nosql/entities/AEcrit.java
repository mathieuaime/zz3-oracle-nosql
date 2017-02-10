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

    //key
    private String auteurNom;
    private int rang;
    
    //value
    private int idLivre;

    public AEcrit(String auteurNom, int rang, int idLivre) {
        this.auteurNom = auteurNom;
        this.idLivre = idLivre;
        this.rang = rang;
    }
    
    public AEcrit(byte[] bytes) {
        String titre = new String(bytes);
        
        String[] elt = titre.split("/");
        this.auteurNom = elt[0];
        this.rang = Integer.parseInt(elt[1]);
        this.idLivre = Integer.parseInt(elt[2]);
    }
    
    public AEcrit() {
        this("",-1,-1);
    }

    public String getAuteurNom() {
        return auteurNom;
    }

    public void setAuteurNom(String auteurNom) {
        this.auteurNom = auteurNom;
    }

    public int getIdLivre() {
        return idLivre;
    }

    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
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
        return Value.createValue(toString().getBytes());
    }
    
    @Override
    public String toString() {
        return auteurNom + "/" + rang + "/" + idLivre;
    }
    
}
