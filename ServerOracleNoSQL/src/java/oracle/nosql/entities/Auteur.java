/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql.entities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import oracle.kv.Key;
import oracle.kv.Value;

/**
 *
 * @author mathieu
 */
public class Auteur implements Serializable {
    /*
     * The auteurId is a unique identifier and is used to construct
     * the Key's major path.
     */
    private int id;

    /*
     * The MAJOR_KEY is used to construct
     * the Key's major path component.
     */
    public static final String MAJOR_KEY = "auteur";

    private String nom;
    private String prenom;
    private String adresse;
    private String phone;
    
    public Auteur(int id, String nom, String prenom, String adresse, String phone) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.phone = phone;
        this.id = id;
    }
    
    public Auteur(int id, byte[] bytes) {
        this.id = id;
        String auteur = new String(bytes);
        String[] elt = auteur.split(";");
        nom = elt[0].trim();
        prenom = elt[1].trim();        
        adresse = elt[2].trim();        
        phone = elt[3].trim();        
    }
    
    public Auteur() {
        this(-1,null,null,null,null);
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
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

    public Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY,String.valueOf(id)), minorKey);
    }

    public Value getStoreValue() {

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
        return id + "/" + nom + "/" + prenom + "/" + adresse + "/" + phone;
    }
    
}
