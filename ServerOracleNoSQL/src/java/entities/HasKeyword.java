/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Arrays;
import oracle.kv.Key;
import oracle.kv.Value;

/**
 *
 * @author Mathieu
 */
public class HasKeyword {
    
    //key
    private String titreArticle;
    private int rank;
    
    //value
    private String keyword;
    
    public static final String MAJOR_KEY = "has_keyword";
    
    public HasKeyword(String titreArticle, int rank, String keyword) {
        this.titreArticle = titreArticle;
        this.rank = rank;
        this.keyword = keyword;
    }
    
    public HasKeyword(byte[] bytes) {        
        String keyw = new String(bytes);
        String[] elt = keyw.split("/");
        
        this.titreArticle = elt[0];
        this.rank = Integer.parseInt(elt[1]);
        this.keyword = elt[2];
    }
    
    public HasKeyword() {
        this(null, -1, null);
    }

    public String getTitreArticle() {
        return titreArticle;
    }

    public void setTitreArticle(String titreArticle) {
        this.titreArticle = titreArticle;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    public Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY,titreArticle,String.valueOf(rank)), minorKey);
    }

    public Value getStoreValue() {
        return Value.createValue(toString().getBytes());
    }
    
    @Override
    public String toString() {
        return titreArticle + "/" + rank + "/" + keyword;
    }
}
