package com.rosivanyshyn.db.manager;

import com.rosivanyshyn.exeption.DBException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/** Database connection manager.
 * <br> Configure Hikari connection pool and provides access to it
 *
 *  @author Rostyslav Ivanyshyn.
 */
public class DBManager {
    private static final Logger LOG = LogManager.getLogger(DBManager.class);
    private static Properties appProps;

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {

        appProps = getProperties();
        config.setJdbcUrl( appProps.getProperty("connection.url") );
        config.setUsername( appProps.getProperty("connection.username") );
        config.setPassword( appProps.getProperty("connection.password") );
        config.setDriverClassName(appProps.getProperty("connection.driver"));

        config.addDataSourceProperty( "cachePrepStmts" ,
                appProps.getProperty("connection.datasource.cachePrepStmts"));
        config.addDataSourceProperty( "prepStmtCacheSize" ,
                appProps.getProperty("connection.datasource.prepStmtCacheSize") );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" ,
                appProps.getProperty("connection.datasource.prepStmtCacheSqlLimit") );

        config.setMinimumIdle(Integer.parseInt(
                appProps.getProperty("connection.minimumIdle")
        ));
        config.setMaximumPoolSize(Integer.parseInt(
                appProps.getProperty("connection.maximumPoolSize")
        ));
        ds = new HikariDataSource( config );

    }

    private DBManager() {
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException ex){
            LOG.error("Cannot establish DB connection", ex);
            throw new DBException("Cannot establish DB connection ", ex);
        }
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        String connectionFile = "app.properties";
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream resource = classLoader.getResourceAsStream(connectionFile)){
            properties.load(resource);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return properties;
    }
}
