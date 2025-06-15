package com.skillverify.examservice.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.skillverify.examservice.dto.ScheduleExamRequest;
import com.skillverify.examservice.entity.Exam;
import com.skillverify.examservice.enums.ExamStatus;
import com.skillverify.examservice.repository.ExamRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExamService {
	
	
	
	
	private final ExamRepository respository;
	
	public Exam scheduleExam(ScheduleExamRequest request,String emailFromToken) {
		log.info("ExamService || scheduleExam() called for jobId: {} by {}", request.getJobId(), emailFromToken);
		
		Exam exam = Exam.builder()
				.jobId(request.getJobId())
				.email(emailFromToken)
				.status(ExamStatus.SCHEDULED)
				.examRequired(request.isExamRequired())
				.round2InterviewRequired(request.isRound2InterviewRequired())
				.resumeTransfered(false)
				.createdAt(LocalDateTime.now())
				.build();
		
		Exam savedExam = respository.save(exam);
		 log.info("ExamService || Exam scheduled successfully for jobId: {}", savedExam.getJobId());
		 
		 return savedExam;

	}

}
