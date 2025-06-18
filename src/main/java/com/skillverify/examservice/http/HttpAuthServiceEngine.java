package com.skillverify.examservice.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.skillverify.examservice.http.ValidateResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class HttpAuthServiceEngine {

    private final RestClient restClient;

    @Value("${auth.service.base-url}")
    private String authServiceBaseUrl;

    public ValidateResponse makeAuthServiceValidationCall(String token) {
        log.info("HttpAuthServiceEngine || makeAuthServiceValidationCall() called with token: {} by {}", token, System.getProperty("user.name"));

        String url = authServiceBaseUrl + "/api/auth/validate";

        ValidateResponse response = restClient.get()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(ValidateResponse.class);

        log.info("HttpAuthServiceEngine || Response from auth-service: {}", response);

        return response;
    }
}