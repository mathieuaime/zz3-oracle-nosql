/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql.daos;

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
public class AEteEcritDAO {
    
    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public AEteEcritDAO() {
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
    
    public void create(String auteurNom, String livreTitre) {     
        AEteEcrit aEcrit = new AEteEcrit(auteurNom, livreTitre);
        create(aEcrit);
    } 
    
    public void update(String livreTitre, String newAuteurNom) {
        AEteEcrit a = read(livreTitre);
        a.setAuteurNom(newAuteurNom);
        store.delete(a.getStoreKey("info"));
        store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());        
    }    
    
    public void delete(String livreTitre) {
        AEteEcrit a = read(livreTitre);
        store.delete(a.getStoreKey("info"));
    }
    
    public void genererTest(int n) {       
        
        for (int i = 0; i < n; i++) {
            create("Aimé"+i,"Le bateau"+(2*i));
            create("Aimé"+i,"Le bateau"+(1+2*i));
        } 
    }
    
    public void afficherTest(int n) {       
        
        for (int i = 0; i < n; i++) {
            AEteEcrit a = read("Le bateau"+(2*i));
            System.out.println(a);
            a = read("Le bateau"+(1 + 2*i));
            System.out.println(a);
        } 
    }
    
    public void supprimerTest(int n) {       
        
        for (int i = 0; i < n; i+=2) {
            delete("Le bateau"+(2*i));
            delete("Le bateau"+(1 + 2*i));
        } 
    }
    
}