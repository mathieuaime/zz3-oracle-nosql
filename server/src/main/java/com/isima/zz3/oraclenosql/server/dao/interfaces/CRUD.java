/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.interfaces;

import java.util.List;

/**
 *
 * @author mathieu
 * @param <T>
 */
public interface CRUD<T> {
    List<T> get();
    T save(T object);
    void delete(T object);
}
