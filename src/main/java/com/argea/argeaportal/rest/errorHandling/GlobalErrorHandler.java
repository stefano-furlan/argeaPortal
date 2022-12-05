package com.argea.argeaportal.rest.errorHandling;


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

}
