package com.rosivanyshyn.controller.context;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.log4j.Logger;


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
    // no op
    LOG.debug("Servlet context destruction finished");
  }


}
