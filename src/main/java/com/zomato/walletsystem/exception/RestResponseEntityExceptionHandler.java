package com.zomato.walletsystem.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(value
            = { WalletException.class })
    protected ResponseEntity<ErrorDetails> handleWalletException(
            WalletException ex, WebRequest request) {
        logger.error(ex.toString());
        HttpStatus status = HttpStatus.valueOf(ex.getErrorCode());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails, status);
    }
	
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        super.handleExceptionInternal(ex,body,headers,status, request);
        logger.error(ex.toString());
        String message = (body != null)?body.toString(): ex.getMessage();
        ErrorDetails errorDetails = new ErrorDetails(new Date(),message,request.getDescription(false));
        return new ResponseEntity<>(errorDetails, status);

    }


}
