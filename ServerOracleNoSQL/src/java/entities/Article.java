/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import oracle.kv.Key;
import oracle.kv.Value;

/**
 *
 * @author mathieu
 */
public class Article implements Serializable {
    /*
     * The id is a unique identifier and is used to construct
     * the Key's major path.
     */
    private int id;

    /*
     * The MAJOR_KEY is used to construct
     * the Key's major path component.
     */
    public static final String MAJOR_KEY = "article";
    
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MMMM/yyyy");

    private String titre;
    private String resume;
    private float prix;
    private Date receptionDate;
    private Date acceptationDate;
    private Date publicationDate;
    
    public Article(int id, String titre, String resume, float prix) {
        this(id, titre, resume, prix, null, null, null);
    }
    
    public Article(int id, String titre, String resume, float prix, Date receptionDate, Date acceptationDate, Date publicationDate) {
        this.titre = titre;
        this.resume = resume;
        this.prix = prix;
        this.id = id;
        this.receptionDate = receptionDate;
        this.acceptationDate = acceptationDate;
        this.publicationDate = publicationDate;
    }
    
    public Article(byte[] bytes) throws ParseException {
        
        String auteur = new String(bytes);
        String[] elt = auteur.split("/");
        this.id = Integer.parseInt(elt[0]);
        titre = elt[1];
        resume = elt[2];        
        prix = Float.parseFloat(elt[3]);
        if (elt.length > 5 && elt[4] != null) receptionDate = DATE_FORMAT.parse(elt[4]);
        if (elt.length > 6 && elt[5] != null) acceptationDate = DATE_FORMAT.parse(elt[5]);
        if (elt.length > 7 && elt[6] != null) publicationDate = DATE_FORMAT.parse(elt[6]);
    }
    
    public Article() {
        this(-1,null,null,-1, null, null, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Date getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(Date receptionDate) {
        this.receptionDate = receptionDate;
    }

    public Date getAcceptationDate() {
        return acceptationDate;
    }

    public void setAcceptationDate(Date acceptationDate) {
        this.acceptationDate = acceptationDate;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
    
    public Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY,String.valueOf(id)), minorKey);
    }

    public Value getStoreValue() {
        return Value.createValue(toString().getBytes());
    }
    
    @Override
    public String toString() {
        return  id + "/" + 
                titre + "/" + 
                resume + "/" + 
                prix + "/" + 
                (receptionDate != null ? DATE_FORMAT.format(receptionDate) : "") + "/" + 
                (acceptationDate != null ? DATE_FORMAT.format(acceptationDate) : "") + "/" + 
                (publicationDate != null ? DATE_FORMAT.format(publicationDate) : "");
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.id;
        hash = 19 * hash + Objects.hashCode(this.titre);
        hash = 19 * hash + Objects.hashCode(this.resume);
        hash = 19 * hash + Float.floatToIntBits(this.prix);
        hash = 19 * hash + Objects.hashCode(this.receptionDate);
        hash = 19 * hash + Objects.hashCode(this.acceptationDate);
        hash = 19 * hash + Objects.hashCode(this.publicationDate);
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
        final Article other = (Article) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Float.floatToIntBits(this.prix) != Float.floatToIntBits(other.prix)) {
            return false;
        }
        if (!Objects.equals(this.titre, other.titre)) {
            return false;
        }
        if (!Objects.equals(this.resume, other.resume)) {
            return false;
        }
        if (!Objects.equals(this.receptionDate, other.receptionDate)) {
            return false;
        }
        if (!Objects.equals(this.acceptationDate, other.acceptationDate)) {
            return false;
        }
        if (!Objects.equals(this.publicationDate, other.publicationDate)) {
            return false;
        }
        return true;
    }
    
}
