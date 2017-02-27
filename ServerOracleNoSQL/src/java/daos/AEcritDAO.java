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
import entities.AEcrit;
import entities.Article;
import java.text.ParseException;

/**
 *
 * @author mathieu
 */
public class AEcritDAO {

    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public AEcritDAO() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }

    public List<AEcrit> read(String auteurNom) {
        return read(auteurNom, "info");
    }

    public List<AEcrit> read(String auteurNom, String minorPath) {
        Key key = Key.createKey(Arrays.asList(AEcrit.MAJOR_KEY, auteurNom));

        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);

        ArrayList<AEcrit> ecrits = new ArrayList<>();

        while (it.hasNext()) {
            Key k = it.next().getKey();

            if (k.getMinorPath().get(0).equals(minorPath)) {

                ValueVersion vv2 = store.get(k);

                Value value2 = vv2.getValue();
                byte[] bytes2 = value2.getValue();
                AEcrit a = new AEcrit(bytes2);
                ecrits.add(a);
            }
        }

        return ecrits;
    }

    public AEcrit read(String auteurNom, int rang) {
        return read(auteurNom, rang, "info");
    }

    public AEcrit read(String auteurNom, int rang, String minorPath) {
        Key key = Key.createKey(Arrays.asList(AEcrit.MAJOR_KEY, auteurNom, String.valueOf(rang)), minorPath);

        AEcrit a = null;

        ValueVersion vv2 = store.get(key);

        if (vv2 != null) {
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            a = new AEcrit(bytes2);
        }
        return a;
    }

    public int getRang(String auteurNom, int idArticle) {
        return getRang(auteurNom, idArticle, "info");
    }

    public int getRang(String auteurNom, int idArticle, String minorPath) {
        Key key = Key.createKey(Arrays.asList(AEcrit.MAJOR_KEY, auteurNom));

        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);

        int rg = -1;

        while (it.hasNext()) {
            Key k = it.next().getKey();

            if (k.getMinorPath().get(0).equals(minorPath)) {

                List<String> majorPath = k.getMajorPath();
                String rang = majorPath.get(2);

                ValueVersion vv2 = store.get(k);

                Value value2 = vv2.getValue();
                byte[] bytes2 = value2.getValue();
                AEcrit a = new AEcrit(bytes2);

                if (a.getIdArticle() == idArticle) {
                    rg = Integer.parseInt(rang);
                    break;
                }
            }
        }
        return rg;
    }

    private int getLastRang(String auteurNom, String minorPath) {
        Key key = Key.createKey(Arrays.asList(AEcrit.MAJOR_KEY, auteurNom));

        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);

        int rang = 0;

        while (it.hasNext()) {
            Key k = it.next().getKey();

            if (k.getMinorPath().get(0).equals(minorPath)) {
                rang = Integer.max(rang, Integer.parseInt(k.getMajorPath().get(2)));
            }
        }

        return rang;
    }

    public int create(AEcrit a) throws ParseException {
        return create(a, "info");
    }

    public int create(AEcrit a, String minorPath) throws ParseException {
        if (a.getRank() < 0) {
            a.setRank(1 + getLastRang(a.getAuteurNom(), minorPath));
        }

        ArticleDAO adao = new ArticleDAO();
        Article read = adao.read(a.getIdArticle());
        if (read != null) {
            Version putIfAbsent = store.putIfAbsent(a.getStoreKey(minorPath), a.getStoreValue());
            return (putIfAbsent != null ? 0 : 102);
        } else {
            return 151;
        }
    }

    public int create(String auteurNom, int idArticle) throws ParseException {
        return create(auteurNom, idArticle, "info");
    }

    public int create(String auteurNom, int idArticle, String minorPath) throws ParseException {
        return create(auteurNom, idArticle, 1 + getLastRang(auteurNom, minorPath), minorPath);
    }

    public int create(String auteurNom, int idArticle, int rang) throws ParseException {
        return create(auteurNom, idArticle, rang, "info");
    }

    public int create(String auteurNom, int idArticle, int rang, String minorPath) throws ParseException {
        AEcrit aEcrit = new AEcrit(auteurNom, idArticle, rang);
        return create(aEcrit, minorPath);
    }

    public int update(String auteurNom, int idArticle, int newIdArticle) throws ParseException {
        return update(auteurNom, idArticle, newIdArticle, "info");
    }

    public int update(String auteurNom, int idArticle, int newIdArticle, String minorPath) throws ParseException {
        AEcrit a = read(auteurNom, getRang(auteurNom, idArticle, minorPath), minorPath);
        if (a != null) {
            ArticleDAO adao = new ArticleDAO();
            Article read = adao.read(newIdArticle);
            if (read != null) {
                a.setIdArticle(newIdArticle);
                store.delete(a.getStoreKey(minorPath));
                store.putIfAbsent(a.getStoreKey(minorPath), a.getStoreValue());
            } else {
                return 151;
            }
        } else {
            return 152;
        }

        return 0;
    }

    public int delete(String auteurNom) {
        return delete(auteurNom, "info");
    }

    public int delete(String auteurNom, String minorPath) {
        int result = 152;
        for (AEcrit a : read(auteurNom, minorPath)) {
            store.delete(a.getStoreKey(minorPath));
            result = 0;
        }

        return result;
    }

    public int delete(String auteurNom, int rang) {
        return delete(auteurNom, rang, "info");
    }

    public int delete(String auteurNom, int rang, String minorPath) {
        AEcrit a = read(auteurNom, rang, minorPath);
        if (a != null) {
            store.delete(a.getStoreKey(minorPath));
        }

        return (a != null ? 0 : 152);
    }

    public void genererTest(int n) throws ParseException {

        for (int i = 0; i < n; i += 2) {
            create("AimÃ©" + i, (2 * i), 1, "demo");
            create("AimÃ©" + i, (1 + 2 * i), 2, "demo");
        }
    }

    public void afficherTest(int n) {

        for (int i = 0; i < n; i++) {
            AEcrit a = read("AimÃ©" + i, 1, "demo");
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
