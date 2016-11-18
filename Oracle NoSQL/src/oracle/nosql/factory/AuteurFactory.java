/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql.factory;

import java.util.Arrays;
import java.util.Iterator;
import oracle.kv.Direction;
import oracle.kv.KVStore;
import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;
import oracle.kv.ValueVersion;
import oracle.kv.Value;
import oracle.kv.Key;
import oracle.kv.KeyValueVersion;
import oracle.nosql.entities.Auteur;
import oracle.nosql.entities.Livre;

/**
 *
 * @author mathieu
 */
public class AuteurFactory {
    
    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public AuteurFactory() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }
    
    public Auteur read(String nom) {    
        Key myKey2 = Key.createKey(Auteur.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);
        
        Auteur auteur = new Auteur();
        
        while (i.hasNext()) 
          {
           Key k = i.next().getKey();

           ValueVersion valueVersionrecherche = store.get(k); 
           Value v = valueVersionrecherche.getValue();
           byte[] bytes2 = v.getValue();
           Auteur a = new Auteur(Integer.parseInt(k.getMajorPath().get(1)), bytes2);
           String t = a.getNom();
           
           if (t.equals(nom)) {auteur = a; break; }      
        }
        
        return auteur;
        
    }
    
    public Auteur read(int auteurId) {
        Key key = Key.createKey(Arrays.asList(Auteur.MAJOR_KEY,String.valueOf(auteurId)),"info");
            
        ValueVersion vv2 = store.get(key);

        Value value2 = vv2.getValue();
        byte[] bytes2 = value2.getValue();
        Auteur a = new Auteur(auteurId, bytes2);
        
        return a;        
    }
      
    public Auteur create(Auteur auteur) {     
        store.putIfAbsent(auteur.getStoreKey("info"), auteur.getStoreValue());
        return auteur;    
    }

    public Auteur create(int auteurId, String nom, String prenom, String adresse, String phone) {     
        Auteur auteur = new Auteur(auteurId, nom, prenom, adresse, phone);
        return create(auteur);
    }    
    
    public void update(int auteurId, String nom, String prenom, String adresse, String phone) {
        Auteur a = read(auteurId);
        if (nom != null) a.setNom(nom);
        if (prenom != null) a.setPrenom(prenom);
        if (adresse != null) a.setAdresse(adresse);
        if (phone != null) a.setPhone(phone);
        store.delete(a.getStoreKey("info"));
        store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());        
    }    
    
    public void delete(int auteurId) {
        Auteur a = read(auteurId);
        store.delete(a.getStoreKey("info"));
    }
    
    public void genererTest(int n) {       

        for (int i = 0; i < n; i++) {
            create(i, "Aimé"+i, "Mathieu", "Clermont", "4444");
            //delete(i); //pour vider la base
        } 
    }

    public void afficherTest(int n) {       

        for (int i = 0; i < n; i++) {
            Auteur a = read(i);
            System.out.println(a);
        } 
    }
    
}
