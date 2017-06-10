/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.entity.model;

import com.isima.zz3.oraclenosql.server.entity.exception.RelationBuildException;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author mathieu
 * @param <T> key
 * @param <U> value
 */
public abstract class Relation<T extends Entity, U extends Entity> {
    protected T key;
    protected List<U> value;

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public List<U> getValue() {
        return value;
    }

    public void setValue(List<U> value) {
        this.value = value;
    }
    
    public abstract List<String> getStoreKey();
    public String getStoreValue() {
        return toString();
    }

    @Override
    public String toString() {
        return key + "/" + value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.key);
        hash = 41 * hash + Objects.hashCode(this.value);
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
        return Objects.equals(this.value, other.value);
    }  
}
