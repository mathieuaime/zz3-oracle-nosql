/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import entities.EstRattache;
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
import oracle.kv.Version;

/**
 *
 * @author mathieu
 */
public class EstRattacheDAO {
    
    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public EstRattacheDAO() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }
    
    public EstRattache read(String auteurNom, String type, int rank) {
        EstRattache a = null;
        Key key = Key.createKey(Arrays.asList(EstRattache.MAJOR_KEY, auteurNom, type, String.valueOf(rank)),"info");
            
        ValueVersion vv2 = store.get(key);
        
        if (vv2 != null) {
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            a = new EstRattache(bytes2);
        }
        
        return a;
    }
    
    public List<EstRattache> read(String auteurNom, String type) {
        Key key = Key.createKey(Arrays.asList(EstRattache.MAJOR_KEY, auteurNom, type));
        
        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);
        
        ArrayList<EstRattache> rattaches = new ArrayList<>();
        
        while(it.hasNext()) {
            Key k = it.next().getKey();
            
            ValueVersion vv2 = store.get(k);

            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            EstRattache a = new EstRattache(bytes2);
            rattaches.add(a);
        }
        
        return rattaches;
    }
    
    public List<EstRattache> read(String auteurNom) {
        Key key = Key.createKey(Arrays.asList(EstRattache.MAJOR_KEY,auteurNom));
        
        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);
        
        ArrayList<EstRattache> rattaches = new ArrayList<>();
        
        while(it.hasNext()) {
            Key k = it.next().getKey();
            
            ValueVersion vv2 = store.get(k);

            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            EstRattache a = new EstRattache(bytes2);
            rattaches.add(a);
        }
        
        return rattaches;
    }
    
    public List<EstRattache> read() {
        Key key = Key.createKey(EstRattache.MAJOR_KEY);
        
        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);
        
        ArrayList<EstRattache> rattaches = new ArrayList<>();
        
        while(it.hasNext()) {
            Key k = it.next().getKey();
            
            ValueVersion vv2 = store.get(k);

            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            EstRattache a = new EstRattache(bytes2);
            rattaches.add(a);
        }
        
        return rattaches;
    }

    public String create(EstRattache a) {     
        Version putIfAbsent = store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());
        return (putIfAbsent != null ? "200" : "308");
    }    
    
    public String create(String auteurNom, String type, int rank, int value) {     
        EstRattache estRattache = new EstRattache(auteurNom, type, rank, value);
        return create(estRattache);
    }
    
    public String create(String auteurNom, String type, int value) {
        return create(auteurNom, type, getLastRank(auteurNom, type, "info"), value);
    }
    
    public String update(String auteurNom, String type, int rank, int newValue) {
        EstRattache read = read(auteurNom, type, rank);
        
        if (read != null) {
            if(newValue > 0) read.setValue(newValue);
            store.delete(read.getStoreKey("info"));
            store.putIfAbsent(read.getStoreKey("info"), read.getStoreValue());
            return "200";
        } else {
            return "408";
        }
    }    
    
    public String update(String auteurNom, String type, int rank, EstRattache r) {
        return update(auteurNom, type, rank, r.getValue());
    }
    
    public String delete(String auteurNom, String type, int rank) {
        EstRattache a = read(auteurNom, type, rank);
        boolean delete = false;
        if (a != null) delete = store.delete(a.getStoreKey("info"));
        return (delete ? "200" : "408");
    }
    
    public String delete(String auteurNom) {
        boolean delete = false;
        for(EstRattache er : read(auteurNom))
            delete = store.delete(er.getStoreKey("info"));
        
        return (delete ? "200" : "408");
    }
    
    public void genererTest(int n) {       
        
        for (int i = 0; i < n; i+=2) {
            create("Aimé"+i, "labo", 1, i);
            create("Aimé"+i, "univ", 1, i);
        } 
    }
    
    public void afficherTest(int n) {       
        
        for (int i = 0; i < n; i++) {
            EstRattache a = read("Aimé"+i,"labo",1);
            System.out.println(a);
            a = read("Aimé"+i, "univ",1);
            System.out.println(a);
           
        } 
    }
    
    public void supprimerTest(int n) {       
        
        for (int i = 0; i < n; i+=2) {
            delete("Aimé"+i);
           
        } 
    }

    public int getRank(String auteurNom, String type, int value) {
        return getRank(auteurNom, type, value, "info");
    }
    
    public int getRank(String auteurNom, String type, int value, String minorPath) {
        Key key = Key.createKey(Arrays.asList(EstRattache.MAJOR_KEY,auteurNom, type));
        
        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);
        
        int rg = -1;
        
        while(it.hasNext()) {
            Key k = it.next().getKey();
            
            if(k.getMinorPath().get(0).equals(minorPath)) {
            
                List<String> majorPath = k.getMajorPath();
                String rang  = majorPath.get(3);

                ValueVersion vv2 = store.get(k);

                Value value2 = vv2.getValue();
                byte[] bytes2 = value2.getValue();
                EstRattache a = new EstRattache(bytes2);

                if (a.getValue() == value) {
                    rg = Integer.parseInt(rang);
                    break;
                }
            }
        }
        return rg;
    }
    
    private int getLastRank(String auteurNom, String type, String minorPath) {
        Key key = Key.createKey(Arrays.asList(EstRattache.MAJOR_KEY,auteurNom, type));
        
        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);
        
        int rang = 0;
        
        while(it.hasNext()) {
            Key k = it.next().getKey();
            
            if(k.getMinorPath().get(0).equals(minorPath)) {
                rang = Integer.max(rang, Integer.parseInt(k.getMajorPath().get(3)));
            }
        }
        
        return rang; 
    }
    
}
