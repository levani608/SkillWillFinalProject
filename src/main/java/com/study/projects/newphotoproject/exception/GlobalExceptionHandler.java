package com.study.projects.newphotoproject.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Invalid Params Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionBody> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {


        String message = e.getFieldErrors().stream()
                .map(f -> "Invalid " + f.getField() + ", " + f.getDefaultMessage()).collect(Collectors.joining(".\n"));

        ExceptionBody exceptionBody = new ExceptionBody(HttpStatus.BAD_REQUEST, message, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionBody);
    }

    //Response Status Exception
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionBody> handleResponseStatusException(ResponseStatusException e, HttpServletRequest request) {

        String message = e.getReason();

        ExceptionBody exceptionBody = new ExceptionBody(e.getStatusCode(), message, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), request.getRequestURI());

        return ResponseEntity.status(e.getStatusCode()).body(exceptionBody);
    }

    //Matches in Database Exception
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionBody> handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {

        String str = e.getMostSpecificCause().getMessage();

        String message = str.split("=")[1];

        ExceptionBody exceptionBody = new ExceptionBody(HttpStatus.CONFLICT, message, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionBody);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionBody> handleClientErrorException(RuntimeException e, HttpServletRequest request) {


        ExceptionBody exceptionBody = new ExceptionBody(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred!", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionBody);
    }



}
