package com.argea.argeaportal.rest.errorHandling;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalErrorHandler {

    /*@Autowired
    AnomaliesNotificationService anomaliesNotificationService;*/

    @ExceptionHandler({PortalOperationNotPermittedException.class})
    public ResponseEntity<Object> handle(PortalOperationNotPermittedException e, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(e.getLocalizedMessage());
        PortalErrorResponse error = new PortalErrorResponse(e.getMessage(), e.getCode(), e.getType());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PortalInternalCodeException.class})
    public ResponseEntity<Object> handle(PortalInternalCodeException e, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(e.getLocalizedMessage());
        //TODO: mandare mail a sviluppo?
        PortalErrorResponse error = new PortalErrorResponse(e.getMessage(), e.getCode(), e.getType());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PortalRecoverableErrorException.class})
    public ResponseEntity<Object> handle(PortalRecoverableErrorException e, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(e.getLocalizedMessage());
        PortalErrorResponse error = new PortalErrorResponse(e.getMessage(), e.getCode(), e.getType());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PortalResourceNotFoundException.class})
    public ResponseEntity<Object> handle(PortalResourceNotFoundException e, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(e.getLocalizedMessage());
        PortalErrorResponse error = new PortalErrorResponse(e.getMessage(), e.getCode(), e.getType());
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PortalSecurityException.class})
    public ResponseEntity<Object> handle(PortalSecurityException e, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(e.getLocalizedMessage());
        PortalErrorResponse error = new PortalErrorResponse(e.getMessage(), e.getCode(), e.getType());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PortalIntersystemException.class})
    public ResponseEntity<Object> handle(PortalIntersystemException e, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(e.getLocalizedMessage());
        //anomaliesNotificationService.notify(e.getMessage());
        PortalErrorResponse error = new PortalErrorResponse(e.getMessage(), e.getCode(), e.getType());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
