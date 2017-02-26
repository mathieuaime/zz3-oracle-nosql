/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import entities.Laboratoire;
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
import entities.Rattache;
import entities.Universite;
import oracle.kv.Version;

/**
 *
 * @author mathieu
 */
public class RattacheDAO {

    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public RattacheDAO() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }

    public ArrayList<Rattache> read(String type) {
        Key key = Key.createKey(Arrays.asList(Rattache.MAJOR_KEY, type));

        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);

        ArrayList<Rattache> rattaches = new ArrayList<>();

        while (it.hasNext()) {
            Key k = it.next().getKey();

            ValueVersion vv2 = store.get(k);

            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            Rattache a = new Rattache(bytes2);
            rattaches.add(a);
        }

        return rattaches;
    }

    public ArrayList<Rattache> read(String type, String value) {
        Key key = Key.createKey(Arrays.asList(Rattache.MAJOR_KEY, type, value));

        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);

        ArrayList<Rattache> rattaches = new ArrayList<>();

        while (it.hasNext()) {
            Key k = it.next().getKey();

            ValueVersion vv2 = store.get(k);

            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            Rattache a = new Rattache(bytes2);
            rattaches.add(a);
        }

        return rattaches;
    }

    public Rattache read(String type, String value, int rang) {
        Rattache r = null;
        Key key = Key.createKey(Arrays.asList(Rattache.MAJOR_KEY, type, value, String.valueOf(rang)), "info");

        ValueVersion vv2 = store.get(key);

        if (vv2 != null) {
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            r = new Rattache(bytes2);
        }

        return r;
    }

    public int create(Rattache a) {
        Version putIfAbsent = store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());
        return (putIfAbsent != null ? 0 : 106);
    }

    public int create(String type, String value, int idAuteur) {
        Rattache rattache = new Rattache(value, type, 1 + getLastRang(type, value, "info"), idAuteur);
        return create(rattache);
    }

    public int create(String type, String value, int idAuteur, int rang) {
        Rattache rattache = new Rattache(value, type, rang, idAuteur);
        return create(rattache);
    }

    public int getRang(String type, String value, int idAuteur) {
        return getRang(type, value, idAuteur, "info");
    }

    public int getRang(String type, String value, int idAuteur, String minorPath) {
        Key key = Key.createKey(Arrays.asList(type, value));

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
                Rattache a = new Rattache(bytes2);

                if (a.getIdAuteur() == idAuteur) {
                    rg = Integer.parseInt(rang);
                    break;
                }
            }
        }
        return rg;
    }

    private int getLastRang(String type, String value, String minorPath) {
        Key key = Key.createKey(Arrays.asList(type, value));

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

    public int update(String type, String value, int rang, int idAuteur) {
        Rattache a = read(type, value, rang);
        if (a != null) {
            if (idAuteur > 0) {
                a.setIdAuteur(idAuteur);
            }
            store.delete(a.getStoreKey("info"));
            store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());
        }

        return (a != null ? 0 : 156);
    }

    public int update(String type, String value, int rang, Rattache r) {
        return update(type, value, rang, r.getIdAuteur());
    }

    public int delete(String type) {
        boolean delete = false;

        for (Rattache a : read(type)) {
            delete = store.delete(a.getStoreKey("info"));
        }

        return (delete ? 0 : 156);
    }

    public int delete(String type, String value) {
        boolean delete = false;

        for (Rattache a : read(type, value)) {
            delete = store.delete(a.getStoreKey("info"));
        }

        return (delete ? 0 : 156);
    }

    public int delete(String type, String value, int rang) {
        Rattache a = read(type, value, rang);
        boolean delete = false;
        if (a != null) {
            delete = store.delete(a.getStoreKey("info"));
        }
        return (delete ? 0 : 156);
    }

    public void genererTest(int n) {

        for (int i = 0; i < n; i += 2) {
            create(Laboratoire.MAJOR_KEY, "Laboratoire" + i, i, 1);
            create(Universite.MAJOR_KEY, "Universite" + i, i, 1);
        }
    }

    public void afficherTest(int n) {

        for (int i = 0; i < n; i++) {
            Rattache a = read(Laboratoire.MAJOR_KEY, "Laboratoire" + i, 1);
            System.out.println(a);
            a = read(Universite.MAJOR_KEY, "Universite" + i, 1);
            System.out.println(a);
        }
    }

    public void supprimerTest(int n) {
        delete(Laboratoire.MAJOR_KEY);
        delete(Universite.MAJOR_KEY);
    }
}
