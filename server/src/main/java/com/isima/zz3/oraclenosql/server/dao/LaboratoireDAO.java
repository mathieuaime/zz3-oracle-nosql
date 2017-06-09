package com.isima.zz3.oraclenosql.server.dao;

import java.util.Arrays;
import java.util.Iterator;
import oracle.kv.Direction;
import oracle.kv.KVStore;
import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;
import oracle.kv.Key;
import oracle.kv.KeyValueVersion;
import oracle.kv.Value;
import oracle.kv.ValueVersion;
import com.isima.zz3.oraclenosql.server.model.Laboratory;
import java.util.ArrayList;
import java.util.List;
import oracle.kv.Version;

public class LaboratoireDAO {

    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public LaboratoireDAO() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }

    public List<Laboratory> read() {
        Key myKey2 = Key.createKey(Laboratory.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);

        List<Laboratory> laboratoires = new ArrayList<>();

        while (i.hasNext()) {
            Key k = i.next().getKey();

            ValueVersion valueVersionrecherche = store.get(k);
            Value v = valueVersionrecherche.getValue();
            byte[] bytes2 = v.getValue();
            Laboratory l = new Laboratory(bytes2);
            laboratoires.add(l);
        }

        return laboratoires;
    }

    public Laboratory read(String nom) {
        Key myKey2 = Key.createKey(Laboratory.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);

        Laboratory laboratoire = new Laboratory();

        while (i.hasNext()) {
            Key k = i.next().getKey();

            ValueVersion valueVersionrecherche = store.get(k);
            Value v = valueVersionrecherche.getValue();
            byte[] bytes2 = v.getValue();
            Laboratory a = new Laboratory(bytes2);
            String t = a.getNom();

            if (t.equals(nom)) {
                laboratoire = a;
                break;
            }
        }

        return laboratoire;

    }

    public Laboratory read(int laboratoireId) {
        Laboratory l = null;
        Key key = Key.createKey(Arrays.asList(Laboratory.MAJOR_KEY, String.valueOf(laboratoireId)), "info");

        ValueVersion vv2 = store.get(key);

        if (vv2 != null) {
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            l = new Laboratory(bytes2);
        }

        return l;
    }
    
    public boolean exist(int laboratoireId) {
        Key key = Key.createKey(Arrays.asList(Laboratory.MAJOR_KEY, String.valueOf(laboratoireId)), "info");
        return store.get(key) != null;
    }

    public int create(Laboratory laboratoire) {
        Version putIfAbsent = store.putIfAbsent(laboratoire.getStoreKey("info"), laboratoire.getStoreValue());
        return (putIfAbsent != null ? 0 : 104);
    }

    public int create(int laboratoireId, String nom, String adresse) {
        return create(new Laboratory(laboratoireId, nom, adresse));
    }

    public int update(int laboratoireId, Laboratory laboratoire) {
        return update(laboratoireId, laboratoire.getNom(), laboratoire.getAdresse());
    }

    public int update(int laboratoireId, String nom, String adresse) {
        if (exist(laboratoireId)) {
            Laboratory l = read(laboratoireId);
            if (nom != null) {
                l.setNom(nom);
            }
            if (adresse != null) {
                l.setAdresse(adresse);
            }
            store.delete(l.getStoreKey("info"));
            store.putIfAbsent(l.getStoreKey("info"), l.getStoreValue());
            
            return 0;
        } else {
            return 154;
        }
    }

    public int delete(int laboratoireId) {
        boolean delete = false;
        if (exist(laboratoireId)) {
            delete = store.delete(read(laboratoireId).getStoreKey("info"));
        }

        return (delete ? 0 : 154);
    }

    public void genererTest(int n) {

        for (int i = 0; i < n; i++) {
            create(i, "Laboratoire" + i, "Mathieu");
        }
    }

    public void afficherTest(int n) {

        for (int i = 0; i < n; i++) {
            Laboratory a = read(i);
            System.out.println(a);
        }
    }

    public void supprimerTest(int n) {

        for (int i = 0; i < n; i++) {
            delete(i);
        }
    }
}
