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
public class Livre implements Serializable {
    /*
     * The id is a unique identifier and is used to construct
     * the Key's major path.
     */
    private int id;

    /*
     * The MAJOR_KEY is used to construct
     * the Key's major path component.
     */
    public static final String MAJOR_KEY = "livre";

    private String titre;
    private String resume;
    private float prix;
    
    public Livre(int id, String titre, String resume, float prix) {
        this.titre = titre;
        this.resume = resume;
        this.prix = prix;
        this.id = id;
    }
    
    public Livre(int id, byte[] bytes) {
        String auteur = new String(bytes);
        String[] elt = auteur.split(";");
        this.id = id;
        titre = elt[0].trim();
        resume = elt[1].trim();        
        prix = Float.parseFloat(elt[2].trim());   
    }
    
    public Livre() {
        this(-1,null,null,-1);
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
    
    public Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY,String.valueOf(id)), minorKey);
    }

    public Value getStoreValue() {

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try {
             
            dataOutputStream.writeUTF(titre);            
            dataOutputStream.writeUTF(";");
            dataOutputStream.writeUTF(resume);
            dataOutputStream.writeUTF(";");
            dataOutputStream.writeUTF(String.valueOf(prix));
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Value.createValue(byteArrayOutputStream.toByteArray());
    }
    
    @Override
    public String toString() {
        return id + "/" + titre + "/" + resume + "/" + prix;
    }
    
}
