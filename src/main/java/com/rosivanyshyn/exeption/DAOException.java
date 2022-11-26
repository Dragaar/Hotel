package com.rosivanyshyn.exeption;

public class DAOException extends RuntimeException{

    private static final long serialVersionUID = -8160603862424549851L;

    public DAOException(String s, Exception ex) {
        super(s, ex);
    }
}
