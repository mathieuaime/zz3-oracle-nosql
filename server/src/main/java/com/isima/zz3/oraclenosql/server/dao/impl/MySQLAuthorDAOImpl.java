/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.impl;

import com.isima.zz3.oraclenosql.server.dao.exception.DAOEntityNotFoundException;
import com.isima.zz3.oraclenosql.server.dao.exception.DAOEntityNotSavedException;
import com.isima.zz3.oraclenosql.server.dao.interfaces.EntityDAO;
import com.isima.zz3.oraclenosql.server.dao.util.HibernateUtil;
import com.isima.zz3.oraclenosql.server.entity.Article;
import com.isima.zz3.oraclenosql.server.entity.Author;
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
public class MySQLAuthorDAOImpl implements EntityDAO<Author> {

    @Override
    public List<Author> get() {
        return get("");
    }

    @Override
    public Author save(Author object) {
        Transaction trns = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            trns = session.beginTransaction();
            session.saveOrUpdate(object);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            throw new DAOEntityNotSavedException("Author " + object + " not saved");
        }
        return object;
    }

    @Override
    public void delete(Author object) {
        Transaction trns = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            trns = session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            throw new DAOEntityNotFoundException("Author " + object + " not found");
        }
    }

    @Override
    public List<Author> get(String search) {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            return session
                    .createQuery("SELECT a FROM Author a WHERE a.name like :search", Author.class)
                    .setParameter("search", search + "%")
                    .getResultList();
        } catch (ObjectNotFoundException e) {
            throw new DAOEntityNotFoundException("Author " + search + " not found");
        }
    }

    @Override
    public Author get(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession();) {
            Author author = session.load(Author.class, id);
            Hibernate.initialize(author);
            return author;
        } catch (ObjectNotFoundException e) {
            throw new DAOEntityNotFoundException("Author " + id + " not found");
        }
    }

    @Override
    public Page<Author> get(Page<?> page) {
        Page<Author> newPage = new Page<>(page);
        newPage.setObjects(get(page.getSearch()));
        return newPage;
    }

    @Override
    public long count(Page<?> page) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
