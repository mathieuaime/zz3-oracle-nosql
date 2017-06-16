/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.impl;

import com.isima.zz3.oraclenosql.server.dao.exception.DAONotFoundException;
import com.isima.zz3.oraclenosql.server.dao.exception.DAONotSavedException;
import com.isima.zz3.oraclenosql.server.dao.interfaces.EntityDAO;
import com.isima.zz3.oraclenosql.server.dao.util.HibernateUtil;
import com.isima.zz3.oraclenosql.server.entity.Establishment;
import com.isima.zz3.oraclenosql.server.entity.Page;
import java.util.ArrayList;
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

    private static final String QUERY_SELECT_SEARCH
            = "SELECT e FROM Establishment e WHERE e.name like :search";
    
    private static final String QUERY_SELECT_SEARCH_AND_TYPE
            = "SELECT e FROM Establishment e WHERE e.DTYPE like :type AND e.name like :search";

    @Override
    public List get() {
        return get("");
    }

    @Override
    public Establishment save(Establishment object) {
        Transaction trns = null;
        try (Session session
                = HibernateUtil.newSessionFactory().openSession();) {
            trns = session.beginTransaction();
            session.saveOrUpdate(object);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            throw new DAONotSavedException("Establishment "
                    + object + " not saved");
        }
        return object;
    }

    @Override
    public void delete(Establishment object) {
        Transaction trns = null;
        try (Session session
                = HibernateUtil.newSessionFactory().openSession();) {
            trns = session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            throw new DAONotFoundException("Establishment "
                    + object + " not found");
        }
    }

    @Override
    public List get(String search) {
        List l = new ArrayList();
        try (Session session
                = HibernateUtil.newSessionFactory().openSession();) {
            l = session
                    .createQuery(QUERY_SELECT_SEARCH)
                    .setParameter("search", search + "%")
                    .getResultList();
        } catch (ObjectNotFoundException e) {
            throw new DAONotFoundException("Establishment "
                    + search + " not found");
        }
        
        return l;
    }
    
    public List get(String search, String type) {
        List l = new ArrayList();
        try (Session session
                = HibernateUtil.newSessionFactory().openSession();) {
            l = session
                    .createQuery(QUERY_SELECT_SEARCH_AND_TYPE)
                    .setParameter("search", search + "%")
                    .setParameter("type", type)
                    .getResultList();
        } catch (ObjectNotFoundException e) {
            throw new DAONotFoundException("Establishment "
                    + search + " not found");
        }
        
        return l;
    }

    @Override
    public Establishment get(long id) {
        try (Session session
                = HibernateUtil.newSessionFactory().openSession();) {
            Establishment establishment = session.load(Establishment.class, id);
            Hibernate.initialize(establishment);
            return establishment;
        } catch (ObjectNotFoundException e) {
            throw new DAONotFoundException("Establisment "
                    + id + " not found");
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
