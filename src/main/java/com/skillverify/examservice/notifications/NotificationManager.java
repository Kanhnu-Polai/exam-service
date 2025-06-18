package com.skillverify.examservice.notifications;

import org.springframework.stereotype.Component;

import com.skillverify.examservice.dto.NotificationRequest;
import com.skillverify.examservice.entity.Exam;
import com.skillverify.examservice.http.HttpNotificationServiceEngine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationManager {
	
	
	private final HttpNotificationServiceEngine httpNotificationServiceEngine;
	
	
	
	public void sendScheduleExamNotification(Exam exam) {
		log.info("NotificationManager || sendScheduleExamNotification() called for exam: {}", exam);
		String message = String.format("Dear %s,\n\nYour exam for Job ID %s is scheduled on %s.\n\nThank you,\nSkillVerify Team",
	            exam.getEmail(),
	            exam.getJobId(),
	            exam.getScheduleTime());
		
		
		NotificationRequest request = NotificationRequest.builder()
				.to_email(exam.getEmail())
				.subject("Exam Scheduled")
				.message(message).build();
		
		
		
	    httpNotificationServiceEngine.callToNotificationService(request);
		
		
		
		
		
	}

}
