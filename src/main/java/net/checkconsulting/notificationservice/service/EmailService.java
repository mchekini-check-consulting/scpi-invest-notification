package net.checkconsulting.notificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private TemplateEngine templateEngine;

    private final SesClient sesClient;

    public EmailService() {
        this.sesClient = SesClient.builder().build();
    }


    public void sendEmail(String to, String subject, Map<String, Object> templateModel) {

        Context context = new Context();
        context.setVariables(templateModel);


        String htmlBody = templateEngine.process("email-template", context);


        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(Destination.builder().toAddresses(to).build())
                .message(Message.builder()
                        .subject(Content.builder().data(subject).charset("UTF-8").build())
                        .body(Body.builder()
                                .html(Content.builder().data(htmlBody).charset("UTF-8").build())
                                .build())
                        .build())
                .source("me.chekini@gmail.com")
                .build();

        sesClient.sendEmail(emailRequest);
    }
}
