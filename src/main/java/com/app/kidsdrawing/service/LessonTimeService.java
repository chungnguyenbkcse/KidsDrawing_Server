package com.app.kidsdrawing.service;

import java.util.Map;


import org.springframework.http.ResponseEntity;

import com.app.kidsdrawing.dto.CreateLessonTimeRequest;
import com.app.kidsdrawing.dto.GetLessonTimeResponse;

public interface LessonTimeService {
    ResponseEntity<Map<String, Object>> getAllLessonTime();
    GetLessonTimeResponse getLessonTimeById(Long id);
    Long createLessonTime(CreateLessonTimeRequest createLessonTimeRequest);
    Long removeLessonTimeById(Long id);
    Long updateLessonTimeById(Long id, CreateLessonTimeRequest createLessonTimeRequest);
}
