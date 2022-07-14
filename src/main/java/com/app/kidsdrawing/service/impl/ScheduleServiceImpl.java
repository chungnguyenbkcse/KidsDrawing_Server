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

import com.app.kidsdrawing.dto.CreateScheduleRequest;
import com.app.kidsdrawing.dto.GetScheduleResponse;
import com.app.kidsdrawing.entity.Schedule;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.ScheduleRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllSchedule(int page, int size) {
        List<GetScheduleResponse> allScheduleResponses = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Schedule> pageSchedule = scheduleRepository.findAll(paging);
        pageSchedule.getContent().forEach(schedule -> {
            GetScheduleResponse scheduleResponse = GetScheduleResponse.builder()
                    .id(schedule.getId())
                    .name(schedule.getName())
                    .build();
            allScheduleResponses.add(scheduleResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("schedules", allScheduleResponses);
        response.put("currentPage", pageSchedule.getNumber());
        response.put("totalItems", pageSchedule.getTotalElements());
        response.put("totalPages", pageSchedule.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetScheduleResponse getScheduleById(Long id){
        Optional<Schedule> scheduleOpt = scheduleRepository.findById(id);
        Schedule schedule = scheduleOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Schedule.not_found");
        });

        return GetScheduleResponse.builder()
                .id(schedule.getId())
                .name(schedule.getName())
                .build();
    }

    @Override
    public Long createSchedule(CreateScheduleRequest createScheduleRequest) {
        Optional<User> userOpt = userRepository.findById(createScheduleRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        Schedule savedSchedule = Schedule.builder()
                .name(createScheduleRequest.getName())
                .user(user)
                .build();
        scheduleRepository.save(savedSchedule);

        return savedSchedule.getId();
    }

    @Override
    public Long removeScheduleById(Long id) {
        Optional<Schedule> scheduleOpt = scheduleRepository.findById(id);
        scheduleOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Schedule.not_found");
        });

        scheduleRepository.deleteById(id);
        return id;
    }

    @Override
    public Long updateScheduleById(Long id, CreateScheduleRequest createScheduleRequest) {
        Optional<Schedule> scheduleOpt = scheduleRepository.findById(id);
        Schedule updatedSchedule = scheduleOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Schedule.not_found");
        });

        Optional<User> userOpt = userRepository.findById(createScheduleRequest.getCreator_id());
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });

        updatedSchedule.setName(createScheduleRequest.getName());
        updatedSchedule.setUser(user);
        scheduleRepository.save(updatedSchedule);

        return updatedSchedule.getId();
    }
}
