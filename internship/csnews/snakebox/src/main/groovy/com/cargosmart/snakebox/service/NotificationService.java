package com.cargosmart.snakebox.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Service
public class NotificationService {

//    private JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    JavaMailSender javaMailSender;

//    public NotificationService(JavaMailSender javaMailSender){
//        this.javaMailSender = javaMailSender;
//    }

    @Async
    public void sendNotificaitoin(String from, String[] to, String subject, String html){
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper  message = new MimeMessageHelper(mail, false, "UTF-8"); // true = multipart
            message.setTo(to);
            message.setFrom(from);
            message.setSubject(subject);
            message.setText(html, true);
            javaMailSender.send(mail);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String createExceptionMail(Exception ex, String caseId) {
        final Context ctx = new Context();
        ctx.setVariable("message",ex.getMessage());
        ctx.setVariable("caseId",caseId);
        ctx.setVariable("stackTrace", ExceptionUtils.getStackTrace(ex));
        return templateEngine.process("email/exception", ctx);
    }

}