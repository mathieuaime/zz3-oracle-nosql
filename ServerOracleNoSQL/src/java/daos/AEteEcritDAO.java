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
import oracle.kv.Key;
import oracle.kv.KeyValueVersion;
import oracle.kv.Value;
import oracle.kv.ValueVersion;
import oracle.kv.Version;
import entities.AEteEcrit;
import entities.Author;

/**
 *
 * @author mathieu
 */
public class AEteEcritDAO {

    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public AEteEcritDAO() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }

    public List<AEteEcrit> read() {
        ArrayList<AEteEcrit> a = new ArrayList<>();

        Key key = Key.createKey(AEteEcrit.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, key, null, null);

        AEteEcrit aee;

        while (i.hasNext()) {
            Key k = i.next().getKey();
            ValueVersion valueVersionrecherche = store.get(k);
            Value v = valueVersionrecherche.getValue();
            byte[] bytes2 = v.getValue();
            aee = new AEteEcrit(bytes2);
            a.add(aee);
        }

        return a;
    }

    public List<AEteEcrit> read(String articleTitre) {
        return read(articleTitre, "info");
    }

    public List<AEteEcrit> read(String articleTitre, String minorKey) {
        ArrayList<AEteEcrit> a = new ArrayList<>();

        Key key = Key.createKey(Arrays.asList(AEteEcrit.MAJOR_KEY, articleTitre));

        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, key, null, null);

        AEteEcrit aee;

        while (i.hasNext()) {
            Key k = i.next().getKey();

            if (minorKey.equals(k.getMinorPath().get(0))) {
                ValueVersion valueVersionrecherche = store.get(k);
                Value v = valueVersionrecherche.getValue();
                byte[] bytes2 = v.getValue();
                aee = new AEteEcrit(bytes2);
                a.add(aee);
            }
        }

        return a;
    }
    
    public AEteEcrit read(String titreArticle, int rang) {
        return read(titreArticle, rang, "info");
    }

    public AEteEcrit read(String titreArticle, int rang, String minorPath) {
        Key key = Key.createKey(Arrays.asList(AEteEcrit.MAJOR_KEY, titreArticle, String.valueOf(rang)), minorPath);

        AEteEcrit a = null;

        ValueVersion vv2 = store.get(key);

        if (vv2 != null) {
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            a = new AEteEcrit(bytes2);
        }
        return a;
    }
    
    public int getRang(String titreArticle, int idAuteur) {
        return getRang(titreArticle, idAuteur, "info");
    }

    public int getRang(String titreArticle, int idAuteur, String minorPath) {
        Key key = Key.createKey(Arrays.asList(AEteEcrit.MAJOR_KEY, titreArticle));

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
                AEteEcrit a = new AEteEcrit(bytes2);

                if (a.getIdAuteur()== idAuteur) {
                    rg = Integer.parseInt(rang);
                    break;
                }
            }
        }
        return rg;
    }

    private int getLastRang(String titreArticle, String minorPath) {
        Key key = Key.createKey(Arrays.asList(AEteEcrit.MAJOR_KEY, titreArticle));

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

    public int create(AEteEcrit a) {
        return create(a, "info");
    }

    public int create(AEteEcrit a, String minorPath) {
        
        if (a.getRank() < 0) {
            a.setRank(1 + getLastRang(a.getArticleTitre(), minorPath));
        }
        
        AuthorDAO adao = new AuthorDAO();
        Author read = adao.read(a.getIdAuteur());
        if (read != null) {
            Version putIfAbsent = store.putIfAbsent(a.getStoreKey(minorPath), a.getStoreValue());
            return (putIfAbsent != null ? 0 : 103);
        } else {
            return 150;
        }
    }

    public int create(String articleTitre, int idAuteur) {
        return create(articleTitre, idAuteur, "info");
    }

    public int create(String articleTitre, int idAuteur, String minorKey) {
        AEteEcrit aEcrit = new AEteEcrit(articleTitre, 1 + getLastRang(articleTitre, minorKey), idAuteur);

        return create(aEcrit, minorKey);
    }
    
    public int create(String articleTitre, int idAuteur, int rang) {
        return create(articleTitre, idAuteur, rang, "info");
    }

    public int create(String articleTitre, int idAuteur, int rang, String minorPath) {
        AEteEcrit aEcrit = new AEteEcrit(articleTitre, idAuteur, rang);
        return create(aEcrit, minorPath);
    }

    public int update(String articleTitre, int idAuteur, int newIdAuteur) {
        return update(articleTitre, idAuteur, newIdAuteur, "info");
    }
    
    public int update(String articleTitre, int idAuteur, int newIdAuteur, String minorPath) {
        AEteEcrit a = read(articleTitre, getRang(articleTitre, idAuteur, minorPath), minorPath);
        if (a != null) {
            AuthorDAO adao = new AuthorDAO();
            Author read = adao.read(newIdAuteur);
            if (read != null) {
                a.setIdAuteur(newIdAuteur);
                store.delete(a.getStoreKey(minorPath));
                store.putIfAbsent(a.getStoreKey(minorPath), a.getStoreValue());
            } else {
                return 150;
            }
        } else {
            return 153;
        }

        return 0;
    }

    public int delete(String articleTitre) {
        return delete(articleTitre, "info");
    }

    public int delete(String articleTitre, String minorKey) {
        int result = 153;
        for (AEteEcrit a : read(articleTitre, minorKey)) {
            store.delete(a.getStoreKey(minorKey));
            result = 0;
        }

        return result;
    }
    public int delete(String articleTitre, int rang) {
        return delete(articleTitre, rang, "info");
    }

    public int delete(String articleTitre, int rang, String minorPath) {
        AEteEcrit a = read(articleTitre, rang, minorPath);
        if (a != null) {
            store.delete(a.getStoreKey(minorPath));
        }

        return (a != null ? 0 : 153);
    }

    public void genererTest(int n) {

        for (int i = 0; i < n; i++) {
            create("Le bateau" + (2 * i), i, "demo");
            create("Le bateau" + (1 + 2 * i), i, "demo");
        }
    }

    public void afficherTest(int n) {

        for (int i = 0; i < n; i++) {
            for (AEteEcrit a : read("Le bateau" + (2 * i), "demo")) {
                System.out.println(a);
            }
            for (AEteEcrit a : read("Le bateau" + (2 * i + 1), "demo")) {
                System.out.println(a);
            }
        }
    }

    public void supprimerTest(int n) {

        for (int i = 0; i < n; i += 2) {
            delete("Le bateau" + (2 * i), "demo");
            delete("Le bateau" + (1 + 2 * i), "demo");
        }
    }

}
