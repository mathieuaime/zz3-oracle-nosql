/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

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
import oracle.kv.Version;
import entities.Autor;

/**
 *
 * @author mathieu
 */
public class AutorDAO {
    
    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public AutorDAO() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }
    
    public Autor read(String nom) {    
        Key myKey2 = Key.createKey(Autor.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);
        
        Autor auteur = new Autor();
        
        while (i.hasNext()) 
          {
           Key k = i.next().getKey();

           ValueVersion valueVersionrecherche = store.get(k); 
           Value v = valueVersionrecherche.getValue();
           byte[] bytes2 = v.getValue();
           Autor a = new Autor(bytes2);
           String t = a.getNom();
           
           if (t.equals(nom)) {auteur = a; break; }      
        }
        
        return auteur;
        
    }
    
    public Autor read(int auteurId) {
        Autor a = null;
        Key key = Key.createKey(Arrays.asList(Autor.MAJOR_KEY,String.valueOf(auteurId)),"info");
            
        ValueVersion vv2 = store.get(key);
        
        if (vv2 != null) {
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            a = new Autor(bytes2);
        }
        
        return a;        
    }
    
    public List<Autor> read(){
        Key myKey2 = Key.createKey(Autor.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);
        
        List<Autor> auteurs = new ArrayList<>();
        
        while (i.hasNext()) 
          {
           Key k = i.next().getKey();

           ValueVersion valueVersionrecherche = store.get(k); 
           Value v = valueVersionrecherche.getValue();
           byte[] bytes2 = v.getValue();
           Autor a = new Autor(bytes2);
           auteurs.add(a);
        }
        
        return auteurs;
    }
    
    public List<Autor> getLast(int n){
        Key myKey2 = Key.createKey(Autor.MAJOR_KEY);
        //Iterator<KeyValueVersion> i = store.multiGetIterator(Direction.REVERSE, 0, myKey2, null, null);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);
        
        List<Autor> auteurs = new ArrayList<>();
        
        while (i.hasNext()) 
          {
           Key k = i.next().getKey();

           ValueVersion valueVersionrecherche = store.get(k); 
           Value v = valueVersionrecherche.getValue();
           byte[] bytes2 = v.getValue();
           Autor a = new Autor(bytes2);
           auteurs.add(a);
        }
        
        return auteurs;
    }
    
    public String create(Autor auteur) {     
        Version putIfAbsent = store.putIfAbsent(auteur.getStoreKey("info"), auteur.getStoreValue());
        
        return (putIfAbsent != null ? "200" : "300");    
    }

    public String create(int auteurId, String nom, String prenom, String adresse, String phone, String fax, String mail) {     
        Autor auteur = new Autor(auteurId, nom, prenom, adresse, phone, fax, mail);
        return create(auteur);
    }    
    
    public String update(int auteurId, String nom, String prenom, String adresse, String phone, String fax, String mail) {
        Autor a = read(auteurId);
        if (a != null) {
            if (nom != null) a.setNom(nom);
            if (prenom != null) a.setPrenom(prenom);
            if (adresse != null) a.setAdresse(adresse);
            if (phone != null) a.setPhone(phone);
            if (fax != null) a.setFax(fax);
            if (mail != null) a.setMail(mail);
            store.delete(a.getStoreKey("info"));
            store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue()); 
        }
        
        return (a != null ? "200" : "400");
    }    
    
    public String update(int auteurId, Autor auteur) {
        return update(auteurId, auteur.getNom(), auteur.getPrenom(), auteur.getAdresse(), auteur.getPhone(), auteur.getFax(), auteur.getMail());
    }
    
    public String delete(int auteurId) {
        Autor a = read(auteurId);
        boolean delete = false;
        if (a != null) {
            delete = store.delete(a.getStoreKey("info"));
        }
        
        return (delete ? "200" : "400");
    }
    
    public void genererTest(int n) {       

        for (int i = 0; i < n; i++) {
            create(i, "Aimé"+i, "Mathieu", "Clermont", "4444", "4422", "mathieu@aime.com");
        } 
    }

    public void afficherTest(int n) {       

        for (int i = 0; i < n; i++) {
            Autor a = read(i);
            System.out.println(a);
        } 
    }
    
    public void supprimerTest(int n) {       

        for (int i = 0; i < n; i++) {
            delete(i);
        } 
    }
    
    public void displayAll() {
        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED,0);
        System.out.println("In StoreIterator Method: Delete Everything");
        while (it.hasNext() )
        {
            KeyValueVersion kvvi = it.next();
            System.out.println(kvvi.getKey());
        }
    }
    
    public void deleteAll() {
        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED,0);
        while (it.hasNext() )
        {
            KeyValueVersion kvvi = it.next();
            store.delete(kvvi.getKey());
        }
    }
    
}
