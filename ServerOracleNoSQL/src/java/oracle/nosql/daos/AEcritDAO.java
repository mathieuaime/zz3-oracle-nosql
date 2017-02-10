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
import oracle.kv.Version;
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
    
    public List<AEcrit> read(String auteurNom) {
        return read(auteurNom, "info");
    }
    public List<AEcrit> read(String auteurNom, String minorPath) {
        Key key = Key.createKey(Arrays.asList(AEcrit.MAJOR_KEY,auteurNom));
        
        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);
        
        ArrayList<AEcrit> ecrits = new ArrayList<>();
        
        while(it.hasNext()) {
            Key k = it.next().getKey();
            
            if(k.getMinorPath().get(0).equals(minorPath)) {

                ValueVersion vv2 = store.get(k);

                Value value2 = vv2.getValue();
                byte[] bytes2 = value2.getValue();
                AEcrit a = new AEcrit(bytes2);
                ecrits.add(a);
            }
        }
        
        return ecrits;        
    }
    
    public AEcrit read(String auteurNom, int rang) {
        return read(auteurNom, rang, "info");
    }
    
    public AEcrit read(String auteurNom, int rang, String minorPath) {
        Key key = Key.createKey(Arrays.asList(AEcrit.MAJOR_KEY,auteurNom,String.valueOf(rang)),minorPath);
        
        AEcrit a = null;
            
        ValueVersion vv2 = store.get(key);

        if(vv2 != null) {
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            a = new AEcrit(bytes2);
        }
        return a;        
    }
    
    public int getRang(String auteurNom, int idLivre) {
        return getRang(auteurNom, idLivre, "info");
    }
    
    public int getRang(String auteurNom, int idLivre, String minorPath) {
        Key key = Key.createKey(Arrays.asList(AEcrit.MAJOR_KEY,auteurNom));
        
        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);
        
        int rg = -1;
        
        while(it.hasNext()) {
            Key k = it.next().getKey();
            
            if(k.getMinorPath().get(0).equals(minorPath)) {
            
                List<String> majorPath = k.getMajorPath();
                String rang  = majorPath.get(2);

                ValueVersion vv2 = store.get(k);

                Value value2 = vv2.getValue();
                byte[] bytes2 = value2.getValue();
                AEcrit a = new AEcrit(bytes2);

                if (a.getIdLivre() == idLivre) {
                    rg = Integer.parseInt(rang);
                    break;
                }
            }
        }
        return rg;
    }
    
    private int getLastRang(String auteurNom, String minorPath) {
        Key key = Key.createKey(Arrays.asList(AEcrit.MAJOR_KEY,auteurNom));
        
        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);
        
        int rang = 0;
        
        while(it.hasNext()) {
            Key k = it.next().getKey();
            
            if(k.getMinorPath().get(0).equals(minorPath)) {
                rang = Integer.max(rang, Integer.parseInt(k.getMajorPath().get(2)));
            }
        }
        
        return rang; 
    }

    public String create(AEcrit a) {     
        return create(a, "info");
    }
    public String create(AEcrit a, String minorPath) { 
        if (a.getRang() < 0) a.setRang(1 + getLastRang(a.getAuteurNom(), minorPath));
        
        Version putIfAbsent = store.putIfAbsent(a.getStoreKey(minorPath), a.getStoreValue());
        //TODO tester si le livre existe sinon erreur 401
        
        return (putIfAbsent != null ? "200" : "302");
    }    
    
    public String create(String auteurNom, int idLivre) {
        return create(auteurNom, idLivre, "info");
    }
    
    public String create(String auteurNom, int idLivre, String minorPath) {
        return create(auteurNom, idLivre, 1 + getLastRang(auteurNom, minorPath), minorPath);
    }   
    
    public String create(String auteurNom, int idLivre, int rang) {     
        return create(auteurNom, idLivre, rang, "info");
    }
    
    public String create(String auteurNom, int idLivre, int rang, String minorPath) {     
        AEcrit aEcrit = new AEcrit(auteurNom, idLivre, rang);
        return create(aEcrit, minorPath);
    }    
    
    public String update(String auteurNom, int idLivre, int newIdLivre) {
        return update(auteurNom, idLivre, newIdLivre, "info");
    }
    public String update(String auteurNom, int idLivre, int newIdLivre, String minorPath) {
        AEcrit a = read(auteurNom, getRang(auteurNom, idLivre, minorPath), minorPath);
        if (a != null) {
            a.setIdLivre(newIdLivre);
            //TODO tester si le nouveau livre existe sinon erreur 401
            store.delete(a.getStoreKey(minorPath));
            store.putIfAbsent(a.getStoreKey(minorPath), a.getStoreValue());  
        }
        
        return (a != null ? "200" : "402");
    }    
    
    public String delete(String auteurNom, int rang) {
        return delete(auteurNom, rang, "info");
    }
    
    public String delete(String auteurNom, int rang, String minorPath) {
        AEcrit a = read(auteurNom, rang, minorPath);
        if (a != null) store.delete(a.getStoreKey(minorPath));
        
        return (a != null ? "200" : "402");
    }
    
    public String delete(String auteurNom) {
        return delete(auteurNom, "info");
    }
    public String delete(String auteurNom, String minorPath) {
        String result = "402";
        for(AEcrit a : read(auteurNom, minorPath)) {
            store.delete(a.getStoreKey(minorPath));
            result = "200";
        }
        
        return result;
    }
    
    public void genererTest(int n) {       
        
        for (int i = 0; i < n; i+=2) {
            create("AimÃ©"+i,(2*i),1,"demo");
            create("AimÃ©"+i,(1+2*i),2,"demo");
        } 
    }
    
    public void afficherTest(int n) {       
        
        for (int i = 0; i < n; i++) {
            AEcrit a = read("AimÃ©"+i,1,"demo");
            System.out.println(a);
            a = read("AimÃ©"+i,2,"demo");
            System.out.println(a);
        } 
    }
    
    public void supprimerTest(int n) {       
        
        for (int i = 0; i < n; i+=2) {
            delete("AimÃ©"+i,1,"demo");
            delete("AimÃ©"+i,2,"demo");
        } 
    }
}
