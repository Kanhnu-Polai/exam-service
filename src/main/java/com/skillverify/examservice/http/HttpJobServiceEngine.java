package com.skillverify.examservice.http;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.skillverify.examservice.enums.ErrorCodeEnum;
import com.skillverify.examservice.exceptions.FailedToCallJobServiceException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
@RequiredArgsConstructor

public class HttpJobServiceEngine {
	
	
	
	
	private ResponseEntity<?> makeValidationCallToJobService(UUID jobId) {
	  // TODO:Replace with real job-service end-point later
		throw new RuntimeException("Simulated job-service failure");
	}
	
	
	
	
	public boolean isJobExists(UUID jobId) {
		try {
			
			ResponseEntity<?> response = makeValidationCallToJobService(jobId);
			return response != null && response.getStatusCode().is2xxSuccessful();
		} catch (Exception e) {
			 log.error("HttpJobServiceEngine || Error calling job-service: {}", e.getMessage());
			 throw new FailedToCallJobServiceException(ErrorCodeEnum.FAILED_TO_CALL_JOB_SERVICE);
			
		}
	}

}
