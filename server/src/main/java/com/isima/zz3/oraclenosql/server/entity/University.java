/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.entity;

import javax.persistence.Entity;

/**
 *
 * @author mathieu
 */
@Entity
public class University extends Establishment {

    public University() {
        super();
    }

    public University(String name, String address) {
        super(name, address);
    }
}
