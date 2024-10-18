package net.checkconsulting.notificationservice.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

@RestController
@RequestMapping("/sms")
@Slf4j
public class SmsResource {

    private final SnsClient snsClient = SnsClient.create();

    @PostMapping("/send")
    public String sendSms(@RequestParam String phoneNumber, @RequestParam String message) {
        try {
            log.info("trying to send sms to {}", phoneNumber);
            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .phoneNumber(phoneNumber)
                    .build();

            snsClient.publish(request);
            log.info("sms sent to {}", phoneNumber);
            return "SMS sent successfully!";
        } catch (SnsException e) {
            log.error("failed sending sms to {}", phoneNumber);
            return "Failed to send SMS: " + e.getMessage();
        }
    }
}

