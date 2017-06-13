/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.exception;

/**
 *
 * @author mathieu
 */
public class DAOEntityNotSavedException extends Exception {

    public DAOEntityNotSavedException() {
    }

    public DAOEntityNotSavedException(String message) {
        super(message);
    }

    public DAOEntityNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOEntityNotSavedException(Throwable cause) {
        super(cause);
    }

    public DAOEntityNotSavedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    
}
