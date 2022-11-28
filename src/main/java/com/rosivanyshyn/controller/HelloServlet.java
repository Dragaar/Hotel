package com.rosivanyshyn.controller;

import com.rosivanyshyn.db.transaction.TransactionManager;
//import org.apache.log4j.Logger;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.apache.log4j.Logger;

import org.apache.log4j.lf5.DefaultLF5Configurator;


@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {


   private static final Logger LOG = Logger.getLogger(HelloServlet.class);
    private String message;

    public void init() {
        message = "Hello World444!";

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DefaultLF5Configurator.configure();
        response.setContentType("text/html");
        LOG.error("tesing info message");
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("Logger name " + LOG.getName());
        out.println("Path log " + "${catalina.base}");
        out.println("</body></html>");

        LOG.error("tesing info message");



    }

    public void destroy() {
    }
}