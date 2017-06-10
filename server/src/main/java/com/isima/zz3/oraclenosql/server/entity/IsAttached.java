/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.entity;

import com.isima.zz3.oraclenosql.server.entity.exception.ArticleBuildException;
import com.isima.zz3.oraclenosql.server.entity.model.Establishment;
import com.isima.zz3.oraclenosql.server.entity.model.Relation;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author mathieu
 */
public class IsAttached extends Relation<Author, Establishment> {

    @Override
    public List<String> getStoreKey() {
        return Arrays.asList("is_attached", key.getName());
    }
    
    @Override
    public String getStoreValue() {
        return value.stream().map(a -> a.getName()).collect(Collectors.joining(","));
    }
}
