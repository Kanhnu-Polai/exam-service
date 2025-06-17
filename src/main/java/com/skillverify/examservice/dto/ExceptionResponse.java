package com.skillverify.examservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ExceptionResponse {
	
	private String message;
	private String code;
	private String service;

}
