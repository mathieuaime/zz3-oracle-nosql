/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql.factory;

import java.util.Arrays;
import oracle.kv.KVStore;
import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;
import oracle.kv.Key;
import oracle.kv.Value;
import oracle.kv.ValueVersion;
import oracle.nosql.entities.AEteEcrit;

/**
 *
 * @author mathieu
 */
public class AEteEcritFactory {
    
    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public AEteEcritFactory() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }
    
    public AEteEcrit read(int livreId) {
        Key key = Key.createKey(Arrays.asList(AEteEcrit.MAJOR_KEY,String.valueOf(livreId)),"info");
            
        ValueVersion vv2 = store.get(key);

        Value value2 = vv2.getValue();
        byte[] bytes2 = value2.getValue();
        AEteEcrit a = new AEteEcrit(livreId, bytes2);
        
        return a;        
    }

    public void create(int auteurId, int livreId) {     
        AEteEcrit aEcrit = new AEteEcrit(auteurId, livreId);
        store.putIfAbsent(aEcrit.getStoreKey("info"), aEcrit.getStoreValue());
    }    
    
    public void update(int livreId, int newAuteurId) {
        AEteEcrit a = read(livreId);
        a.setLivreId(newAuteurId);
        store.delete(a.getStoreKey("info"));
        store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());        
    }    
    
    public void delete(int livreId) {
        AEteEcrit a = read(livreId);
        store.delete(a.getStoreKey("info"));
    }
    
}
