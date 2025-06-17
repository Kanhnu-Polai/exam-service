package com.skillverify.examservice.http;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class HttpAuthServiceEngine {
	
	
	
	private final RestClient restClient;
	
	
	
	
	public ValidateResponse makeAuthServiceValidationCall(String token) {
		
		log.info("HtttpAUthServiceEngine || makeAuthServiceValidationCall() called for email: {} by {}", token, System.getProperty("user.name"));
		
		ValidateResponse response = restClient.get()
				.uri("http://localhost:8080/api/auth/validate")
				.header("Authorization", "Bearer " + token)
				.retrieve()
				.body(ValidateResponse.class);
		
		 log.info("HttpAuthServiceEngine || Response from auth-service: {}", response);
		
		
		 return response;
	}

}
