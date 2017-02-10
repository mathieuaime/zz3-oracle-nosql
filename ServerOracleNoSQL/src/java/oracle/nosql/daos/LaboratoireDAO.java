package oracle.nosql.daos;

import java.util.Arrays;
import java.util.Iterator;
import oracle.kv.Direction;
import oracle.kv.KVStore;
import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;
import oracle.kv.Key;
import oracle.kv.KeyValueVersion;
import oracle.kv.Value;
import oracle.kv.ValueVersion;
import oracle.nosql.entities.Laboratoire;

public class LaboratoireDAO {
    
    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public LaboratoireDAO() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }
    
    public Laboratoire read(String nom) {    
        Key myKey2 = Key.createKey(Laboratoire.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);
        
        Laboratoire laboratoire = new Laboratoire();
        
        while (i.hasNext()) 
          {
           Key k = i.next().getKey();

           ValueVersion valueVersionrecherche = store.get(k); 
           Value v = valueVersionrecherche.getValue();
           byte[] bytes2 = v.getValue();
           Laboratoire a = new Laboratoire(Integer.parseInt(k.getMajorPath().get(1)), bytes2);
           String t = a.getNom();
           
           if (t.equals(nom)) {laboratoire = a; break; }      
        }
        
        return laboratoire;
        
    }
    
    public Laboratoire read(int laboratoireId) {
        Key key = Key.createKey(Arrays.asList(Laboratoire.MAJOR_KEY,String.valueOf(laboratoireId)),"info");
            
        ValueVersion vv2 = store.get(key);

        Value value2 = vv2.getValue();
        byte[] bytes2 = value2.getValue();
        Laboratoire a = new Laboratoire(laboratoireId, bytes2);
        
        return a;        
    }
      
    public Laboratoire create(Laboratoire laboratoire) {     
        store.putIfAbsent(laboratoire.getStoreKey("info"), laboratoire.getStoreValue());
        return laboratoire;    
    }

    public Laboratoire create(int laboratoireId, String nom, String adresse) {     
        Laboratoire laboratoire = new Laboratoire(laboratoireId, nom,adresse);
        return create(laboratoire);
    }    
    
    public void update(int laboratoireId, String nom, String adresse) {
        Laboratoire a = read(laboratoireId);
        if (nom != null) a.setNom(nom);      
        if (adresse != null) a.setAdresse(adresse);
        store.delete(a.getStoreKey("info"));
        store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());        
    }    
    
    public void delete(int laboratoireId) {
        Laboratoire a = read(laboratoireId);
        store.delete(a.getStoreKey("info"));
    }
    
    public void genererTest(int n) {       

        for (int i = 0; i < n; i++) {
            create(i, "Laboratoire"+i, "Mathieu");
        } 
    }

    public void afficherTest(int n) {       

        for (int i = 0; i < n; i++) {
            Laboratoire a = read(i);
            System.out.println(a);
        } 
    }
    
    public void supprimerTest(int n) {       

        for (int i = 0; i < n; i++) {
            delete(i);
        } 
    }
    
}
