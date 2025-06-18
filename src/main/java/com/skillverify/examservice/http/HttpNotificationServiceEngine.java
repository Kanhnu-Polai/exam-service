package com.skillverify.examservice.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.skillverify.examservice.dto.NotificationRequest;
import com.skillverify.examservice.enums.ErrorCodeEnum;
import com.skillverify.examservice.exceptions.FailedToCallNotificationServiceException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class HttpNotificationServiceEngine {

    private final RestClient restClient;

    @Value("${notification.service.base-url}")
    private String notificationServiceBaseUrl;

    public void callToNotificationService(NotificationRequest request) {
        log.info("HttpNotificationServiceEngine || callToNotificationService() called for email: {} by {}",
                request.getTo_email(), System.getProperty("user.name"));

        try {
            String url = notificationServiceBaseUrl + "/send-email";

            ResponseEntity<String> response = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toEntity(String.class);

            log.info("HttpNotificationServiceEngine || Response from notification service: {}", response.getBody());

        } catch (Exception e) {
            log.error("HttpNotificationServiceEngine || Error calling notification service: {}", e.getMessage());
            throw new FailedToCallNotificationServiceException(ErrorCodeEnum.FAILED_TO_CALL_NOTIFICATION_SERVICE);
        }
    }
}