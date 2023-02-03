package com.rosivanyshyn.exeption;

import java.io.Serial;

/**
 * DAO layer exception
 *
 * @author Rostyslav Ivanyshyn.
 */
public class DAOException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -8160603862424549851L;

    public DAOException(String s, Exception ex) {
        super(s, ex);
    }
}
