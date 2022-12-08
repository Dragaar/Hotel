package com.rosivanyshyn.db.transaction;

/**
 * encapsulates a set of Database queries
 *
 * @param <T>
 */
@FunctionalInterface
public interface ExecuteOperation <T> {
    /**
     * Execute operation.
     *
     * @return T
     */
     T execute();
}
