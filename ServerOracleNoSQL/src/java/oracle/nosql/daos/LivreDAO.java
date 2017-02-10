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
import oracle.nosql.entities.Livre;

/**
 *
 * @author mathieu
 */
public class LivreDAO {
    
    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public LivreDAO() {
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
        Livre l = null;
        
        Key key = Key.createKey(Arrays.asList(Livre.MAJOR_KEY,String.valueOf(livreId)),"info");
            
        ValueVersion vv2 = store.get(key);

        if (vv2 != null) {
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            l = new Livre(livreId, bytes2);
        }
        
        return l;        
    }
    
    public List<Livre> read() {
        Key myKey2 = Key.createKey(Livre.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);
        
        List<Livre> livres = new ArrayList<>();
        
        while (i.hasNext()) 
          {
           Key k = i.next().getKey();

           ValueVersion valueVersionrecherche = store.get(k); 
           Value v = valueVersionrecherche.getValue();
           byte[] bytes2 = v.getValue();
           Livre l = new Livre(Integer.parseInt(k.getMajorPath().get(1)), bytes2);
           livres.add(l);
        }
        
        return livres;
    }
    
    public String create(Livre livre) {
        Version putIfAbsent = store.putIfAbsent(livre.getStoreKey("info"), livre.getStoreValue());
        
        return "{\"status\":\""+(putIfAbsent != null ? "ok" : "not ok")+"\"}";
    }

    public String create(int livreId, String titre, String resume, float prix) {
        Livre livre = new Livre(livreId, titre, resume, prix);
        return create(livre);
    }    
    
    public String update(int livreId, String titre, String resume, float prix) {
        Livre l = read(livreId);
        if (l != null) {
            if (titre != null) l.setTitre(titre);
            if (resume != null) l.setResume(resume);
            if (prix >= 0) l.setPrix(prix);
            store.delete(l.getStoreKey("info"));
            store.putIfAbsent(l.getStoreKey("info"), l.getStoreValue());
        }
        
        return "{\"status\":\""+(l != null ? "ok" : "not ok")+"\"}";
    }  
    
    public String update(int idLivre, Livre livre) {
        return update(idLivre, livre.getTitre(), livre.getResume(), livre.getPrix());
    }
    
    public String delete(int livreId) {
        Livre l = read(livreId);
        if (l != null) store.delete(l.getStoreKey("info"));
        return "{\"status\":\""+(l != null ? "ok" : "not ok")+"\"}";
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
