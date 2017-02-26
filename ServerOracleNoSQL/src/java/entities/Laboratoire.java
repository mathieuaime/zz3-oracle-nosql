/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Arrays;
import java.util.Objects;
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
        this(-1, "", "");
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
        return Key.createKey(Arrays.asList(MAJOR_KEY, String.valueOf(laboratoireId)), minorKey);
    }

    public Value getStoreValue() {
        return Value.createValue(toString().getBytes());
    }

    @Override
    public String toString() {
        return laboratoireId + "/"
                + nom + "/"
                + adresse;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.laboratoireId;
        hash = 47 * hash + Objects.hashCode(this.nom);
        hash = 47 * hash + Objects.hashCode(this.adresse);
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
        final Laboratoire other = (Laboratoire) obj;
        if (this.laboratoireId != other.laboratoireId) {
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
