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
    
    private AuthorDAO adao = new AuthorDAO();

    public AEteEcritDAO() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }

    public List<AEteEcrit> read() {
        ArrayList<AEteEcrit> a = new ArrayList<>();

        Key key = Key.createKey(AEteEcrit.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, key, null, null);

        while (i.hasNext()) {
            Value value = i.next().getValue();
            if (value != null) {
                a.add(new AEteEcrit(value.getValue()));
            }
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

        while (i.hasNext()) {
            KeyValueVersion kvv = i.next();

            if (minorKey.equals(kvv.getKey().getMinorPath().get(0))) {
                Value value = kvv.getValue();
                if (value != null) {
                    a.add(new AEteEcrit(value.getValue()));
                }
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
            Value value = vv2.getValue();
            a = new AEteEcrit(value.getValue());
        }
        return a;
    }

    public boolean exist(String titreArticle, int rang, String minorPath) {
        Key key = Key.createKey(Arrays.asList(AEteEcrit.MAJOR_KEY, titreArticle, String.valueOf(rang)), minorPath);
        return store.get(key) != null;
    }

    public int getRang(String titreArticle, int idAuteur) {
        return getRang(titreArticle, idAuteur, "info");
    }

    public int getRang(String titreArticle, int idAuteur, String minorPath) {
        Key key = Key.createKey(Arrays.asList(AEteEcrit.MAJOR_KEY, titreArticle));

        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);

        int rg = -1;

        while (it.hasNext()) {
            KeyValueVersion kvv = it.next();
            Key k = kvv.getKey();

            if (k.getMinorPath().get(0).equals(minorPath)) {

                List<String> majorPath = k.getMajorPath();
                String rang = majorPath.get(2);

                Value value = kvv.getValue();
                if (value != null) {
                    AEteEcrit a = new AEteEcrit(value.getValue());

                    if (a.getIdAuteur() == idAuteur) {
                        rg = Integer.parseInt(rang);
                        break;
                    }
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

        if (adao.exist(a.getIdAuteur())) {
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
        return create(new AEteEcrit(articleTitre, 1 + getLastRang(articleTitre, minorKey), idAuteur), minorKey);
    }

    public int create(String articleTitre, int idAuteur, int rang) {
        return create(articleTitre, idAuteur, rang, "info");
    }

    public int create(String articleTitre, int idAuteur, int rang, String minorPath) {
        return create(new AEteEcrit(articleTitre, idAuteur, rang), minorPath);
    }

    public int update(String articleTitre, int idAuteur, int newIdAuteur) {
        return update(articleTitre, idAuteur, newIdAuteur, "info");
    }

    public int update(String articleTitre, int idAuteur, int newIdAuteur, String minorPath) {
        if (exist(articleTitre, getRang(articleTitre, idAuteur, minorPath), minorPath)) {
            AEteEcrit a = read(articleTitre, getRang(articleTitre, idAuteur, minorPath), minorPath);
            if (adao.exist(newIdAuteur)) {
                a.setIdAuteur(newIdAuteur);
                store.delete(a.getStoreKey(minorPath));
                store.putIfAbsent(a.getStoreKey(minorPath), a.getStoreValue());
                
                return 0;
            } else {
                return 150;
            }
        } else {
            return 153;
        }
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
        boolean delete = false;
        if (exist(articleTitre, rang, minorPath)) {
            delete = store.delete(read(articleTitre, rang, minorPath).getStoreKey(minorPath));
        }

        return (delete ? 0 : 153);
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
