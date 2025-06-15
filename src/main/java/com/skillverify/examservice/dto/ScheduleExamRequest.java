package com.skillverify.examservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScheduleExamRequest {
	@NotNull(message = "Job ID is required")
	private UUID jobId;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	private String email;
	
	@NotNull(message = "Schedule time is required")
	private LocalDateTime scheduleTime;
	
	
	private boolean examRequired = true;
	private boolean round2InterviewRequired = false;

}
