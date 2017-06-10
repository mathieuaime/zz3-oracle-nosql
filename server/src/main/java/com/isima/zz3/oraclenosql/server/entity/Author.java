/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.entity;

import com.isima.zz3.oraclenosql.server.entity.exception.AuthorBuildException;
import com.isima.zz3.oraclenosql.server.entity.model.Entity;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author mathieu
 */
public class Author extends Entity {
    
    private Author() {}

    private String name;
    private String firstName;
    private String address;
    private String phone;
    private String fax;
    private String mail;
    
    public static class Builder {
        private final Author author;
        
        public Builder(String name) {
            author = new Author();
            author.name = name;
        }
        
        public Builder firstName(String firstName) {
            author.firstName = firstName;
            return this;
        }
        
        public Builder address(String address) {
            author.address = address;
            return this;
        }
        
        public Builder phone(String phone) {
            author.phone = phone;
            return this;
        }
        
        public Builder fax(String fax) {
            author.fax = fax;
            return this;
        }
        
        public Builder mail(String mail) {
            author.mail = mail;
            return this;
        }
        
        public Author build() {
            return author;
        }
        
        public Author build(String[] author) throws AuthorBuildException {
            if (author.length < 6) {
                throw new AuthorBuildException();
            }
            return new Builder(author[0])
                    .firstName(author[1])
                    .address(author[2])
                    .phone(author[3])
                    .fax(author[4])
                    .mail(author[5])
                    .build();
        }
    }

    @Override
    public String toString() {
        return name + "/" + firstName + "/" + address + "/" + phone + "/" + fax + "/" + mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public List<String> getStoreKey() {
        return Arrays.asList("author", String.valueOf(name));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.firstName);
        hash = 97 * hash + Objects.hashCode(this.address);
        hash = 97 * hash + Objects.hashCode(this.phone);
        hash = 97 * hash + Objects.hashCode(this.fax);
        hash = 97 * hash + Objects.hashCode(this.mail);
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
        final Author other = (Author) obj;

        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.fax, other.fax)) {
            return false;
        }
        return Objects.equals(this.mail, other.mail);
    }
}
