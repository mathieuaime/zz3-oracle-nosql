/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql;

import com.sun.org.apache.xpath.internal.compiler.Keywords;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import oracle.kv.Key;
import oracle.kv.Value;
import static oracle.nosql.Auteur.MAJOR_KEY;

/**
 *
 * @author mathieu
 */
public class Livre {
    /*
     * The livreId is a unique identifier and is used to construct
     * the Key's major path.
     */
    private final String livreId;

    /*
     * The MAJOR_KEY is used to construct
     * the Key's major path component.
     */
    static final String MAJOR_KEY = "livre";

    private String titre;
    private String resume;
    private ArrayList<Auteur> auteurs = new ArrayList();
    private ArrayList<String> keywords = new ArrayList();
    
    Livre(String livreId) {
        this.livreId = livreId;
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

    public ArrayList<Auteur> getAuteurs() {
        return auteurs;
    }
    
    public void addAuteur(Auteur auteur) {
        auteurs.add(auteur);
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }
    
    public void addKeyword(String keyword) {
        keywords.add(keyword);
    }    
    
    Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY,livreId), minorKey);
    }

    Value getStoreValue() {

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try {
             
            dataOutputStream.writeUTF(titre);            
            dataOutputStream.writeUTF(";");
            dataOutputStream.writeUTF(resume);
            for (Auteur auteur : auteurs) {
                dataOutputStream.writeUTF(";");
                dataOutputStream.writeUTF(auteur.toString());                
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Value.createValue(byteArrayOutputStream.toByteArray());
    }
    
    @Override
    public String toString() {
        return livreId + " / " + titre + " / " + resume;
    }
    
}
