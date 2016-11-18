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
import oracle.nosql.entities.Livre;

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
    
    public AEteEcrit read(String livreTitre) {
        Key key = Key.createKey(Arrays.asList(AEteEcrit.MAJOR_KEY,livreTitre),"info");
            
        ValueVersion vv2 = store.get(key);

        Value value2 = vv2.getValue();
        byte[] bytes2 = value2.getValue();
        AEteEcrit a = new AEteEcrit(livreTitre, bytes2);
        
        return a;        
    }

    public void create(AEteEcrit a) {     
        store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());
    }    
    
    public void create(int auteurId, String livreTitre) {     
        AEteEcrit aEcrit = new AEteEcrit(auteurId, livreTitre);
        create(aEcrit);
    } 
    
    public void update(String livreTitre, int newAuteurId) {
        AEteEcrit a = read(livreTitre);
        a.setAuteurId(newAuteurId);
        store.delete(a.getStoreKey("info"));
        store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());        
    }    
    
    public void delete(String livreTitre) {
        AEteEcrit a = read(livreTitre);
        store.delete(a.getStoreKey("info"));
    }
    
    public void genererTest(int n) {       
        
        for (int i = 0; i < n; i++) {
            create(i,"Le bateu"+(2*i));
            create(i,"Le bateau"+(1+2*i));
        } 
    }
    
    public void afficherTest(int n) {       
        
        for (int i = 0; i < n; i++) {
            AEteEcrit a = read("Le bateu"+(2*i));
            System.out.println(a);
            a = read("Le bateu"+(1 + 2*i));
            System.out.println(a);
        } 
    }
    
    public void supprimerTest(int n) {       
        
        for (int i = 0; i < n; i+=2) {
            delete("Le bateu"+(2*i));
            delete("Le bateu"+(1 + 2*i));
        } 
    }
    
}
