/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql.daos;

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

/**
 *
 * @author mathieu
 */
public class AEcritDAO {
    
    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public AEcritDAO() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }
    
    public ArrayList<AEcrit> read(String auteurNom) {
        Key key = Key.createKey(Arrays.asList(AEcrit.MAJOR_KEY,auteurNom),"info");
        
        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);
        
        ArrayList<AEcrit> ecrits = new ArrayList<>();
        
        while(it.hasNext()) {
            Key k = it.next().getKey();
            
            List<String> majorPath = k.getMajorPath();
            String rang  = majorPath.get(2);
            
            ValueVersion vv2 = store.get(k);

            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            AEcrit a = new AEcrit(auteurNom, Integer.parseInt(rang), bytes2);
            ecrits.add(a);
        }
        
        return ecrits;        
    }
    
    public AEcrit read(String auteurNom, int rang) {
        Key key = Key.createKey(Arrays.asList(AEcrit.MAJOR_KEY,auteurNom,String.valueOf(rang)),"info");
            
        ValueVersion vv2 = store.get(key);

        Value value2 = vv2.getValue();
        byte[] bytes2 = value2.getValue();
        AEcrit a = new AEcrit(auteurNom, rang, bytes2);
        
        return a;        
    }

    public void create(AEcrit a) {     
        store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());
    }    
    
    public void create(String auteurNom, String livreTitre, int rang) {     
        AEcrit aEcrit = new AEcrit(auteurNom, livreTitre, rang);
        create(aEcrit);
    }    
    
    public void update(String auteurNom, int rang, String newLivreTitre) {
        AEcrit a = read(auteurNom, rang);
        a.setLivreTitre(newLivreTitre);
        store.delete(a.getStoreKey("info"));
        store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());        
    }    
    
    public void delete(String auteurNom, int rang) {
        AEcrit a = read(auteurNom, rang);
        store.delete(a.getStoreKey("info"));
    }
    
    public void genererTest(int n) {       
        
        for (int i = 0; i < n; i+=2) {
            create("AimÃ©"+i,"Le bateau"+(2*i),1);
            create("AimÃ©"+i,"Le bateau"+(1+2*i),2);
        } 
    }
    
    public void afficherTest(int n) {       
        
        for (int i = 0; i < n; i++) {
            AEcrit a = read("AimÃ©"+i,1);
            System.out.println(a);
            a = read("AimÃ©"+i,2);
            System.out.println(a);
        } 
    }
    
    public void supprimerTest(int n) {       
        
        for (int i = 0; i < n; i+=2) {
            delete("AimÃ©"+i,1);
            delete("AimÃ©"+i,2);
        } 
    }
    
}
