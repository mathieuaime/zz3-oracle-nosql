/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.impl;

import com.isima.zz3.oraclenosql.server.dao.exception.DAOEntityNotFoundException;
import com.isima.zz3.oraclenosql.server.dao.exception.DAOEntityNotSavedException;
import com.isima.zz3.oraclenosql.server.dao.interfaces.EntityDAO;
import com.isima.zz3.oraclenosql.server.entity.model.Entity;
import com.isima.zz3.oraclenosql.server.entity.model.Page;
import java.util.Set;

/**
 *
 * @author mathieu
 */
public class MySQLEntityDAOImpl implements EntityDAO<Entity> {

    @Override
    public Set<Entity> get() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Entity save(Entity object) throws DAOEntityNotSavedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Entity object) throws DAOEntityNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Entity get(String search) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Entity get(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Page<Entity> get(Page<?> page) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long count(Page<?> page) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
