/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author mathieu
 */
@MappedSuperclass
public abstract class Establishment implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    protected long id;

    @Column(unique = true, nullable = false)
    protected String name;

    @Column
    protected String address;

    @ManyToMany(mappedBy="authors")
    private List<Author> authors;

    public Establishment() {
        this("", "");
    }

    public Establishment(String nom, String address) {
        this.name = nom;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        return Objects.equals(this.address, other.address);
    }

}
