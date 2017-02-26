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

        Iterator<KeyValueVersion> i = store.multiGetIterator(Direction.FORWARD, 0, key, null, null);

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

    public int create(AEteEcrit a) {
        return create(a, "info");
    }

    public int create(AEteEcrit a, String minorKey) {
        AuthorDAO adao = new AuthorDAO();
        Author read = adao.read(a.getIdAuteur());
        if (read != null) {
            Version putIfAbsent = store.putIfAbsent(a.getStoreKey(minorKey), a.getStoreValue());
            return (putIfAbsent != null ? 0 : 103);
        } else {
            return 150;
        }
    }

    public int create(String articleTitre, int idAuteur) {
        return create(articleTitre, idAuteur, "info");
    }

    public int create(String articleTitre, int idAuteur, String minorKey) {
        AEteEcrit aEcrit = new AEteEcrit(articleTitre, idAuteur);

        return create(aEcrit, minorKey);
    }

    public int update(String articleTitre, int idAuteur, int newIdAuteur) {
        return update(articleTitre, idAuteur, newIdAuteur, "info");
    }

    public int update(String articleTitre, int idAuteur, int newIdAuteur, String minorKey) {
        int result = 153;

        for (AEteEcrit a : read(articleTitre, minorKey)) {
            if (a.getIdAuteur() == idAuteur) {
                AuthorDAO adao = new AuthorDAO();
                Author read = adao.read(newIdAuteur);
                if (read != null) {
                    a.setIdAuteur(newIdAuteur);
                    store.delete(a.getStoreKey(minorKey));
                    store.putIfAbsent(a.getStoreKey(minorKey), a.getStoreValue());
                    result = 0;
                } else {
                    result = 150;
                }
            }
        }

        return result;
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

    public int delete(String articleTitre, int auteurId) {
        return delete(articleTitre, auteurId, "info");
    }

    public int delete(String articleTitre, int auteurId, String minorKey) {
        int result = 153;
        for (AEteEcrit a : read(articleTitre, minorKey)) {
            if (a.getIdAuteur() == auteurId) {
                store.delete(a.getStoreKey(minorKey));
                result = 0;
            }
        }

        return result;
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
