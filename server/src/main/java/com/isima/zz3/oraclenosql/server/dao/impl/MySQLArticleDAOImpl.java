/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.impl;

import com.isima.zz3.oraclenosql.server.dao.exception.DAOEntityNotFoundException;
import com.isima.zz3.oraclenosql.server.dao.exception.DAOEntityNotSavedException;
import com.isima.zz3.oraclenosql.server.dao.interfaces.EntityDAO;
import com.isima.zz3.oraclenosql.server.dao.util.HSQLServerUtil;
import com.isima.zz3.oraclenosql.server.entity.Article;
import com.isima.zz3.oraclenosql.server.entity.Page;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mathieu
 */
public class MySQLArticleDAOImpl implements EntityDAO<Article> {

    @Override
    public List<Article> get() {
        return get("");
    }

    @Override
    public Article save(Article object) {
        Transaction trns = null;
        try (Session session = HSQLServerUtil.getSessionFactory().openSession();) {
            trns = session.beginTransaction();
            session.saveOrUpdate(object);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            throw new DAOEntityNotSavedException("Article " + object + " not saved");
        }
        return object;
    }

    @Override
    public void delete(Article object) {
        Transaction trns = null;
        try (Session session = HSQLServerUtil.getSessionFactory().openSession();) {
            trns = session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            throw new DAOEntityNotFoundException("Article " + object + " not found");
        }
    }

    @Override
    public List<Article> get(String search) {
        try (Session session = HSQLServerUtil.getSessionFactory().openSession();) {
            return session
                    .createQuery("SELECT a FROM Article a WHERE a.title like :search", Article.class)
                    .setParameter("search", search + "%")
                    .getResultList();
        } catch (ObjectNotFoundException e) {
            throw new DAOEntityNotFoundException("Article " + search + " not found");
        }
    }

    @Override
    public Article get(long id) {
        try (Session session = HSQLServerUtil.getSessionFactory().openSession();) {
            Article article = session.load(Article.class, id);
            Hibernate.initialize(article);
            return article;
        } catch (ObjectNotFoundException e) {
            throw new DAOEntityNotFoundException("Article " + id + " not found");
        }
    }

    @Override
    public Page<Article> get(Page<?> page) {
        Page<Article> newPage = new Page<>(page);
        newPage.setObjects(get(page.getSearch()));
        return newPage;
    }

    @Override
    public long count(Page<?> page) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
