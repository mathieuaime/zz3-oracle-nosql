/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.controller.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Mathieu
 * @param <T>
 */
public class RestResponse<T> implements Serializable {

    private String responseCode;
    private String message;
    private int code;
    private Set<T> objectList;//TODO : tester avec List + test à l'ajout et Tout en Set

    public RestResponse() {
        this(200);
    }

    public RestResponse(int code) {
        this(code, new ArrayList<>());
    }

    public RestResponse(int code, String responseCode) {
        this(code, responseCode, new ArrayList<>());
    }

    public RestResponse(int code, List<T> objectList) {
        this(code, getStatus(code), getMessage(code), objectList);
    }

    public RestResponse(int code, String responseCode, List<T> objectList) {
        this(code, responseCode, getMessage(code), objectList);
    }

    public RestResponse(int code, String responseCode, String message, List<T> objectList) {
        this.responseCode = responseCode;
        this.code = code;
        this.message = message;
        this.objectList = new TreeSet<>(objectList);
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getObjectList() {
        return new ArrayList<>(objectList);
    }

    public void setObjectList(List<T> objectList) {
        this.objectList = new TreeSet<>(objectList);
    }

    public void addObjectList(T object) {
        if (object != null) {
            this.objectList.add(object);
        }
    }

    public static String getStatus(int code) {
        String status;
        switch (code) {
            case 0:
                status = "200";
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                status = "201";
                break;

            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
                status = "409";
                break;

            case 150:
            case 151:
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
                status = "404";
                break;

            default:
                status = "500";
                break;
        }

        return status;
    }

    public static String getMessage(int code) {
        String message;
        switch (code) {
            case 0:
                message = "ok";
                break;

            case 100:
                message = "Auteur déjà créé";
                break;
            case 101:
                message = "Livre déjà créé";
                break;
            case 102:
                message = "Relation AEcrit déjà créée";
                break;
            case 103:
                message = "Relation AEteEcrit déjà créée";
                break;
            case 104:
                message = "Laboratoire déjà créé";
                break;
            case 105:
                message = "Université déjà créé";
                break;
            case 106:
                message = "Relation rattache déjà créée";
                break;
            case 107:
                message = "Keyword déjà créé";
                break;
            case 108:
                message = "Relation EstRattache déjà créée";
                break;

            case 150:
                message = "Auteur inconnu";
                break;
            case 151:
                message = "Livre inconnu";
                break;
            case 152:
                message = "Relation AEcrit inconnue";
                break;
            case 153:
                message = "Relation AEteEcrit inconnue";
                break;
            case 154:
                message = "Laboratoire inconnu";
                break;
            case 155:
                message = "Université inconnue";
                break;
            case 156:
                message = "Relation rattache inconnue";
                break;
            case 157:
                message = "Keyword inconnu";
                break;
            case 158:
                message = "Relation EstRattache inconnue";
                break;

            default:
                message = "Unknown error";
        }

        return message;
    }
}
