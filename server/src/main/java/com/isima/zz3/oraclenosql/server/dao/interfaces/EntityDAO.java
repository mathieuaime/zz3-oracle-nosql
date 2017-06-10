/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.interfaces;

import com.isima.zz3.oraclenosql.server.entity.model.Entity;

/**
 *
 * @author mathieu
 * @param <T>
 */
public interface EntityDAO<T extends Entity> extends CRUD<T>, Search<T>, Pagination<T> {
}
