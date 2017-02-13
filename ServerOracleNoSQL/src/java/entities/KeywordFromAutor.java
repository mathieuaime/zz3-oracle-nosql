package entities;

import java.util.Arrays;
import oracle.kv.Key;
import oracle.kv.Value;

/**
 *
 * @author Mathieu
 */
public class KeywordFromAutor {
    
    //key
    private String autor;
    private int rank;
    
    //value
    private String keyword;
    
    public static final String MAJOR_KEY = "keyword_from_autor";
    
    public KeywordFromAutor(String autor, int rank, String keyword) {
        this.autor = autor;
        this.rank = rank;
        this.keyword = keyword;
    }
    
    public KeywordFromAutor(byte[] bytes) {        
        String keyw = new String(bytes);
        String[] elt = keyw.split("/");
        
        this.autor = elt[0];
        this.rank = Integer.parseInt(elt[1]);
        this.keyword = elt[1];
    }
    
    public KeywordFromAutor() {
        this(null, -1, null);
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
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
        return Key.createKey(Arrays.asList(MAJOR_KEY,autor,String.valueOf(rank)), minorKey);
    }

    public Value getStoreValue() {
        return Value.createValue(toString().getBytes());
    }
    
    @Override
    public String toString() {
        return autor + "/" + rank + "/" + keyword;
    }
}
