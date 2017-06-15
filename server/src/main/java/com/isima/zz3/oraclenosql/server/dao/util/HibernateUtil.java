/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.dao.util;

import com.isima.zz3.oraclenosql.server.entity.Article;
import com.isima.zz3.oraclenosql.server.entity.Author;
import com.isima.zz3.oraclenosql.server.entity.Laboratory;
import com.isima.zz3.oraclenosql.server.entity.University;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;

/**
 *
 * @author excilys
 */
public class HibernateUtil {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(HibernateUtil.class);

    public static SessionFactory newSessionFactory(final String pathToHibernateCfgXml) {
        LOGGER.info("Loading Hibernate Session Factory with configurations from file "
                + pathToHibernateCfgXml + "...");
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure(pathToHibernateCfgXml);
            configuration.addAnnotatedClass(Article.class);
            configuration.addAnnotatedClass(Author.class);
            configuration.addAnnotatedClass(Laboratory.class);
            configuration.addAnnotatedClass(University.class);
            LOGGER.info("Hibernate Configuration loaded");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            LOGGER.info("Hibernate serviceRegistry created");

            return configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException ex) {
            LOGGER.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory newSessionFactory() {
        return newSessionFactory("config/db/hibernate.cfg.xml");
    }
}
