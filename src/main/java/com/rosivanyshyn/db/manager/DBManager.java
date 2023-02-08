package com.rosivanyshyn.db.manager;

import java.sql.Connection;
/**
 * DB Manager interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface DBManager {
    /** Get configured database connection with exception handling
     *
     * @return database connection
     */
    Connection getConnection();
}
