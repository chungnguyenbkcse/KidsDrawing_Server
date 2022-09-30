package com.app.kidsdrawing.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateLessonTimeRequest;
import com.app.kidsdrawing.dto.GetLessonTimeResponse;

public interface LessonTimeService {
    ResponseEntity<Map<String, Object>> getAllLessonTime();
    GetLessonTimeResponse getLessonTimeById(UUID id);
    UUID createLessonTime(CreateLessonTimeRequest createLessonTimeRequest);
    UUID removeLessonTimeById(UUID id);
    UUID updateLessonTimeById(UUID id, CreateLessonTimeRequest createLessonTimeRequest);
}
