package com.skillverify.examservice.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.skillverify.examservice.dto.ScheduleExamRequest;
import com.skillverify.examservice.entity.Exam;
import com.skillverify.examservice.enums.ErrorCodeEnum;
import com.skillverify.examservice.enums.ExamStatus;
import com.skillverify.examservice.exceptions.ExamAlreadyExistException;
import com.skillverify.examservice.exceptions.JobNotFoundException;
import com.skillverify.examservice.http.HttpJobServiceEngine;
import com.skillverify.examservice.repository.ExamRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExamService {
	
	
	
	
	private final ExamRepository respository;
	
	private final HttpJobServiceEngine jobServiceEngine;
	
	
	
	public Exam scheduleExam(ScheduleExamRequest request,String emailFromToken) {
		log.info("ExamService || scheduleExam() called for jobId: {} by {}", request.getJobId(), emailFromToken);
		
		// Validate jobId with job-service 
		
		
				if(jobServiceEngine.isJobExists(request.getJobId()) != true) {
					
					throw new JobNotFoundException(ErrorCodeEnum.JOB_NOT_FOUND);
				}
				
		
		 //Check for specific email the job id scheduled or not
		if(respository.existsByJobIdAndEmail(request.getJobId(), emailFromToken))
		{
			throw new ExamAlreadyExistException(ErrorCodeEnum.EXAM_ALREADY_SCHEDULED);
		}
		
		
		
		
		
		
		 LocalDateTime finalScheduleTime = request.getScheduleTime() != null ? request.getScheduleTime() : LocalDateTime.now().plusDays(2);
		
		Exam exam = Exam.builder()
				.jobId(request.getJobId())
				.email(emailFromToken)
				.status(ExamStatus.SCHEDULED)
				.examRequired(request.isExamRequired())
				.scheduleTime(finalScheduleTime)
				.round2InterviewRequired(request.isRound2InterviewRequired())
				.resumeTransfered(false)
				.createdAt(LocalDateTime.now())
				.build();
		
		Exam savedExam = respository.save(exam);
		 log.info("ExamService || Exam scheduled successfully for jobId: {}", savedExam.getJobId());
		 
		 return savedExam;

	}

}
