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
public class AEteEcrit {
    /*
     * The MAJOR_KEY is used to construct
     * the Key's major path component.
     */
    public static final String MAJOR_KEY = "a_ete_ecrit";

    private int auteurId;
    private int livreId;

    public AEteEcrit(int auteurId, int livreId) {
        this.auteurId = auteurId;
        this.livreId = livreId;
    }
    
    public AEteEcrit(int livreId, byte[] bytes) {
        this.livreId = livreId;
        
        String auteur = new String(bytes);
        
        this.auteurId = Integer.parseInt(auteur);
    }

    public int getAuteurId() {
        return auteurId;
    }

    public void setAuteurId(int auteurId) {
        this.auteurId = auteurId;
    }

    public int getLivreId() {
        return livreId;
    }

    public void setLivreId(int livreId) {
        this.livreId = livreId;
    }    
    
    public Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY,String.valueOf(livreId)), minorKey);
    }

    public Value getStoreValue() {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try {
            dataOutputStream.writeUTF(String.valueOf(auteurId)); 
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Value.createValue(byteArrayOutputStream.toByteArray());
    }
    
    @Override
    public String toString() {
        return livreId + " / " + auteurId;
    }
    
}
