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
import oracle.kv.Key;
import oracle.kv.KeyValueVersion;
import oracle.kv.Value;
import oracle.kv.ValueVersion;
import oracle.nosql.entities.AEteEcrit;

/**
 *
 * @author mathieu
 */
public class AEteEcritDAO {
    
    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public AEteEcritDAO() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }
    
    public List<AEteEcrit> read(String livreTitre) {
        return read(livreTitre, "info");        
    }
    
    public List<AEteEcrit> read(String livreTitre, String minorKey) {
        ArrayList<AEteEcrit> a = new ArrayList<>();
        
        Key key = Key.createKey(Arrays.asList(AEteEcrit.MAJOR_KEY,livreTitre),minorKey);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, key, null, null);
        
        AEteEcrit aee;
        
        while (i.hasNext()) 
          {
           Key k = i.next().getKey();

           ValueVersion valueVersionrecherche = store.get(k); 
           Value v = valueVersionrecherche.getValue();
           byte[] bytes2 = v.getValue();
           aee = new AEteEcrit(bytes2);
           a.add(aee);
        }
        
        return a;        
    }

    public AEteEcrit create(AEteEcrit a) {     
        return create(a, "info");
    }    

    public AEteEcrit create(AEteEcrit a, String minorKey) {     
        store.putIfAbsent(a.getStoreKey(minorKey), a.getStoreValue());
        return a;
    }    
    
    public AEteEcrit create(String livreTitre, int idAuteur) {     
        return create(livreTitre, idAuteur, "info");
    } 
    
    public AEteEcrit create(String livreTitre, int idAuteur, String minorKey) {     
        AEteEcrit aEcrit = new AEteEcrit(livreTitre, idAuteur);
        return create(aEcrit, minorKey);
    } 
    
    public void update(String livreTitre, int idAuteur, int newIdAuteur) {
        update(livreTitre, idAuteur, newIdAuteur, "info");
    }    
    
    public void update(String livreTitre, int idAuteur, int newIdAuteur, String minorKey) {
        for(AEteEcrit a : read(livreTitre)) {
            if(a.getIdAuteur() == idAuteur) {
                a.setIdAuteur(newIdAuteur);
                store.delete(a.getStoreKey(minorKey));
                store.putIfAbsent(a.getStoreKey(minorKey), a.getStoreValue()); 
            }
        }
    }    
    
    public void delete(String livreTitre) {
        delete(livreTitre, "info");
    }
    
    public void delete(String livreTitre, String minorKey) {
        for(AEteEcrit a : read(livreTitre)) store.delete(a.getStoreKey(minorKey));
    }
    
    public void genererTest(int n) {       
        
        for (int i = 0; i < n; i++) {
            create("Le bateau"+(2*i),i,"demo");
            create("Le bateau"+(1+2*i),i,"demo");
        } 
    }
    
    public void afficherTest(int n) {       
        
        for (int i = 0; i < n; i++) {
            for(AEteEcrit a : read("Le bateau"+(2*i),"demo")) System.out.println(a);
            for(AEteEcrit a : read("Le bateau"+(2*i + 1),"demo")) System.out.println(a);
        } 
    }
    
    public void supprimerTest(int n) {       
        
        for (int i = 0; i < n; i+=2) {
            delete("Le bateau"+(2*i),"demo");
            delete("Le bateau"+(1 + 2*i),"demo");
        } 
    }
    
}
