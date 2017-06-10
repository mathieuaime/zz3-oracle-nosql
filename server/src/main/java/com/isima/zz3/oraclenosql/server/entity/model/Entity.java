/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.entity.model;

import java.util.List;

/**
 *
 * @author mathieu
 */
public abstract class Entity {
    public static class Builder {
        public Entity build() {
            return null;
        };
        public Entity build(String[] entity) {
            return null;
        };
    }
    
    public abstract List<String> getStoreKey();
    public String getStoreValue() {
        return toString();
    }
}
