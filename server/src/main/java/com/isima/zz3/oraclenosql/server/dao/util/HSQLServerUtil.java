/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.util;

import java.io.IOException;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;

import org.hsqldb.Server;
import org.hsqldb.server.ServerConfiguration;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerAcl;

/**
 *
 * @author mathieu
 */
public class HSQLServerUtil {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(HSQLServerUtil.class);

    private static SessionFactory sessionFactory;

    private static final HSQLServerUtil UTIL = new HSQLServerUtil();  
    private Server hsqlServer;  
  
    private HSQLServerUtil() {  
        // prevent instantiation  
    }  
  
    /** 
     * @return utility instance. 
     */  
    public static HSQLServerUtil getInstance() {  
        return UTIL;  
    }  

    private void doStart(final HsqlProperties props) {

        ServerConfiguration.translateDefaultDatabaseProperty(props);

        try {
            hsqlServer = new Server();
            hsqlServer.setRestartOnShutdown(false);
            hsqlServer.setNoSystemExit(true);
            hsqlServer.setProperties(props);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(HSQLServerUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServerAcl.AclFormatException ex) {
            java.util.logging.Logger.getLogger(HSQLServerUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        LOGGER.info("Configured the HSQLDB server...");
        hsqlServer.start();
        LOGGER.info("HSQLDB server started on port " + hsqlServer.getPort() + "...");
    }

    /**
     * start the server with a database configuration.
     *
     * @param dbName the name of database
     * @param port port to listen to
     */
    public void start(final String dbName, final int port) {
        HsqlProperties props = new HsqlProperties();
        props.setProperty("server.port", port);
        props.setProperty("server.database.0", dbName);
        props.setProperty("server.dbname.0", dbName);
        doStart(props);
    }

    /**
     * start the server with a database configuration.
     *
     * @param dbName the name of database
     */
    public void start(final String dbName) {
        HsqlProperties props = new HsqlProperties();
        props.setProperty("server.database.0", dbName);
        props.setProperty("server.dbname.0", dbName);
        doStart(props);
    }

    /**
     * shutdown the started instance.
     */
    public void stop() {
        LOGGER.info("HSQLDB server shutting down...");
        hsqlServer.stop();
        LOGGER.info("HSQLDB server shutting down... done");
    }

    @PostConstruct
    public void initApp() {
        LOGGER.info("Hibernate configuring...");
    }
}
