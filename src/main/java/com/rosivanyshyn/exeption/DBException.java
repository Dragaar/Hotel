package com.rosivanyshyn.exeption;

public class DBException extends RuntimeException{

    private static final long serialVersionUID = -5814167690292246702L;

    public DBException(String message, Exception cause) {
        super(message, cause);
    }
}
