package com.rosivanyshyn.exeption;

import java.io.Serial;

/**
 * Database layer exception
 *
 * @author Rostyslav Ivanyshyn.
 */
public class DBException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -5814167690292246702L;

    public DBException(String message, Exception cause) {
        super(message, cause);
    }
}
