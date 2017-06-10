/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.interfaces;

import com.isima.zz3.oraclenosql.server.dao.exception.DAOEntityNotFoundException;
import com.isima.zz3.oraclenosql.server.entity.model.Entity;

/**
 *
 * @author mathieu
 * @param <T>
 */
public interface Search<T extends Entity> {
    T get(String search) throws DAOEntityNotFoundException;
    T get(long id) throws DAOEntityNotFoundException;
}
