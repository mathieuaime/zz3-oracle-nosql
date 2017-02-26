package daos;

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
import entities.Universite;
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

    public List<Universite> read() {
        Key myKey2 = Key.createKey(Universite.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);

        List<Universite> universites = new ArrayList<>();

        while (i.hasNext()) {
            Key k = i.next().getKey();

            ValueVersion valueVersionrecherche = store.get(k);
            Value v = valueVersionrecherche.getValue();
            byte[] bytes2 = v.getValue();
            Universite l = new Universite(bytes2);
            universites.add(l);
        }

        return universites;
    }

    public Universite read(String nom) {
        Key myKey2 = Key.createKey(Universite.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);

        Universite universite = new Universite();

        while (i.hasNext()) {
            Key k = i.next().getKey();

            ValueVersion valueVersionrecherche = store.get(k);
            Value v = valueVersionrecherche.getValue();
            byte[] bytes2 = v.getValue();
            Universite a = new Universite(bytes2);
            String t = a.getNom();

            if (t.equals(nom)) {
                universite = a;
                break;
            }
        }

        return universite;

    }

    public Universite read(int universiteId) {
        Universite l = null;
        Key key = Key.createKey(Arrays.asList(Universite.MAJOR_KEY, String.valueOf(universiteId)), "info");

        ValueVersion vv2 = store.get(key);

        if (vv2 != null) {
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            l = new Universite(bytes2);
        }

        return l;
    }

    public int create(Universite universite) {
        Version putIfAbsent = store.putIfAbsent(universite.getStoreKey("info"), universite.getStoreValue());
        return (putIfAbsent != null ? 0 : 105);
    }

    public int create(int universiteId, String nom, String adresse) {
        Universite universite = new Universite(universiteId, nom, adresse);
        return create(universite);
    }

    public int update(int universiteId, Universite universite) {
        return update(universiteId, universite.getNom(), universite.getAdresse());
    }

    public int update(int universiteId, String nom, String adresse) {
        Universite l = read(universiteId);
        if (l != null) {
            if (nom != null) {
                l.setNom(nom);
            }
            if (adresse != null) {
                l.setAdresse(adresse);
            }
            store.delete(l.getStoreKey("info"));
            store.putIfAbsent(l.getStoreKey("info"), l.getStoreValue());
        }
        return (l != null ? 0 : 155);
    }

    public int delete(int universiteId) {
        Universite a = read(universiteId);
        boolean delete = false;
        if (a != null) {
            delete = store.delete(a.getStoreKey("info"));
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
            Universite a = read(i);
            System.out.println(a);
        }
    }

    public void supprimerTest(int n) {

        for (int i = 0; i < n; i++) {
            delete(i);
        }
    }
}
