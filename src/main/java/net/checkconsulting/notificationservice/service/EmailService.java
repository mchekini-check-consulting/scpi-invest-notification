package net.checkconsulting.notificationservice.service;

import net.checkconsulting.notificationservice.dto.EmailDetailsDto;
import net.checkconsulting.notificationservice.enums.EmailType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private TemplateEngine templateEngine;

    private final SesClient sesClient;

    public EmailService() {
        this.sesClient = SesClient.builder().build();
    }


    public void sendEmail(String to, String subject, Map<String, Object> templateModel, EmailType emailType) {

        Context context = new Context();
        context.setVariables(templateModel);

        String emailTemplate;

        if(emailType == EmailType.REQUEST_VERSMENT) {
            emailTemplate = "email-template";
        } else {
            emailTemplate = "email-relance-template";
        }

        String htmlBody = templateEngine.process(emailTemplate, context);


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


    public Map<String, Object> generateTemplateModel(EmailDetailsDto emailDetailsDto) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("investorName", emailDetailsDto.getInvestorName());
        templateModel.put("investmentAmount", emailDetailsDto.getInvestmentAmount());
        templateModel.put("scpiName", emailDetailsDto.getScpiName());
        templateModel.put("numberOfShares", emailDetailsDto.getNumberOfShares());
        templateModel.put("sharePrice", emailDetailsDto.getSharePrice());
        templateModel.put("propertyType", emailDetailsDto.getPropertyType());
        templateModel.put("investmentDuration", emailDetailsDto.getInvestmentDuration());
        templateModel.put("iban", emailDetailsDto.getIban());
        templateModel.put("bic", emailDetailsDto.getBic());
        templateModel.put("companyName", emailDetailsDto.getCompanyName());
        return templateModel;
    }
}
