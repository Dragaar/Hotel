package com.rosivanyshyn.db.manager;

import com.rosivanyshyn.exeption.DBException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {
    private static final Logger LOG = Logger.getLogger(DBManager.class);
    //private InputStream appConfigPath = getClass().getResourceAsStream("app.properties");
    private static Properties appProps = new Properties();

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        try {
            appProps.load(new FileInputStream("src/main/resources/app.properties"));
        } catch (IOException e){ e.printStackTrace();}

        config.setJdbcUrl( appProps.getProperty("connection.url") );
        config.setUsername( appProps.getProperty("connection.username") );
        config.setPassword( appProps.getProperty("connection.password") );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        config.setMinimumIdle(20);
        config.setMaximumPoolSize(50);
        ds = new HikariDataSource( config );
    }

    private DBManager() {}

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException ex){
            LOG.error("Cannot establish DB connection", ex);
            throw new DBException("Cannot establish DB connection ", ex);
        }
    }
}
