package com.exemplo.ufmaextensao.controller;

import com.exemplo.ufmaextensao.service.RegraDeNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegraDeNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRegraDeNegocio(RegraDeNegocioException e) {
        return e.getMessage();
    }

}
