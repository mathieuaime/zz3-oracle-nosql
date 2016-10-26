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
import oracle.kv.Key;
import oracle.kv.Value;
import oracle.kv.ValueVersion;
import oracle.nosql.entities.Livre;

/**
 *
 * @author mathieu
 */
public class CreateLivre {
    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public CreateLivre() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }
    
    public static Livre get(int livreId) {
        Key key = Key.createKey(Arrays.asList(Livre.MAJOR_KEY,String.valueOf(livreId)),"info");
            
        ValueVersion vv2 = store.get(key);

        Value value2 = vv2.getValue();
        byte[] bytes2 = value2.getValue();
        Livre l = new Livre(livreId, bytes2);
        l.setLivreId(livreId);
        
        return l;        
    }

    public static void add(Livre livre) {        
        store.putIfAbsent(livre.getStoreKey("info"), livre.getStoreValue());
    }    
    
    public static void update(int livreId, Livre newLivre) {
        Livre l = get(livreId);
        newLivre.setLivreId(livreId);
        store.delete(l.getStoreKey("info"));
        store.putIfAbsent(l.getStoreKey("info"), newLivre.getStoreValue());        
    }    
    
    public static void delete(int livreId) {
        Livre l = get(livreId);
        store.delete(l.getStoreKey("info"));
    }
    
}
