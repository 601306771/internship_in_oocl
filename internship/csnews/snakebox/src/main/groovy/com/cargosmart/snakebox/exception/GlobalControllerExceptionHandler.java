package com.cargosmart.snakebox.exception;

import java.util.UUID;

import com.cargosmart.snakebox.properties.EmailProperties;
import com.cargosmart.snakebox.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class GlobalControllerExceptionHandler {

    @Autowired
    NotificationService notificationService;
    @Autowired
    EmailProperties emailProperties;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody ResponseEntity<String> handleException(Exception ex) {
        String caseId = UUID.randomUUID().toString().replaceAll("-","");
        sendAlertEmail(ex, caseId);
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Async
    void sendAlertEmail(Exception ex, String caseId){
        notificationService.sendNotificaitoin(emailProperties.from,
                emailProperties.alertTo,
                emailProperties.alertSubject,
                notificationService.createExceptionMail(ex, caseId));
    }
}
