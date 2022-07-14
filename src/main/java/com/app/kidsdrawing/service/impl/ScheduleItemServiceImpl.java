package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateScheduleItemRequest;
import com.app.kidsdrawing.dto.GetScheduleItemResponse;
import com.app.kidsdrawing.entity.LessonTime;
import com.app.kidsdrawing.entity.Schedule;
import com.app.kidsdrawing.entity.ScheduleItem;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.LessonTimeRepository;
import com.app.kidsdrawing.repository.ScheduleItemRepository;
import com.app.kidsdrawing.repository.ScheduleRepository;
import com.app.kidsdrawing.service.ScheduleItemService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ScheduleItemServiceImpl implements ScheduleItemService {

    private final ScheduleItemRepository scheduleItemRepository;
    private final ScheduleRepository scheduleRepository;
    private final LessonTimeRepository lessonTimeRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllScheduleItem(int page, int size) {
        List<GetScheduleItemResponse> allScheduleItemResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<ScheduleItem> pageScheduleItem = scheduleItemRepository.findAll(paging);
        pageScheduleItem.getContent().forEach(schedule_item -> {
            GetScheduleItemResponse scheduleItemResponse = GetScheduleItemResponse.builder()
                    .id(schedule_item.getId())
                    .schedule_id(schedule_item.getSchedule().getId())
                    .lesson_time(schedule_item.getLessonTime().getId())
                    .date_of_week(schedule_item.getDate_of_week())
                    .build();
            allScheduleItemResponses.add(scheduleItemResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("lesson_times", allScheduleItemResponses);
        response.put("currentPage", pageScheduleItem.getNumber());
        response.put("totalItems", pageScheduleItem.getTotalElements());
        response.put("totalPages", pageScheduleItem.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetScheduleItemResponse getScheduleItemById(Long id){
        Optional<ScheduleItem> scheduleItemOpt = scheduleItemRepository.findById(id);
        ScheduleItem scheduleItem = scheduleItemOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ScheduleItem.not_found");
        });

        return GetScheduleItemResponse.builder()
                .id(scheduleItem.getId())
                .schedule_id(scheduleItem.getLessonTime().getId())
                .lesson_time(scheduleItem.getLessonTime().getId())
                .date_of_week(scheduleItem.getDate_of_week())
                .build();
    }

    @Override
    public Long createScheduleItem(CreateScheduleItemRequest createScheduleItemRequest) {

        Optional<Schedule> scheduleOpt = scheduleRepository.findById(createScheduleItemRequest.getSchedule_id());
        Schedule schedule = scheduleOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Schedule.not_found");
        });

        Optional<LessonTime> lessonTimeOpt = lessonTimeRepository.findById(createScheduleItemRequest.getLesson_time());
        LessonTime lessonTime = lessonTimeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.LessonTime.not_found");
        });
        
        ScheduleItem savedScheduleItem = ScheduleItem.builder()
                .lessonTime(lessonTime)
                .schedule(schedule)
                .date_of_week(createScheduleItemRequest.getDate_of_week())
                .build();
        scheduleItemRepository.save(savedScheduleItem);

        return savedScheduleItem.getId();
    }

    @Override
    public Long removeScheduleItemById(Long id) {
        Optional<ScheduleItem> scheduleItemOpt = scheduleItemRepository.findById(id);
        scheduleItemOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ScheduleItem.not_found");
        });

        scheduleItemRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateScheduleItemById(Long id, CreateScheduleItemRequest createScheduleItemRequest) {
        Optional<ScheduleItem> scheduleItemOpt = scheduleItemRepository.findById(id);
        ScheduleItem updatedScheduleItem = scheduleItemOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.ScheduleItem.not_found");
        });

        Optional<Schedule> scheduleOpt = scheduleRepository.findById(createScheduleItemRequest.getSchedule_id());
        Schedule schedule = scheduleOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Schedule.not_found");
        });

        Optional<LessonTime> lessonTimeOpt = lessonTimeRepository.findById(createScheduleItemRequest.getLesson_time());
        LessonTime lessonTime = lessonTimeOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.LessonTime.not_found");
        });

        updatedScheduleItem.setLessonTime(lessonTime);
        updatedScheduleItem.setSchedule(schedule);
        updatedScheduleItem.setDate_of_week(createScheduleItemRequest.getDate_of_week());
        scheduleItemRepository.save(updatedScheduleItem);

        return updatedScheduleItem.getId();
    }
}
