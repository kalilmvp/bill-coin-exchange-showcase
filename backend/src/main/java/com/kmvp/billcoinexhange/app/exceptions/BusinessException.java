package com.kmvp.billcoinexhange.app.exceptions;

/**
 * @author kalil.peixoto
 * @date 4/4/25 23:15
 * @email kalilmvp@gmail.com
 */
public abstract class BusinessException extends RuntimeException{
    protected BusinessException(String message) {
        super(message);
    }
}
