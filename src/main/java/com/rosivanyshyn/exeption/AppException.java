package com.rosivanyshyn.exeption;

import java.io.Serial;

/**
 * Application layer exception
 *
 * @author Rostyslav Ivanyshyn.
 */
public class AppException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -5346086590320186614L;

    public AppException(String msg, Exception ex) {
        super(msg, ex);
    }

}
