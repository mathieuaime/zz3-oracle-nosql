/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.model;

import java.util.Objects;

/**
 *
 * @author mathieu
 */
public class Establishment {
    private int id;
    private String name;
    private String address;

    public Establishment(int id, String nom, String address) {
        this.name = nom;
        this.address = address;
        this.id = id;
    }

    public Establishment() {
        this(0, "", "");
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @param addresse the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Establishment{" + "id=" + id + ", name=" + name + ", address=" + address + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.address);
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
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return true;
    }
}
