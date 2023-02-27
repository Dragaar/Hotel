package com.rosivanyshyn.db.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import com.rosivanyshyn.exeption.DAOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TransactionManager provide safe database queries
 * execution as one transaction
 *
 */
public final class TransactionManager {

    private static final Logger LOG = LogManager.getLogger(TransactionManager.class);

    public static Object execute(Connection con, ExecuteOperation operation) {
        Object value = null;
        String message = "Cannot execute operation in transaction";
        try {
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            value = operation.execute();
            con.commit();
        } catch (SQLException ex) {
            rollback(con);
            LOG.error(message, ex);
            throw new DAOException(message, ex);
        } catch (Exception ex){
            rollback(con);
            LOG.error(message, ex);
            throw ex;
        }
        finally {
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
