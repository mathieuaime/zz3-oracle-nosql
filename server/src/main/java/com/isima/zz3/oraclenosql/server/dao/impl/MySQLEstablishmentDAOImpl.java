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
import com.isima.zz3.oraclenosql.server.dao.util.HibernateUtil;
import com.isima.zz3.oraclenosql.server.entity.Article;
import com.isima.zz3.oraclenosql.server.entity.Establishment;
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
public class MySQLEstablishmentDAOImpl implements EntityDAO<Establishment> {

    @Override
    public List<Establishment> get() {
        return get("");
    }

    @Override
    public Establishment save(Establishment object) {
        Transaction trns = null;
        try (Session session = HibernateUtil.newSessionFactory().openSession();) {
            trns = session.beginTransaction();
            session.saveOrUpdate(object);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            throw new DAOEntityNotSavedException("Establishment " + object + " not saved");
        }
        return object;
    }

    @Override
    public void delete(Establishment object) {
        Transaction trns = null;
        try (Session session = HibernateUtil.newSessionFactory().openSession();) {
            trns = session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            throw new DAOEntityNotFoundException("Establishment " + object + " not found");
        }
    }

    @Override
    public List<Establishment> get(String search) {
        try (Session session = HibernateUtil.newSessionFactory().openSession();) {
            return session
                    .createQuery("SELECT e FROM Establishment e WHERE e.name like :search", Establishment.class)
                    .setParameter("search", search + "%")
                    .getResultList();
        } catch (ObjectNotFoundException e) {
            throw new DAOEntityNotFoundException("Establishment " + search + " not found");
        }
    }

    @Override
    public Establishment get(long id) {
        try (Session session = HibernateUtil.newSessionFactory().openSession();) {
            Establishment establishment = session.load(Establishment.class, id);
            Hibernate.initialize(establishment);
            return establishment;
        } catch (ObjectNotFoundException e) {
            throw new DAOEntityNotFoundException("Establisment " + id + " not found");
        }
    }

    @Override
    public Page<Establishment> get(Page<?> page) {
        Page<Establishment> newPage = new Page<>(page);
        newPage.setObjects(get(page.getSearch()));
        return newPage;
    }

    @Override
    public long count(Page<?> page) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
