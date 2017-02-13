package daos;

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
import entities.Laboratoire;
import java.util.ArrayList;
import java.util.List;
import oracle.kv.Version;

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
           Laboratoire a = new Laboratoire(bytes2);
           String t = a.getNom();
           
           if (t.equals(nom)) {laboratoire = a; break; }      
        }
        
        return laboratoire;
        
    }
    
    public Laboratoire read(int laboratoireId) {
        Laboratoire l = null;
        Key key = Key.createKey(Arrays.asList(Laboratoire.MAJOR_KEY,String.valueOf(laboratoireId)),"info");
            
        ValueVersion vv2 = store.get(key);

        if(vv2 != null) {
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            l = new Laboratoire(bytes2);
        }
        
        return l;        
    }
    
    public List<Laboratoire> read(){
        Key myKey2 = Key.createKey(Laboratoire.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);
        
        List<Laboratoire> laboratoires = new ArrayList<>();
        
        while (i.hasNext()) 
          {
           Key k = i.next().getKey();

           ValueVersion valueVersionrecherche = store.get(k); 
           Value v = valueVersionrecherche.getValue();
           byte[] bytes2 = v.getValue();
           Laboratoire l = new Laboratoire(bytes2);
           laboratoires.add(l);
        }
        
        return laboratoires;
    }
      
    public String create(Laboratoire laboratoire) {     
        Version putIfAbsent = store.putIfAbsent(laboratoire.getStoreKey("info"), laboratoire.getStoreValue());
        return (putIfAbsent != null ? "200" : "304");    
    }

    public String create(int laboratoireId, String nom, String adresse) {     
        Laboratoire laboratoire = new Laboratoire(laboratoireId, nom,adresse);
        return create(laboratoire);
    }    
    
    public String update(int laboratoireId, String nom, String adresse) {
        Laboratoire l = read(laboratoireId);
        if(l != null) {
            if (nom != null) l.setNom(nom);      
            if (adresse != null) l.setAdresse(adresse);
            store.delete(l.getStoreKey("info"));
            store.putIfAbsent(l.getStoreKey("info"), l.getStoreValue());        
        }
        return (l != null ? "200" : "404");
    }    
    
    public String update(int laboratoireId, Laboratoire laboratoire) {
        return update(laboratoireId, laboratoire.getNom(), laboratoire.getAdresse());
    }
    
    public String delete(int laboratoireId) {
        Laboratoire a = read(laboratoireId);
        boolean delete = false;
        if (a!= null) delete = store.delete(a.getStoreKey("info"));
        
        return (delete ? "200" : "404");
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
