/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.entity;

import java.util.List;

/**
 *
 * @author mathieu
 * @param <T>
 */
public class Page<T> {

    private String search;
    private List objects;

    public Page() {
    }
    
    public Page(Page<?> page) {
        this.search = page.search;
    }

    public String getSearch() {
        return search;}

    public void setSearch(String search) {
        this.search = search;
    }

    public List getObjects() {
        return objects;
    }

    public void setObjects(List articles) {
        this.objects = objects;
    }

}
