/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.interfaces;

import com.isima.zz3.oraclenosql.server.dao.exception.DAOEntityNotFoundException;
import com.isima.zz3.oraclenosql.server.dao.exception.DAOEntityNotSavedException;
import com.isima.zz3.oraclenosql.server.entity.model.Entity;
import java.util.Set;

/**
 *
 * @author mathieu
 * @param <T>
 */
public interface CRUD<T extends Entity> {
    Set<T> get();
    T save(T object) throws DAOEntityNotSavedException;
    void delete(T object) throws DAOEntityNotFoundException;
}
