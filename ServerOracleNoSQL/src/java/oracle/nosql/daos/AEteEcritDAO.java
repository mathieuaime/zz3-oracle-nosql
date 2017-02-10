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
import oracle.kv.Version;
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
    
    //TODO renvoyer String ok/not ok

    public String create(AEteEcrit a) {     
        return create(a, "info");
    }    

    public String create(AEteEcrit a, String minorKey) {     
        Version putIfAbsent = store.putIfAbsent(a.getStoreKey(minorKey), a.getStoreValue());
        //TODO tester si l'auteur existe sinon erreur 400
        return (putIfAbsent != null ? "200" : "403");
    }    
    
    public String create(String livreTitre, int idAuteur) {     
        return create(livreTitre, idAuteur, "info");
    } 
    
    public String create(String livreTitre, int idAuteur, String minorKey) {     
        AEteEcrit aEcrit = new AEteEcrit(livreTitre, idAuteur);
        
        return create(aEcrit, minorKey);
    } 
    
    public String update(String livreTitre, int idAuteur, int newIdAuteur) {
        return update(livreTitre, idAuteur, newIdAuteur, "info");
    }    
    
    public String update(String livreTitre, int idAuteur, int newIdAuteur, String minorKey) {
        String result = "403";
        
        for(AEteEcrit a : read(livreTitre, minorKey)) {
            if(a.getIdAuteur() == idAuteur) {
                a.setIdAuteur(newIdAuteur);
                //TODO tester si le nouveau auteur existe sinon erreur 400
                store.delete(a.getStoreKey(minorKey));
                store.putIfAbsent(a.getStoreKey(minorKey), a.getStoreValue()); 
                result = "200";
            }
        }
        
        return result;
    }    
    
    public String delete(String livreTitre) {
        return delete(livreTitre, "info");
    }
    
    public String delete(String livreTitre, String minorKey) {
        String result = "403";
        for(AEteEcrit a : read(livreTitre, minorKey))  {
            store.delete(a.getStoreKey(minorKey));
            result = "200";
        }
        
        return result;
    }
    
    public String delete(String livreTitre, int auteurId) {
        return delete(livreTitre, auteurId, "info");
    }
    
    public String delete(String livreTitre, int auteurId, String minorKey) {
        String result = "403";
        for(AEteEcrit a : read(livreTitre, minorKey)) {
            if(a.getIdAuteur() == auteurId) {
                store.delete(a.getStoreKey(minorKey));
                result = "200";
            }
        }
        
        return result;
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
