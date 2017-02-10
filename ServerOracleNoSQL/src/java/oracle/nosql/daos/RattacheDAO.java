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
import oracle.nosql.entities.Rattache;

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
            
            List<String> majorPath = k.getMajorPath();
            String rang  = majorPath.get(2);
            
            ValueVersion vv2 = store.get(k);

            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            Rattache a = new Rattache(laboratoireNom,universiteNom, Integer.parseInt(rang), bytes2);
            rattaches.add(a);
        }
        
        return rattaches;        
    }
    
    public Rattache read(String laboratoireNom, String universiteNom,int rang) {
        Key key = Key.createKey(Arrays.asList(Rattache.MAJOR_KEY,laboratoireNom,universiteNom, String.valueOf(rang)),"info");
            
        ValueVersion vv2 = store.get(key);

        Value value2 = vv2.getValue();
        byte[] bytes2 = value2.getValue();
        Rattache a = new Rattache(laboratoireNom, universiteNom, rang, bytes2);
        
        return a;        
    }

    public void create(Rattache a) {     
        store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());
    }    
    
    public void create(String laboratoireNom,String universiteNom,  int idAuteur, int rang) {     
        Rattache rattache = new Rattache(laboratoireNom,universiteNom,rang, idAuteur);
        create(rattache);
    }    
    
    public void update(String laboratoireNom,String universiteNom, int rang, int idAuteur) {
        Rattache a = read(laboratoireNom,universiteNom, rang);
        a.setIdAuteur(idAuteur);
        store.delete(a.getStoreKey("info"));
        store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());        
    }    
    
    public void delete(String laboratoireNom, String universiteNom,int rang) {
        Rattache a = read(laboratoireNom, universiteNom,rang);
        store.delete(a.getStoreKey("info"));
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
