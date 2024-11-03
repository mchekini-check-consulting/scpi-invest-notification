package net.checkconsulting.notificationservice.resource;


import lombok.extern.slf4j.Slf4j;
import net.checkconsulting.notificationservice.dto.EmailDetailsDto;
import net.checkconsulting.notificationservice.dto.EmailPlannedInvestPartnerNotificationDto;
import net.checkconsulting.notificationservice.dto.EmailPlannedInvestment;
import net.checkconsulting.notificationservice.service.EmailService;
import net.checkconsulting.notificationservice.service.PlannifedInvestEmailService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/email")
@Slf4j
public class EmailResource {

    private final EmailService emailService;
    private final PlannifedInvestEmailService plannifedInvestEmailService;

    public EmailResource(EmailService emailService, PlannifedInvestEmailService plannifedInvestEmailService) {
        this.emailService = emailService;
        this.plannifedInvestEmailService = plannifedInvestEmailService;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestBody EmailDetailsDto emailDetailsDto) {
        try {

            log.info("trying send email to {} with these informations {}", to, emailDetailsDto);

            Map<String, Object> templateModel = emailService.generateTemplateModel(emailDetailsDto);

            emailService.sendEmail(to, subject, templateModel, emailDetailsDto.getEmailType());

            log.info("email to {} successfully sent", to);

            return "E-mail envoyé avec succès!";
        } catch (Exception e) {
            log.error("an error occured when sending email to {}", to);
            return "Échec de l'envoi de l'e-mail.";
        }
    }


    @PostMapping("/planned-invest")
    public String sendPlannedInvestEmail(@RequestParam String to,@RequestParam String subject, @RequestBody EmailPlannedInvestment emailDetailsDto) {
        try {

            log.info("trying send email for her planned investement to {} with these informations {}", to, emailDetailsDto);

            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("investorName", emailDetailsDto.getInvestorName());
            templateModel.put("plannedInvestmentId", emailDetailsDto.getPlannedInvestmentId());


            plannifedInvestEmailService.sendEmail(to, subject,templateModel,"email-planned-invest");

            log.info("planned investment email to {} successfully sent", to);

            return "E-mail envoyé avec succès!";
        } catch (Exception e) {
            log.error("an error occured when sending planned investment email to {}", to);
            return "Échec de l'envoi de l'e-mail.";
        }
    }


    @PostMapping("/reject-planned-invest")
    public String sendRejectPlannedInvestEmail(@RequestParam String to,@RequestParam String subject, @RequestBody EmailPlannedInvestPartnerNotificationDto emailDetailsDto) {
        try {

            log.info("trying send email for reject planned investement to {} with these informations {}", to, emailDetailsDto);

            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("investorName", emailDetailsDto.getInvestorName());
            templateModel.put("motifRefus", emailDetailsDto.getReason());


            plannifedInvestEmailService.sendEmail(to, subject,templateModel,"email-reject-planned-invest");

            log.info("reject planned investment email to {} successfully sent", to);

            return "E-mail envoyé avec succès!";
        } catch (Exception e) {
            log.error("an error occured when sending reject planned investment email to {}", to);
            return "Échec de l'envoi de l'e-mail.";
        }
    }

    @PostMapping("/validated-planned-invest")
    public String sendValidatedPlannedInvestEmail(@RequestParam String to,@RequestParam String subject, @RequestBody EmailPlannedInvestPartnerNotificationDto emailDetailsDto) {
        try {

            log.info("trying send email for validated planned investement to {} with these informations {}", to, emailDetailsDto);

            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("investorName", emailDetailsDto.getInvestorName());
            templateModel.put("montant",emailDetailsDto.getAmount());
            templateModel.put("jourDuMois", emailDetailsDto.getDebitDayOfMonth());
            templateModel.put("frequence", emailDetailsDto.getFrequency());

            plannifedInvestEmailService.sendEmail(to, subject,templateModel,"email-validated-planned-investment");

            log.info("validated planned investment email to {} successfully sent", to);

            return "E-mail envoyé avec succès!";
        } catch (Exception e) {
            log.error("an error occured when sending reject planned investment email to {}", to);
            return "Échec de l'envoi de l'e-mail.";
        }
    }
}


