package com.rosivanyshyn.exeption;

import com.rosivanyshyn.utils.Validation;
import org.apache.log4j.Logger;

import java.io.Serial;

/**
 * Validation layer exception
 *
 * @author Rostyslav Ivanyshyn.
 */
public class ValidationException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -8404774518851987089L;

    public ValidationException(String msg, Exception ex) {
        super(msg, ex);
    }
    public ValidationException(String msg) {
        super(msg, new RuntimeException());
    }
}
