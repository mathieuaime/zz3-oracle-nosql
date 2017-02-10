
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
import oracle.nosql.entities.Universite;

public class UniversiteDAO {
    
    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public UniversiteDAO() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }
    
    public Universite read(String nom) {    
        Key myKey2 = Key.createKey(Universite.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);
        
        Universite universite = new Universite();
        
        while (i.hasNext()) 
          {
           Key k = i.next().getKey();

           ValueVersion valueVersionrecherche = store.get(k); 
           Value v = valueVersionrecherche.getValue();
           byte[] bytes2 = v.getValue();
           Universite a = new Universite(Integer.parseInt(k.getMajorPath().get(1)), bytes2);
           String t = a.getNom();
           
           if (t.equals(nom)) {universite = a; break; }      
        }
        
        return universite;
        
    }
    
    public Universite read(int universiteId) {
        Key key = Key.createKey(Arrays.asList(Universite.MAJOR_KEY,String.valueOf(universiteId)),"info");
            
        ValueVersion vv2 = store.get(key);

        Value value2 = vv2.getValue();
        byte[] bytes2 = value2.getValue();
        Universite a = new Universite(universiteId, bytes2);
        
        return a;        
    }
      
    public Universite create(Universite universite) {     
        store.putIfAbsent(universite.getStoreKey("info"), universite.getStoreValue());
        return universite;    
    }

    public Universite create(int universiteId, String nom, String adresse) {     
        Universite universite = new Universite(universiteId, nom,adresse);
        return create(universite);
    }    
    
    public void update(int universiteId, String nom, String adresse) {
        Universite a = read(universiteId);
        if (nom != null) a.setNom(nom);      
        if (adresse != null) a.setAdresse(adresse);
        store.delete(a.getStoreKey("info"));
        store.putIfAbsent(a.getStoreKey("info"), a.getStoreValue());        
    }    
    
    public void delete(int universiteId) {
        Universite a = read(universiteId);
        store.delete(a.getStoreKey("info"));
    }
    
    public void genererTest(int n) {       

        for (int i = 0; i < n; i++) {
            create(i, "Universite"+i, "Mathieu");
        } 
    }

    public void afficherTest(int n) {       

        for (int i = 0; i < n; i++) {
            Universite a = read(i);
            System.out.println(a);
        } 
    }
    
    public void supprimerTest(int n) {       

        for (int i = 0; i < n; i++) {
            delete(i);
        } 
    }
    
}
