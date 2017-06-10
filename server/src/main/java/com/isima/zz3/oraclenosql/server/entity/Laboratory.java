/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.entity;

import com.isima.zz3.oraclenosql.server.entity.model.Establishment;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author mathieu
 */
public class Laboratory extends Establishment {

    @Override
    public List<String> getStoreKey() {
        return Arrays.asList("laboratory", String.valueOf(name));
    }
}
