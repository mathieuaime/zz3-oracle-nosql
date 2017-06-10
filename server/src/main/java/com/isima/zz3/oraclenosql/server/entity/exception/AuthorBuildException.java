/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.entity.exception;

/**
 *
 * @author mathieu
 */
public class AuthorBuildException extends Exception {

    public AuthorBuildException() {
    }

    public AuthorBuildException(String message) {
        super(message);
    }

    public AuthorBuildException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorBuildException(Throwable cause) {
        super(cause);
    }

    public AuthorBuildException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
