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
import entities.Rattache;
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
    
    public ArrayList<Rattache> read(String laboratoireNom, String universiteNom) {
        Key key = Key.createKey(Arrays.asList(Rattache.MAJOR_KEY,laboratoireNom,universiteNom),"info");
        
        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);
        
        ArrayList<Rattache> rattaches = new ArrayList<>();
        
        while(it.hasNext()) {
            Key k = it.next().getKey();
            
            ValueVersion vv2 = store.get(k);

            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            Rattache a = new Rattache(bytes2);
            rattaches.add(a);
        }
        
        return rattaches;        
    }
    
    public Rattache read(String laboratoireNom, String universiteNom,int rang) {
        Rattache r = null;
        Key key = Key.createKey(Arrays.asList(Rattache.MAJOR_KEY,laboratoireNom,universiteNom, String.valueOf(rang)),"info");
            
        ValueVersion vv2 = store.get(key);
        
        if(vv2 != null) {
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            r = new Rattache(bytes2);
        }
        
        return r;        
    }

    public String create(Rattache a) {     
        Version putIfAbsent = store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());
        return (putIfAbsent != null ? "200" : "306");
    }    
    
    public String create(String laboratoireNom,String universiteNom,  int idAuteur, int rang) {     
        Rattache rattache = new Rattache(laboratoireNom,universiteNom,rang, idAuteur);
        return create(rattache);
    }
    
    public String update(String laboratoireNom,String universiteNom, int rang, int idAuteur) {
        Rattache a = read(laboratoireNom,universiteNom, rang);
        if(a != null) {
            if(idAuteur > 0) a.setIdAuteur(idAuteur);
            store.delete(a.getStoreKey("info"));
            store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());
        }
        
        return (a != null ? "200" : "406");
    }    
    
    public String update(String laboratoireNom, String universiteNom, int rang, Rattache r) {
        return update(laboratoireNom, universiteNom, rang, r.getIdAuteur());
    }
    
    public String delete(String laboratoireNom, String universiteNom,int rang) {
        Rattache a = read(laboratoireNom, universiteNom,rang);
        boolean delete = false;
        if (a != null) delete = store.delete(a.getStoreKey("info"));
        return (delete ? "200" : "406");
    }
    
    public void genererTest(int n) {       
        
        for (int i = 0; i < n; i+=2) {
            create("Laboratoire"+i,"Universite"+i,i,1);
        } 
    }
    
    public void afficherTest(int n) {       
        
        for (int i = 0; i < n; i++) {
            Rattache a = read("Laboratoire"+i,"Universite"+i,1);
            System.out.println(a);
           
        } 
    }
    
    public void supprimerTest(int n) {       
        
        for (int i = 0; i < n; i+=2) {
            delete("Laboratoire"+i,"Universite"+i,1);
           
        } 
    }
    
}
