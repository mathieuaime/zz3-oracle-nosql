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
public class ArticleBuildException extends Exception {

    public ArticleBuildException() {
    }

    public ArticleBuildException(String message) {
        super(message);
    }

    public ArticleBuildException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArticleBuildException(Throwable cause) {
        super(cause);
    }

    public ArticleBuildException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
