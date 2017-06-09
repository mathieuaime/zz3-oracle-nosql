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
import com.isima.zz3.oraclenosql.server.model.University;
import java.util.ArrayList;
import java.util.List;
import oracle.kv.Version;

public class UniversiteDAO {

    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public UniversiteDAO() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }

    public List<University> read() {
        Key myKey2 = Key.createKey(University.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);

        List<University> universites = new ArrayList<>();

        while (i.hasNext()) {
            Key k = i.next().getKey();

            ValueVersion valueVersionrecherche = store.get(k);
            Value v = valueVersionrecherche.getValue();
            byte[] bytes2 = v.getValue();
            University l = new University(bytes2);
            universites.add(l);
        }

        return universites;
    }

    public University read(String nom) {
        Key myKey2 = Key.createKey(University.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);

        University universite = new University();

        while (i.hasNext()) {
            Key k = i.next().getKey();

            ValueVersion valueVersionrecherche = store.get(k);
            Value v = valueVersionrecherche.getValue();
            byte[] bytes2 = v.getValue();
            University a = new University(bytes2);
            String t = a.getNom();

            if (t.equals(nom)) {
                universite = a;
                break;
            }
        }

        return universite;

    }

    public University read(int universiteId) {
        University l = null;
        Key key = Key.createKey(Arrays.asList(University.MAJOR_KEY, String.valueOf(universiteId)), "info");

        ValueVersion vv2 = store.get(key);

        if (vv2 != null) {
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            l = new University(bytes2);
        }

        return l;
    }

    public boolean exist(int universiteId) {
        Key key = Key.createKey(Arrays.asList(University.MAJOR_KEY, String.valueOf(universiteId)), "info");
        return store.get(key) != null;
    }

    public int create(University universite) {
        Version putIfAbsent = store.putIfAbsent(universite.getStoreKey("info"), universite.getStoreValue());
        return (putIfAbsent != null ? 0 : 105);
    }

    public int create(int universiteId, String nom, String adresse) {
        return create(new University(universiteId, nom, adresse));
    }

    public int update(int universiteId, University universite) {
        return update(universiteId, universite.getNom(), universite.getAdresse());
    }

    public int update(int universiteId, String nom, String adresse) {
        if (exist(universiteId)) {
            University l = read(universiteId);
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
            return 155;
        }
    }

    public int delete(int universiteId) {
        boolean delete = false;
        if (exist(universiteId)) {
            delete = store.delete(read(universiteId).getStoreKey("info"));
        }

        return (delete ? 0 : 155);
    }

    public void genererTest(int n) {

        for (int i = 0; i < n; i++) {
            create(i, "Universite" + i, "Mathieu");
        }
    }

    public void afficherTest(int n) {

        for (int i = 0; i < n; i++) {
            University a = read(i);
            System.out.println(a);
        }
    }

    public void supprimerTest(int n) {

        for (int i = 0; i < n; i++) {
            delete(i);
        }
    }
}
