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
 * @author Dehbia Sam
 */
public class Laboratoire {
     private int laboratoireId;

    public static String MAJOR_KEY = "laboratoire";

    private String nom;
    private String adresse;
    
    
    public Laboratoire(int laboratoireId, String nom, String adresse) {
        this.nom = nom.trim();
        this.adresse = adresse.trim();
        this.laboratoireId = laboratoireId;
    }
    
    public Laboratoire(int laboratoireId, byte[] bytes) {
        String laboratoire = new String(bytes);
        String[] elt = laboratoire.split(";");
        this.laboratoireId = laboratoireId;
        nom = elt[0].trim();
        adresse = elt[1].trim();        
    
    }
    
    public Laboratoire() {
        this(-1,"","");
    }
    /**
     * @return the laboratoireId
     */
    public int getLaboratoireId() {
        return laboratoireId;
    }

    /**
     * @param laboratoireId the laboratoireId to set
     */
    public void setLaboratoireId(int laboratoireId) {
        this.laboratoireId = laboratoireId;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @param adresse the adresse to set
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
   
        public Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY,String.valueOf(laboratoireId)), minorKey);
    }

    public Value getStoreValue() {

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try {
            dataOutputStream.writeUTF(nom);            
            dataOutputStream.writeUTF(";");
            dataOutputStream.writeUTF(adresse);
           
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Value.createValue(byteArrayOutputStream.toByteArray());
    }
    
    @Override
    public String toString() {
        return laboratoireId + "/" + nom + "/" + adresse;
    }
}

