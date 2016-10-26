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
public class Livre {
    /*
     * The livreId is a unique identifier and is used to construct
     * the Key's major path.
     */
    private static int livreId = 1;

    /*
     * The MAJOR_KEY is used to construct
     * the Key's major path component.
     */
    public static final String MAJOR_KEY = "livre";

    private String titre;
    private String resume;
    private float prix;
    
    public Livre(String titre, String resume, float prix) {
        this.titre = titre;
        this.resume = resume;
        this.prix = prix;
        ++livreId;
    }
    
    public Livre(int livreId, byte[] bytes) {
        String auteur = new String(bytes);
        String[] elt = auteur.split(";");
        Livre.livreId = livreId;
        titre = elt[0];
        resume = elt[1];        
        prix = Float.parseFloat(elt[2]);   
    }

    public static int getLivreId() {
        return livreId;
    }

    public static void setLivreId(int livreId) {
        Livre.livreId = livreId;
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
        return Key.createKey(Arrays.asList(MAJOR_KEY,String.valueOf(livreId)), minorKey);
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
        return livreId + " / " + titre + " / " + resume + " / " + prix;
    }
    
}
