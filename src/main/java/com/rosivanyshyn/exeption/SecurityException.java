package com.rosivanyshyn.exeption;

import java.io.Serial;

/**
 * Security layer exception
 *
 * @author Rostyslav Ivanyshyn.
 */
public class SecurityException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -4579122583808954729L;

    public SecurityException(String msg, Exception ex) {
        super(msg, ex);
    }
}
