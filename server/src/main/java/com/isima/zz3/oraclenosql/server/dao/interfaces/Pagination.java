/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.interfaces;

import com.isima.zz3.oraclenosql.server.entity.Page;

/**
 *
 * @author mathieu
 * @param <T>
 */
public interface Pagination<T> {
    Page<T> get(Page<?> page);
    long count(Page<?> page);
}
