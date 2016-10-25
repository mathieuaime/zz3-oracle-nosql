/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql;

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
public class Auteur {
    /*
     * The auteurId is a unique identifier and is used to construct
     * the Key's major path.
     */
    private final String auteurId;

    /*
     * The MAJOR_KEY is used to construct
     * the Key's major path component.
     */
    static final String MAJOR_KEY = "auteur";

    private String nom;
    private String prenom;
    private String adresse;
    private String phone;
    
    Auteur(final String auteurId) {
        this.auteurId = auteurId;
    }
    
    Auteur(final String auteurId, byte[] bytes) {
        String auteur = new String(bytes);
        String[] elt = auteur.split(";");
        this.auteurId = auteurId;
        nom = elt[0];
        prenom = elt[1];        
        adresse = elt[2];        
        phone = elt[3];        
    }

    String getAuteurId() {
        return auteurId;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY,auteurId), minorKey);
    }

    Value getStoreValue() {

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try {
             
            dataOutputStream.writeUTF(nom);            
            dataOutputStream.writeUTF(";");
            dataOutputStream.writeUTF(prenom);
            dataOutputStream.writeUTF(";");
            dataOutputStream.writeUTF(adresse);
            dataOutputStream.writeUTF(";");
            dataOutputStream.writeUTF(phone);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Value.createValue(byteArrayOutputStream.toByteArray());
    }
    
    @Override
    public String toString() {
        return auteurId + " / " + nom + " / " + prenom + " / " + adresse + " / " + phone;
    }
    
}
