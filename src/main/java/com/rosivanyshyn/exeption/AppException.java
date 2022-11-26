package com.rosivanyshyn.exeption;

public class AppException extends RuntimeException{

    private static final long serialVersionUID = -5346086590320186614L;


    public AppException(String msg, Exception ex) {
        super(msg, ex);
    }

}
