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
import oracle.kv.Depth;
import oracle.kv.Direction;
import oracle.kv.KVStore;
import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;
import oracle.kv.Key;
import oracle.kv.KeyValueVersion;
import oracle.kv.Value;
import oracle.kv.ValueVersion;
import oracle.nosql.entities.Livre;

/**
 *
 * @author mathieu
 */
public class LivreFactory {
    
    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public LivreFactory() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }
    
    public Livre read(String titre) {    
        Key myKey2 = Key.createKey(Livre.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);
        
        Livre livre = new Livre();
        
        while (i.hasNext()) 
          {
           Key k = i.next().getKey();

           ValueVersion valueVersionrecherche = store.get(k); 
           Value v = valueVersionrecherche.getValue();
           byte[] bytes2 = v.getValue();
           Livre l = new Livre(Integer.parseInt(k.getMajorPath().get(1)), bytes2);
           String t = l.getTitre();
           
           if (t.equals(titre)) {livre = l;  break; }    
        }
        
        return livre;
        
    }
    
    public Livre read(int livreId) {
        Key key = Key.createKey(Arrays.asList(Livre.MAJOR_KEY,String.valueOf(livreId)),"info");
            
        ValueVersion vv2 = store.get(key);

        Value value2 = vv2.getValue();
        byte[] bytes2 = value2.getValue();
        Livre l = new Livre(livreId, bytes2);
        
        return l;        
    }
    
    public Livre create(Livre livre) {
        store.putIfAbsent(livre.getStoreKey("info"), livre.getStoreValue());
        return livre;
    }

    public Livre create(int livreId, String titre, String resume, float prix) {
        Livre livre = new Livre(livreId, titre, resume, prix);
        return create(livre);
    }    
    
    public void update(int livreId, String titre, String resume, float prix) {
        Livre l = read(livreId);
        if (titre != null) l.setTitre(titre);
        if (resume != null) l.setResume(resume);
        if (prix >= 0) l.setPrix(prix);
        store.delete(l.getStoreKey("info"));
        store.putIfAbsent(l.getStoreKey("info"), l.getStoreValue());        
    }    
    
    public void delete(int livreId) {
        Livre l = read(livreId);
        store.delete(l.getStoreKey("info"));
    }
    
    public void genererTest(int n) {       
        
        for (int i = 0; i < n; i++) {
            create(i, "Le bateau"+i, "Une histoire de bateau", 20);
        } 
    }
    
    public void afficherTest(int n) {       
        
        for (int i = 0; i < n; i++) {
            Livre l = read(i);
            System.out.println(l);
        } 
    }
    
    public void supprimerTest(int n) {       
        
        for (int i = 0; i < n; i++) {
            delete(i);
        } 
    }
}
