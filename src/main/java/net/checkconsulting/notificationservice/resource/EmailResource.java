package net.checkconsulting.notificationservice.resource;


import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.notificationservice.dto.EmailDetailsDto;
import net.checkconsulting.notificationservice.service.EmailService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/email")
@Slf4j
public class EmailResource {

    private final EmailService emailService;

    public EmailResource(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestBody EmailDetailsDto emailDetailsDto) {
        try {

            log.info("trying send email to {} with these informations {}", to, emailDetailsDto);

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

            emailService.sendEmail(to, subject, templateModel);

            log.info("email to {} successfully sent", to);

            return "E-mail envoyé avec succès!";
        } catch (Exception e) {
            log.error("an error occured when sending email to {}", to);
            return "Échec de l'envoi de l'e-mail.";
        }
    }
}


