package com.rosivanyshyn.db.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import com.rosivanyshyn.exeption.DAOException;

import org.apache.log4j.Logger;


/**
 * TransactionManager provide safe database queries
 * execution as one transaction
 *
 */
public final class TransactionManager {

    private static final Logger LOG = Logger.getLogger(TransactionManager.class);

    public static Object execute(Connection con, ExecuteOperation operation) {
        Object value = null;
        try {
            con.setAutoCommit(false);
            value = operation.execute();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error("Cannot execute operation in transaction", ex);
            throw new DAOException("Cannot execute operation in transaction", ex);
        } finally {
            if (con != null) {
                close(con);
            }
        }
        return value;
    }

    private static void close(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            LOG.error("Cannot close connection", ex);
            throw new DAOException("Cannot close connection", ex);
        }
    }

    private static void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOG.error("Cannot rollback", ex);
                throw new DAOException("Cannot rollback", ex);
            }
        }
    }
}
