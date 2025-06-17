package com.skillverify.examservice.controller;

import com.skillverify.examservice.dto.ScheduleExamRequest;
import com.skillverify.examservice.entity.Exam;
import com.skillverify.examservice.service.ExamService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
@Slf4j
public class ExamController {

    private final ExamService examService;

    /**
     * POST /api/exams/schedule
     * Requires a valid JWT token (validated via JwtAuthFilter)
     */
    @PostMapping("/schedule")
    public ResponseEntity<?> scheduleExam(
            @Valid @RequestBody ScheduleExamRequest request) {

        // 🔐 Authenticated user's email from token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        log.info("ExamController || Authenticated user: {} is scheduling an exam", email);

        Exam scheduledExam = examService.scheduleExam(request, email);

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduledExam);
    }
}