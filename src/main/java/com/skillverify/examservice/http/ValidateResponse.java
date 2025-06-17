package com.skillverify.examservice.http;

import lombok.Data;

@Data
public class ValidateResponse {
	private String email;
    private String role;
    private String status;

}
