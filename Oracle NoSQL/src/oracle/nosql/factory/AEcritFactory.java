/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql.factory;

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
import oracle.nosql.entities.AEcrit;
import oracle.nosql.entities.Auteur;

/**
 *
 * @author mathieu
 */
public class AEcritFactory {
    
    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public AEcritFactory() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }
    
    public ArrayList<AEcrit> read(int auteurId) {
        Key key = Key.createKey(Arrays.asList(AEcrit.MAJOR_KEY,String.valueOf(auteurId)),"info");
        
        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);
        
        ArrayList<AEcrit> ecrits = new ArrayList<>();
        
        while(it.hasNext()) {
            Key k = it.next().getKey();
            
            List<String> majorPath = k.getMajorPath();
            String rang  = majorPath.get(2);
            
            ValueVersion vv2 = store.get(k);

            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            AEcrit a = new AEcrit(auteurId, Integer.parseInt(rang), bytes2);
            ecrits.add(a);
        }
        
        return ecrits;        
    }
    
    public AEcrit read(int auteurId, int rang) {
        Key key = Key.createKey(Arrays.asList(AEcrit.MAJOR_KEY,String.valueOf(auteurId),String.valueOf(rang)),"info");
            
        ValueVersion vv2 = store.get(key);

        Value value2 = vv2.getValue();
        byte[] bytes2 = value2.getValue();
        AEcrit a = new AEcrit(auteurId, rang, bytes2);
        
        return a;        
    }

    public void create(int auteurId, int livreId, int rang) {     
        AEcrit aEcrit = new AEcrit(auteurId, livreId, rang);
        store.putIfAbsent(aEcrit.getStoreKey("info"), aEcrit.getStoreValue());
    }    
    
    public void update(int auteurId, int rang, int newLivreId) {
        AEcrit a = read(auteurId, rang);
        a.setLivreId(newLivreId);
        store.delete(a.getStoreKey("info"));
        store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());        
    }    
    
    public void delete(int auteurId, int rang) {
        AEcrit a = read(auteurId, rang);
        store.delete(a.getStoreKey("info"));
    }
    
    public void genererTest(int n) {       
        
        for (int i = 0; i < n; i+=2) {
            create(i,2*i,1);
            create(i,1+2*i,2);
            //delete(i); //pour vider la base
        } 
    }
    
}
