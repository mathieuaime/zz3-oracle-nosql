/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracle.nosql;

import java.util.Arrays;
import oracle.kv.KVStore;
import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;
import oracle.kv.ValueVersion;

import oracle.kv.Value;
import oracle.kv.Key;
import static oracle.nosql.Auteur.MAJOR_KEY;

/**
 *
 * @author mathieu
 */
public class CreateAuteur {
    
    private static KVStore store;
    
    public static void main(final String args[]) {
        try {
            String storeName = "kvstore";
            String hostName = "localhost";
            String hostPort = "5000";
            store = KVStoreFactory.getStore(new KVStoreConfig(storeName,
                    hostName + ":" + hostPort));
            
            add();          
            
            String auteurId = "auteur5";
            
            Key key = Key.createKey(Arrays.asList(MAJOR_KEY,auteurId),"auteur");
            
            ValueVersion vv2 = store.get(key);
            
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            Auteur a = new Auteur(auteurId, bytes2);
            
            System.out.println(a);
            
            
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            store.close();
        }
    }

    private static void add() {
        String auteurId = "auteur";

        Auteur auteur = new Auteur(auteurId);

        auteur.setNom("Aimé");
        auteur.setPrenom("Mathieu");
        auteur.setAdresse("Aubière");
        auteur.setPhone(String.valueOf((int) (Math.random() * 9999)));
        
        store.putIfAbsent(auteur.getStoreKey("info"), auteur.getStoreValue());

    }
    
}
