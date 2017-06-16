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
public class Laboratory extends Establishment {

    public Laboratory() {
        super();
    }

    public Laboratory(String nom, String address) {
        super(nom, address);
    }

}
