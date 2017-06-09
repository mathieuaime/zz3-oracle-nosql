/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao;

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
import com.isima.zz3.oraclenosql.server.relation.ArticleKeyword;
import java.text.ParseException;

/**
 *
 * @author mathieu
 */
public class KeywordDAO {

    private final String storeName = "kvstore";
    private final String hostName = "localhost";
    private final String hostPort = "5000";
    private static KVStore store;
    
    private ArticleDAO adao = new ArticleDAO();

    public KeywordDAO() {
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }

    public List<ArticleKeyword> read(String keyword) {
        return read(keyword, "info");
    }

    public List<ArticleKeyword> read(String keyword, String minorPath) {
        Key key = Key.createKey(Arrays.asList(ArticleKeyword.MAJOR_KEY, keyword));

        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);

        ArrayList<ArticleKeyword> ecrits = new ArrayList<>();

        while (it.hasNext()) {
            Key k = it.next().getKey();

            if (k.getMinorPath().get(0).equals(minorPath)) {

                ValueVersion vv2 = store.get(k);

                Value value2 = vv2.getValue();
                byte[] bytes2 = value2.getValue();
                ArticleKeyword a = new ArticleKeyword(bytes2);
                ecrits.add(a);
            }
        }

        return ecrits;
    }

    public ArticleKeyword read(String keyword, int rank) {
        return read(keyword, rank, "info");
    }

    public ArticleKeyword read(String keyword, int rank, String minorPath) {
        Key key = Key.createKey(Arrays.asList(ArticleKeyword.MAJOR_KEY, keyword, String.valueOf(rank)), minorPath);

        ArticleKeyword a = null;

        ValueVersion vv2 = store.get(key);

        if (vv2 != null) {
            Value value2 = vv2.getValue();
            byte[] bytes2 = value2.getValue();
            a = new ArticleKeyword(bytes2);
        }
        return a;
    }
    
    public boolean exist(String keyword, int rank, String minorPath) {
        Key key = Key.createKey(Arrays.asList(ArticleKeyword.MAJOR_KEY, keyword, String.valueOf(rank)), minorPath);
        return store.get(key) != null;
    }

    public int getRank(String keyword, int idArticle) {
        return getRank(keyword, idArticle, "info");
    }

    public int getRank(String keyword, int idArticle, String minorPath) {
        Key key = Key.createKey(Arrays.asList(ArticleKeyword.MAJOR_KEY, keyword));

        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);

        int rg = -1;

        while (it.hasNext()) {
            Key k = it.next().getKey();

            if (k.getMinorPath().get(0).equals(minorPath)) {

                List<String> majorPath = k.getMajorPath();
                String rank = majorPath.get(2);

                ValueVersion vv2 = store.get(k);

                Value value2 = vv2.getValue();
                byte[] bytes2 = value2.getValue();
                ArticleKeyword a = new ArticleKeyword(bytes2);

                if (a.getIdArticle() == idArticle) {
                    rg = Integer.parseInt(rank);
                    break;
                }
            }
        }
        return rg;
    }

    private int getLastRank(String keyword, String minorPath) {
        Key key = Key.createKey(Arrays.asList(ArticleKeyword.MAJOR_KEY, keyword));

        Iterator<KeyValueVersion> it = store.storeIterator(Direction.UNORDERED, 0, key, null, null);

        int rank = 0;

        while (it.hasNext()) {
            Key k = it.next().getKey();

            if (k.getMinorPath().get(0).equals(minorPath)) {
                rank = Integer.max(rank, Integer.parseInt(k.getMajorPath().get(2)));
            }
        }

        return rank;
    }

    public int create(ArticleKeyword a) throws ParseException {
        return create(a, "info");
    }

    public int create(ArticleKeyword a, String minorPath) throws ParseException {
        if (a.getRank() < 0) {
            a.setRank(1 + getLastRank(a.getKeyword(), minorPath));
        }
        
        boolean trouve = false;
        
        for(ArticleKeyword k : read(a.getKeyword())) {
            if (a.getIdArticle() == k.getIdArticle()) {
                trouve = true;
                break;
            }
        }
        if(!trouve) { 
            
            if (adao.exist(a.getIdArticle())) {
                Version putIfAbsent = store.putIfAbsent(a.getStoreKey(minorPath), a.getStoreValue());
                return (putIfAbsent != null ? 0 : 107);
            } else {
                return 151;
            }
        } else {
            return 107;
        }
    }

    public int create(String keyword, int idArticle) throws ParseException {
        return create(keyword, idArticle, "info");
    }

    public int create(String keyword, int idArticle, String minorPath) throws ParseException {
        return create(keyword, idArticle, 1 + getLastRank(keyword, minorPath), minorPath);
    }

    public int create(String keyword, int idArticle, int rank) throws ParseException {
        return create(keyword, idArticle, rank, "info");
    }

    public int create(String keyword, int idArticle, int rank, String minorPath) throws ParseException {
        return create(new ArticleKeyword(keyword, rank, idArticle), minorPath);
    }

    public int update(String keyword, int idArticle, int newIdArticle) throws ParseException {
        return update(keyword, idArticle, newIdArticle, "info");
    }

    public int update(String keyword, int idArticle, int newIdArticle, String minorPath) throws ParseException {
        
        if (exist(keyword, getRank(keyword, idArticle, minorPath), minorPath)) {
            ArticleKeyword a = read(keyword, getRank(keyword, idArticle, minorPath), minorPath);
            if (adao.exist(a.getIdArticle())) {
                a.setIdArticle(newIdArticle);
                store.delete(a.getStoreKey(minorPath));
                store.putIfAbsent(a.getStoreKey(minorPath), a.getStoreValue());
                return 0;
            } else {
                return 151;
            }
        } else {
            return 157;
        }
    }

    public int delete(String keyword) {
        return delete(keyword, "info");
    }

    public int delete(String keyword, String minorPath) {
        int result = 157;
        for (ArticleKeyword a : read(keyword, minorPath)) {
            store.delete(a.getStoreKey(minorPath));
            result = 0;
        }

        return result;
    }

    public int delete(String keyword, int rank) {
        return delete(keyword, rank, "info");
    }

    public int delete(String keyword, int rank, String minorPath) {
        boolean delete = false;
        
        if (exist(keyword, rank, minorPath)) {
            delete = store.delete(read(keyword, rank, minorPath).getStoreKey(minorPath));
        }

        return (delete ? 0 : 157);
    }

    public void genererTest(int n) throws ParseException {

        for (int i = 0; i < n; i += 2) {
            create("AimÃ©" + i, (2 * i), 1, "demo");
            create("AimÃ©" + i, (1 + 2 * i), 2, "demo");
        }
    }

    public void afficherTest(int n) {

        for (int i = 0; i < n; i++) {
            ArticleKeyword a = read("AimÃ©" + i, 1, "demo");
            System.out.println(a);
            a = read("AimÃ©" + i, 2, "demo");
            System.out.println(a);
        }
    }

    public void supprimerTest(int n) {

        for (int i = 0; i < n; i += 2) {
            delete("AimÃ©" + i, 1, "demo");
            delete("AimÃ©" + i, 2, "demo");
        }
    }
}
