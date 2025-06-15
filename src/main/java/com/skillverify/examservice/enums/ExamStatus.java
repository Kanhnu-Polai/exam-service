package com.skillverify.examservice.enums;

public enum ExamStatus {
	SCHEDULED,     // Exam is scheduled for a future date
    STARTED,       // Exam has started
    SUBMITTED,     // Exam has been submitted by the candidate
    COMPLETED,     // Exam has been reviewed and marked as done
    CANCELLED,     // Exam has been cancelled by the candidate or admin
    FAILED         // Exam has been marked as failed

}
