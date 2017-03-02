/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.text.ParseException;
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
import entities.Article;

/**
 *
 * @author mathieu
 */
public class ArticleDAO {

    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;

    public ArticleDAO() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }

    public List<Article> read() throws ParseException {
        Key myKey2 = Key.createKey(Article.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);

        List<Article> livres = new ArrayList<>();

        while (i.hasNext()) {
            Key k = i.next().getKey();

            ValueVersion valueVersionrecherche = store.get(k);
            Value v = valueVersionrecherche.getValue();
            byte[] bytes2 = v.getValue();
            Article l = new Article(bytes2);
            livres.add(l);
        }

        return livres;
    }

    public Article read(String titre) throws ParseException {
        Key myKey2 = Key.createKey(Article.MAJOR_KEY);
        Iterator<KeyValueVersion> i = store.storeIterator(Direction.UNORDERED, 0, myKey2, null, null);

        Article livre = new Article();

        while (i.hasNext()) {
            Key k = i.next().getKey();

            ValueVersion valueVersionrecherche = store.get(k);
            Value v = valueVersionrecherche.getValue();
            byte[] bytes2 = v.getValue();
            Article l = new Article(bytes2);
            String t = l.getTitre();

            if (t.equals(titre)) {
                livre = l;
                break;
            }
        }

        return livre;

    }

    public Article read(int livreId) throws ParseException {
        Article l = null;

        Key key = Key.createKey(Arrays.asList(Article.MAJOR_KEY, String.valueOf(livreId)), "info");

        ValueVersion vv2 = store.get(key);

        if (vv2 != null) {
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            l = new Article(bytes2);
        }

        return l;
    }
    
    public boolean exist(int articleId) {
        Key key = Key.createKey(Arrays.asList(Article.MAJOR_KEY, String.valueOf(articleId)), "info");
        return store.get(key) != null;
    }

    public int create(Article livre) {
        Version putIfAbsent = store.putIfAbsent(livre.getStoreKey("info"), livre.getStoreValue());
        return (putIfAbsent != null ? 0 : 101);
    }

    public int create(int livreId, String titre, String resume, float prix) {
        return create(new Article(livreId, titre, resume, prix));
    }

    public int update(int idLivre, Article livre) throws ParseException {
        return update(idLivre, livre.getTitre(), livre.getResume(), livre.getPrix());
    }

    public int update(int livreId, String titre, String resume, float prix) throws ParseException {
        if (exist(livreId)) {
            Article l = read(livreId);
            if (titre != null) {
                l.setTitre(titre);
            }
            if (resume != null) {
                l.setResume(resume);
            }
            if (prix >= 0) {
                l.setPrix(prix);
            }
            store.delete(l.getStoreKey("info"));
            store.putIfAbsent(l.getStoreKey("info"), l.getStoreValue());
            
            return 0;
        } else {
            return 151;
        }
    }

    public int delete(int livreId) throws ParseException {
        boolean delete = false;
        if (exist(livreId)) {
            delete = store.delete(read(livreId).getStoreKey("info"));
        }
        return (delete ? 0 : 151);
    }

    public void genererTest(int n) {

        for (int i = 0; i < n; i++) {
            create(i, "Le bateau" + i, "Une histoire de bateau", 20);
        }
    }

    public void afficherTest(int n) throws ParseException {

        for (int i = 0; i < n; i++) {
            Article l = read(i);
            System.out.println(l);
        }
    }

    public void supprimerTest(int n) throws ParseException {

        for (int i = 0; i < n; i++) {
            delete(i);
        }
    }
}
