/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import entities.Article;
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
import entities.Keyword;
import java.text.ParseException;

/**
 *
 * @author mathieu
 */
public class KeywordDAO {

    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public KeywordDAO() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }

    public List<Keyword> read(String keyword) {
        return read(keyword, "info");
    }

    public List<Keyword> read(String keyword, String minorPath) {
        Key key = Key.createKey(Arrays.asList(Keyword.MAJOR_KEY, keyword));

        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);

        ArrayList<Keyword> ecrits = new ArrayList<>();

        while (it.hasNext()) {
            Key k = it.next().getKey();

            if (k.getMinorPath().get(0).equals(minorPath)) {

                ValueVersion vv2 = store.get(k);

                Value value2 = vv2.getValue();
                byte[] bytes2 = value2.getValue();
                Keyword a = new Keyword(bytes2);
                ecrits.add(a);
            }
        }

        return ecrits;
    }

    public Keyword read(String keyword, int rank) {
        return read(keyword, rank, "info");
    }

    public Keyword read(String keyword, int rank, String minorPath) {
        Key key = Key.createKey(Arrays.asList(Keyword.MAJOR_KEY, keyword, String.valueOf(rank)), minorPath);

        Keyword a = null;

        ValueVersion vv2 = store.get(key);

        if (vv2 != null) {
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            a = new Keyword(bytes2);
        }
        return a;
    }

    public int getRank(String keyword, int idArticle) {
        return getRank(keyword, idArticle, "info");
    }

    public int getRank(String keyword, int idArticle, String minorPath) {
        Key key = Key.createKey(Arrays.asList(Keyword.MAJOR_KEY, keyword));

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
                Keyword a = new Keyword(bytes2);

                if (a.getIdArticle() == idArticle) {
                    rg = Integer.parseInt(rank);
                    break;
                }
            }
        }
        return rg;
    }

    private int getLastRank(String keyword, String minorPath) {
        Key key = Key.createKey(Arrays.asList(Keyword.MAJOR_KEY, keyword));

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

    public int create(Keyword a) throws ParseException {
        return create(a, "info");
    }

    public int create(Keyword a, String minorPath) throws ParseException {
        if (a.getRank() < 0) {
            a.setRank(1 + getLastRank(a.getKeyword(), minorPath));
        }

        ArticleDAO adao = new ArticleDAO();
        Article read = adao.read(a.getIdArticle());
        if (read != null) {
            Version putIfAbsent = store.putIfAbsent(a.getStoreKey(minorPath), a.getStoreValue());
            return (putIfAbsent != null ? 0 : 107);
        } else {
            return 151;
        }
    }

    public int create(String keyword, int idArticle) throws ParseException {
        return create(keyword, idArticle, "info");
    }

    public int create(String keyword, int idArticle, String minorPath) throws ParseException {
        return create(keyword, idArticle, 1 + getLastRank(keyword, minorPath), minorPath);
    }

    public int create(String keyword, int idArticle, int rank) throws ParseException {
        return create(keyword, idArticle, rank, "info");
    }

    public int create(String keyword, int idArticle, int rank, String minorPath) throws ParseException {
        Keyword aEcrit = new Keyword(keyword, idArticle, rank);
        return create(aEcrit, minorPath);
    }

    public int update(String keyword, int idArticle, int newIdArticle) throws ParseException {
        return update(keyword, idArticle, newIdArticle, "info");
    }

    public int update(String keyword, int idArticle, int newIdArticle, String minorPath) throws ParseException {
        Keyword a = read(keyword, getRank(keyword, idArticle, minorPath), minorPath);
        if (a != null) {
            ArticleDAO adao = new ArticleDAO();
            Article read = adao.read(a.getIdArticle());
            if (read != null) {
                a.setIdArticle(newIdArticle);
                store.delete(a.getStoreKey(minorPath));
                store.putIfAbsent(a.getStoreKey(minorPath), a.getStoreValue());
                return 0;
            } else {
                return 151;
            }
        } else {
            return 157;
        }
    }

    public int delete(String keyword) {
        return delete(keyword, "info");
    }

    public int delete(String keyword, String minorPath) {
        int result = 157;
        for (Keyword a : read(keyword, minorPath)) {
            store.delete(a.getStoreKey(minorPath));
            result = 0;
        }

        return result;
    }

    public int delete(String keyword, int rank) {
        return delete(keyword, rank, "info");
    }

    public int delete(String keyword, int rank, String minorPath) {
        Keyword a = read(keyword, rank, minorPath);
        if (a != null) {
            store.delete(a.getStoreKey(minorPath));
        }

        return (a != null ? 0 : 157);
    }

    public void genererTest(int n) throws ParseException {

        for (int i = 0; i < n; i += 2) {
            create("AimÃ©" + i, (2 * i), 1, "demo");
            create("AimÃ©" + i, (1 + 2 * i), 2, "demo");
        }
    }

    public void afficherTest(int n) {

        for (int i = 0; i < n; i++) {
            Keyword a = read("AimÃ©" + i, 1, "demo");
            System.out.println(a);
            a = read("AimÃ©" + i, 2, "demo");
            System.out.println(a);
        }
    }

    public void supprimerTest(int n) {

        for (int i = 0; i < n; i += 2) {
            delete("AimÃ©" + i, 1, "demo");
            delete("AimÃ©" + i, 2, "demo");
        }
    }
}
