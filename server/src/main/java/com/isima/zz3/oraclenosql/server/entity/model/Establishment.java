/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.entity.model;

import java.util.Objects;

/**
 *
 * @author mathieu
 */
public abstract class Establishment extends Entity {
    protected String name;
    protected String address;

    public Establishment(String nom, String address) {
        this.name = nom;
        this.address = address;
    }

    public Establishment() {
        this("", "");
    }

    /**
     * @return the nom
     */
    public String getName() {
        return name;
    }

    /**
     * @param nom the nom to set
     */
    public void setName(String nom) {
        this.name = nom;
    }

    /**
     * @return the addresse
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    @Override
    public String toString() {
        return name + "/" + address;
    }   

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.address);
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
        final Establishment other = (Establishment) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return true;
    }

}
