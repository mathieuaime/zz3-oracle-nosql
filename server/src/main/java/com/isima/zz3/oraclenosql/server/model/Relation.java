/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.model;

import java.util.Map;
import java.util.Objects;

/**
 *
 * @author mathieu
 * @param <T> key
 * @param <U> value
 */
public class Relation<T, U> {
    private T key;
    private Map<Long, U> values;

    public Relation(T key, Map<Long, U> values) {
        this.key = key;
        this.values = values;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public Map<Long, U> getValues() {
        return values;
    }

    public void setValues(Map<Long, U> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "Relation{" + "key=" + key + ", values=" + values + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.key);
        hash = 29 * hash + Objects.hashCode(this.values);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Relation<?, ?> other = (Relation<?, ?>) obj;
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        return Objects.equals(this.values, other.values);
    }
    
    
}
