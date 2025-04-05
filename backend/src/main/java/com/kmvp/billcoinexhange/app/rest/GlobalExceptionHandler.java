package com.kmvp.billcoinexhange.app.rest;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.kmvp.billcoinexhange.app.exceptions.BusinessException;
import com.kmvp.billcoinexhange.app.rest.models.ErrorResponse;
import com.kmvp.billcoinexhange.app.utils.MessageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * @author kalil.peixoto
 * @date 4/4/25 18:25
 * @email kalilmvp@gmail.com
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> getErrorBadRequest(final String message, final WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(message, request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException ex, WebRequest request) {
        return this.getErrorBadRequest(MessageUtils.MSG_VALUE_HAS_TO_BE_NUM, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadableException(InvalidFormatException ex, WebRequest request) {
        return this.getErrorBadRequest(MessageUtils.MSG_VALUE_HAS_TO_BE_NUM, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        String message = "Validation failed: " + ex.getBindingResult();
        ErrorResponse errorResponse = ErrorResponse.of(message, request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
