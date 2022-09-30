package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateSemesterClassRequest;
import com.app.kidsdrawing.dto.GetSemesterClassResponse;

public interface SemesterClassService {
    ResponseEntity<Map<String, Object>> getAllSemesterClass();
    ResponseEntity<Map<String, Object>> getAllSemesterClassBySemester(UUID id);
    ResponseEntity<Map<String, Object>> getAllSemesterClassBySemesterScheduleClass(UUID id);
    ResponseEntity<Map<String, Object>> getAllSemesterClassByCourse(UUID id);
    ResponseEntity<Map<String, Object>> getAllSemesterClassHistoryOfStudent(UUID id);
    ResponseEntity<Map<String, Object>> getAllSemesterClassPresentOfStudent(UUID id);
    ResponseEntity<Map<String, Object>> getAllSemesterClassNew();
    ResponseEntity<Map<String, Object>> getAllSemesterClassNewByStudentAndCourse(UUID id, UUID course_id);
    GetSemesterClassResponse getSemesterClassById(UUID id);
    GetSemesterClassResponse  createSemesterClass(CreateSemesterClassRequest createSemesterClassRequest);
    UUID removeSemesterClassById(UUID id);
    UUID updateSemesterClassById(UUID id, CreateSemesterClassRequest createSemesterClassRequest);
    String updateSemesterClassMaxParticipantById(UUID id);
}