/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

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

    public static String MAJOR_KEY = "laboratory";

    private String nom;
    private String adresse;
    
    
    public Laboratoire(int laboratoireId, String nom, String adresse) {
        this.nom = nom.trim();
        this.adresse = adresse.trim();
        this.laboratoireId = laboratoireId;
    }
    
    public Laboratoire(byte[] bytes) {
        String laboratoire = new String(bytes);
        String[] elt = laboratoire.split("/");
        this.laboratoireId = Integer.parseInt(elt[0]);
        nom = elt[1];
        adresse = elt[2];        
    
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
        return Value.createValue(toString().getBytes());
    }
    
    @Override
    public String toString() {
        return laboratoireId + "/" + nom + "/" + adresse;
    }
}

