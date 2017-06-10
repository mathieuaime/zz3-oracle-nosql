/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.entity;

import com.isima.zz3.oraclenosql.server.entity.model.Relation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author mathieu
 */
public class Wrote extends Relation<Author, Article> {

    @Override
    public List<String> getStoreKey() {
        return Arrays.asList("wrote", key.getName());
    }
    
    @Override
    public String getStoreValue() {
        return value.stream().map(a -> a.getTitle()).collect(Collectors.joining(","));
    }
}
