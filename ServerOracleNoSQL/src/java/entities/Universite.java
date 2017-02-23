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
import java.util.Objects;
import oracle.kv.Key;
import oracle.kv.Value;


/**
 *
 * @author Dehbia Sam
 */
public class Universite {
     private int universiteId;

    public static String MAJOR_KEY = "university";

    private String nom;
    private String adresse;
    
    
    public Universite(int universiteId, String nom, String adresse) {
        this.nom = nom.trim();
        this.adresse = adresse.trim();
        this.universiteId = universiteId;
    }
    
    public Universite(byte[] bytes) {
        String universite = new String(bytes);
        String[] elt = universite.split("/");
        this.universiteId = Integer.parseInt(elt[0]);
        nom = elt[1];
        adresse = elt[2];        
    
    }
    
    public Universite() {
        this(-1,"","");
    }
    /**
     * @return the universiteId
     */
    public int getUniversiteId() {
        return universiteId;
    }

    /**
     * @param universiteId the universiteId to set
     */
    public void setUniversiteId(int universiteId) {
        this.universiteId = universiteId;
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
        return Key.createKey(Arrays.asList(MAJOR_KEY,String.valueOf(universiteId)), minorKey);
    }

    public Value getStoreValue() {
        return Value.createValue(toString().getBytes());
    }
    
    @Override
    public String toString() {
        return universiteId + "/" + nom + "/" + adresse;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.universiteId;
        hash = 79 * hash + Objects.hashCode(this.nom);
        hash = 79 * hash + Objects.hashCode(this.adresse);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Universite other = (Universite) obj;
        if (this.universiteId != other.universiteId) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.adresse, other.adresse)) {
            return false;
        }
        return true;
    }
}
