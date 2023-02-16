package com.rosivanyshyn.controller.context;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.log4j.Logger;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;


/**
 * Context listener class.
 *
 *  @author Rostyslav Ivanyshyn.
 */
@WebListener
public class ContextListener implements ServletContextListener {

  private static final Logger LOG = Logger.getLogger(ContextListener.class);

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    LOG.debug("Servlet context initialization starts");
    AppContext.createInstance();

    LOG.debug("Servlet context initialization finished");
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    LOG.debug("Servlet context destruction starts");
    deregisterDrivers();
    LOG.debug("Servlet context destruction finished");
  }

  private static void deregisterDrivers(){
    DriverManager.drivers()
            .forEach(driver -> {
                try {
                  DriverManager.deregisterDriver(driver);
                  LOG.debug(String.format("deregistering jdbc driver: %s", driver));
                } catch (SQLException e) {
                  LOG.error(String.format("Error deregistering driver %s", driver), e);
                }
            });
  }

}
