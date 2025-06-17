package com.skillverify.examservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.skillverify.examservice.dto.ExceptionResponse;

@RestControllerAdvice
public class GobalExceptionHandler {
	
	
	
	@ExceptionHandler(ExamAlreadyExistException.class)
	public ResponseEntity<ExceptionResponse> handleExamAlreadyExistException(ExamAlreadyExistException e) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setMessage(e.getErrorCodeEnum().getMessage());
		exceptionResponse.setCode(e.getErrorCodeEnum().getCode());
		exceptionResponse.setService(e.getErrorCodeEnum().getService());
		
		
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
		
		
		
	}
	
	
	@ExceptionHandler(FailedToCallJobServiceException.class)
	public ResponseEntity<ExceptionResponse> handleFailedToCallJobServiceException(FailedToCallJobServiceException e){
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setMessage(e.getErrorCodeEnum().getMessage());
		exceptionResponse.setCode(e.getErrorCodeEnum().getCode());
		exceptionResponse.setService(e.getErrorCodeEnum().getService());
		
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exceptionResponse);
	}
	
	
	
	
	@ExceptionHandler(JobNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleJobNotFoundException(JobNotFoundException e){
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setMessage(e.getErrorCodeEnum().getMessage());
		exceptionResponse.setCode(e.getErrorCodeEnum().getCode());
		exceptionResponse.setService(e.getErrorCodeEnum().getService());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
	}

}
