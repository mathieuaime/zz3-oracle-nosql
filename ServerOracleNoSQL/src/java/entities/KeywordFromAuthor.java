package entities;

import java.io.Serializable;
import java.util.Arrays;
import oracle.kv.Key;
import oracle.kv.Value;

/**
 *
 * @author Mathieu
 */
public class KeywordFromAuthor implements Serializable {

    //key
    private String author;
    private int rank;

    //value
    private String keyword;

    public static final String MAJOR_KEY = "keyword_from_author";

    public KeywordFromAuthor(String author, int rank, String keyword) {
        this.author = author;
        this.rank = rank;
        this.keyword = keyword;
    }

    public KeywordFromAuthor(byte[] bytes) {
        String keyw = new String(bytes);
        String[] elt = keyw.split("/");

        this.author = elt[0];
        this.rank = Integer.parseInt(elt[1]);
        this.keyword = elt[1];
    }

    public KeywordFromAuthor() {
        this(null, -1, null);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
        return Key.createKey(Arrays.asList(MAJOR_KEY, author, String.valueOf(rank)), minorKey);
    }

    public Value getStoreValue() {
        return Value.createValue(toString().getBytes());
    }

    @Override
    public String toString() {
        return author + "/"
                + rank + "/"
                + keyword;
    }
}
