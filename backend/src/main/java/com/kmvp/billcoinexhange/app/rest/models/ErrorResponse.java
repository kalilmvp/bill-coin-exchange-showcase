package com.kmvp.billcoinexhange.app.rest.models;

import java.time.LocalDateTime;

/**
 * @author kalil.peixoto
 * @date 4/4/25 18:26
 * @email kalilmvp@gmail.com
 */
public record ErrorResponse(LocalDateTime timestamp, String message, String details) {
    public static ErrorResponse of(String message, String details) {
        return new ErrorResponse(LocalDateTime.now(), message, details);
    }
}
