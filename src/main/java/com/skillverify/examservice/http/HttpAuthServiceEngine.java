package com.skillverify.examservice.http;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HttpAuthServiceEngine {
	
	
	
	private final RestClient restClient;
	
	
	
	
	public ResponseEntity<?> makeAuthServiceValidationCall(String emailFromToken) {
		
		
		
		return ResponseEntity.ok("Email validated successfully: " + emailFromToken);
	}

}
