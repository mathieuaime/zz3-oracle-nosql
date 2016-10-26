/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql;

import java.util.Arrays;
import oracle.kv.KVStore;
import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;
import oracle.kv.ValueVersion;
import oracle.kv.Value;
import oracle.kv.Key;
import oracle.nosql.entities.Auteur;

/**
 *
 * @author mathieu
 */
public class CreateAuteur {
    
    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public CreateAuteur() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }
    
    public static Auteur get(int auteurId) {
        Key key = Key.createKey(Arrays.asList(Auteur.MAJOR_KEY,String.valueOf(auteurId)),"info");
            
        ValueVersion vv2 = store.get(key);

        Value value2 = vv2.getValue();
        byte[] bytes2 = value2.getValue();
        Auteur a = new Auteur(bytes2);
        a.setAuteurId(auteurId);
        
        return a;        
    }

    public static void add(Auteur auteur) {        
        store.putIfAbsent(auteur.getStoreKey("info"), auteur.getStoreValue());
    }    
    
    public static void update(int auteurId, Auteur newAuteur) {
        Auteur a = get(auteurId);
        newAuteur.setAuteurId(auteurId);
        store.delete(a.getStoreKey("info"));
        store.putIfAbsent(a.getStoreKey("info"), newAuteur.getStoreValue());        
    }    
    
    public static void delete(int auteurId) {
        Auteur a = get(auteurId);
        store.delete(a.getStoreKey("info"));
    }
    
}
