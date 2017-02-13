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
public class Keyword {
    
    //key
    private String keyword;
    private int rank;
    
    //value
    private int idArticle;
    
    public static final String MAJOR_KEY = "keyword";
    
    public Keyword(String keyword, int rank, int idArticle) {
        this.keyword = keyword;
        this.rank = rank;
        this.idArticle = idArticle;
    }
    
    public Keyword(byte[] bytes) {        
        String keyw = new String(bytes);
        String[] elt = keyw.split("/");
        
        this.keyword = elt[0];
        this.rank = Integer.parseInt(elt[1]);
        this.idArticle = Integer.parseInt(elt[2]);
    }
    
    public Keyword() {
        this(null, -1, -1);
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }
    
    public Key getStoreKey(String minorKey) {
        return Key.createKey(Arrays.asList(MAJOR_KEY,keyword,String.valueOf(rank)), minorKey);
    }

    public Value getStoreValue() {
        return Value.createValue(toString().getBytes());
    }
    
    @Override
    public String toString() {
        return keyword + "/" + rank + "/" + idArticle;
    }
}
