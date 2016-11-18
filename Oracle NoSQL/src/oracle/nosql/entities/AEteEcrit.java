/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql.entities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

    private String auteurNom;
    private String livreTitre;

    public AEteEcrit(String auteurNom, String livreTitre) {
        this.auteurNom = auteurNom;
        this.livreTitre = livreTitre;
    }
    
    public AEteEcrit(String livreTitre, byte[] bytes) {
        this.livreTitre = livreTitre;
        
        String auteur = new String(bytes);
        auteur = auteur.trim();
        
        this.auteurNom = auteur;
    }

    public String getAuteurNom() {
        return auteurNom;
    }

    public void setAuteurNom(String auteurNom) {
        this.auteurNom = auteurNom;
    }

    public String getLivreId() {
        return livreTitre;
    }

    public void setLivreId(String livreTitre) {
        this.livreTitre = livreTitre;
    }    
    
    public Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY,livreTitre), minorKey);
    }

    public Value getStoreValue() {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try {
            dataOutputStream.writeUTF(auteurNom); 
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Value.createValue(byteArrayOutputStream.toByteArray());
    }
    
    @Override
    public String toString() {
        return livreTitre + " / " + auteurNom;
    }
    
}
