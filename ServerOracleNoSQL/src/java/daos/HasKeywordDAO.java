/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import oracle.kv.Direction;
import oracle.kv.KVStore;
import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;
import oracle.kv.ValueVersion;
import oracle.kv.Value;
import oracle.kv.Key;
import oracle.kv.KeyValueVersion;
import oracle.kv.Version;
import entities.HasKeyword;

/**
 *
 * @author mathieu
 */
public class HasKeywordDAO {

    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public HasKeywordDAO() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }

    public List<HasKeyword> read(String titreArticle) {
        return read(titreArticle, "info");
    }

    public List<HasKeyword> read(String titreArticle, String minorPath) {
        Key key = Key.createKey(Arrays.asList(HasKeyword.MAJOR_KEY, titreArticle));

        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);

        ArrayList<HasKeyword> hKeywords = new ArrayList<>();

        while (it.hasNext()) {
            Key k = it.next().getKey();

            if (k.getMinorPath().get(0).equals(minorPath)) {

                ValueVersion vv2 = store.get(k);

                Value value2 = vv2.getValue();
                byte[] bytes2 = value2.getValue();
                HasKeyword a = new HasKeyword(bytes2);
                hKeywords.add(a);
            }
        }

        return hKeywords;
    }

    public HasKeyword read(String titreArticle, int rank) {
        return read(titreArticle, rank, "info");
    }

    public HasKeyword read(String titreArticle, int rank, String minorPath) {
        Key key = Key.createKey(Arrays.asList(HasKeyword.MAJOR_KEY, titreArticle, String.valueOf(rank)), minorPath);

        HasKeyword a = null;

        ValueVersion vv2 = store.get(key);

        if (vv2 != null) {
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            a = new HasKeyword(bytes2);
        }
        return a;
    }
    
    public boolean exist(String titreArticle, int rank, String minorPath) {
        Key key = Key.createKey(Arrays.asList(HasKeyword.MAJOR_KEY, titreArticle, String.valueOf(rank)), minorPath);
        return store.get(key) != null;
    }

    public int getRank(String titreArticle, String keyword) {
        return getRank(titreArticle, keyword, "info");
    }

    public int getRank(String titreArticle, String keyword, String minorPath) {
        Key key = Key.createKey(Arrays.asList(HasKeyword.MAJOR_KEY, titreArticle));

        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);

        int rg = -1;

        while (it.hasNext()) {
            Key k = it.next().getKey();

            if (k.getMinorPath().get(0).equals(minorPath)) {

                List<String> majorPath = k.getMajorPath();
                String rank = majorPath.get(2);

                ValueVersion vv2 = store.get(k);

                Value value2 = vv2.getValue();
                byte[] bytes2 = value2.getValue();
                HasKeyword a = new HasKeyword(bytes2);

                if (a.getKeyword().equals(keyword)) {
                    rg = Integer.parseInt(rank);
                    break;
                }
            }
        }
        return rg;
    }

    private int getLastRank(String titreArticle, String minorPath) {
        Key key = Key.createKey(Arrays.asList(HasKeyword.MAJOR_KEY, titreArticle));

        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);

        int rank = 0;

        while (it.hasNext()) {
            Key k = it.next().getKey();

            if (k.getMinorPath().get(0).equals(minorPath)) {
                rank = Integer.max(rank, Integer.parseInt(k.getMajorPath().get(2)));
            }
        }

        return rank;
    }

    public int create(HasKeyword a) {
        return create(a, "info");
    }

    public int create(HasKeyword a, String minorPath) {
        if (a.getRank() < 0) {
            a.setRank(1 + getLastRank(a.getTitreArticle(), minorPath));
        }

        Version putIfAbsent = store.putIfAbsent(a.getStoreKey(minorPath), a.getStoreValue());

        return (putIfAbsent != null ? 0 : 102);
    }

    public int create(String titreArticle, String keyword) {
        return create(titreArticle, keyword, "info");
    }

    public int create(String titreArticle, String keyword, String minorPath) {
        return create(titreArticle, keyword, 1 + getLastRank(titreArticle, minorPath), minorPath);
    }

    public int create(String titreArticle, String keyword, int rank) {
        return create(titreArticle, keyword, rank, "info");
    }

    public int create(String titreArticle, String keyword, int rank, String minorPath) {
        return create(new HasKeyword(titreArticle, rank, keyword), minorPath);
    }

    public int update(String titreArticle, String keyword, String newKeyword) {
        return update(titreArticle, keyword, newKeyword, "info");
    }

    public int update(String titreArticle, String keyword, String newKeyword, String minorPath) {
        
        if (exist(titreArticle, getRank(titreArticle, keyword, minorPath), minorPath)) {
            HasKeyword a = read(titreArticle, getRank(titreArticle, keyword, minorPath), minorPath);
            a.setKeyword(newKeyword);
            //TODO tester si le nouveau livre existe sinon erreur 401
            store.delete(a.getStoreKey(minorPath));
            store.putIfAbsent(a.getStoreKey(minorPath), a.getStoreValue());
            
            return 0;
        } else {
            return 152;
        }
    }

    public int delete(String titreArticle) {
        return delete(titreArticle, "info");
    }

    public int delete(String titreArticle, String minorPath) {
        int result = 152;
        for (HasKeyword a : read(titreArticle, minorPath)) {
            store.delete(a.getStoreKey(minorPath));
            result = 0;
        }

        return result;
    }

    public int delete(String titreArticle, int rank) {
        return delete(titreArticle, rank, "info");
    }

    public int delete(String titreArticle, int rank, String minorPath) {
        boolean delete = false;
        if (exist(titreArticle, rank, minorPath)) {
            delete = store.delete(read(titreArticle, rank, minorPath).getStoreKey(minorPath));
        }

        return (delete ? 0 : 157);
    }

    public void genererTest(int n) {

        for (int i = 0; i < n; i += 2) {
            create("Le bateau" + 2 * i, "bateau", 1, "demo");
            create("Le bateau" + 2 * i + 1, "bateau", 2, "demo");
        }
    }

    public void afficherTest(int n) {

        for (int i = 0; i < n; i++) {
            HasKeyword a = read("Le bateau" + i, 1, "demo");
            System.out.println(a);
            a = read("Le bateau" + i, 2, "demo");
            System.out.println(a);
        }
    }

    public void supprimerTest(int n) {

        for (int i = 0; i < n; i += 2) {
            delete("Le bateau" + i, 1, "demo");
            delete("Le bateau" + i, 2, "demo");
        }
    }
}
