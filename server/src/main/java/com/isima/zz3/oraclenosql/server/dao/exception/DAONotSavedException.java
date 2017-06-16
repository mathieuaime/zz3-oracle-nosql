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
public class DAONotSavedException extends RuntimeException {

    public DAONotSavedException() {
    }

    public DAONotSavedException(String message) {
        super(message);
    }

    public DAONotSavedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAONotSavedException(Throwable cause) {
        super(cause);
    }

    public DAONotSavedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    
}
