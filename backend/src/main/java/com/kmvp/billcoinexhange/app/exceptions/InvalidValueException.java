package com.kmvp.billcoinexhange.app.exceptions;

/**
 * @author kalil.peixoto
 * @date 4/2/25 20:01
 * @email kalilmvp@gmail.com
 */
public class InvalidValueException extends BusinessException{
    public InvalidValueException(String message) {
        super(message);
    }
}
