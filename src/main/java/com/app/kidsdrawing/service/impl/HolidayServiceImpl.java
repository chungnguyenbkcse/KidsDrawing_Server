package com.app.kidsdrawing.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.kidsdrawing.dto.CreateHolidayRequest;
import com.app.kidsdrawing.dto.GetHolidayResponse;
import com.app.kidsdrawing.entity.Holiday;
import com.app.kidsdrawing.entity.Semester;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.HolidayRepository;
import com.app.kidsdrawing.repository.SemesterRepository;
import com.app.kidsdrawing.service.HolidayService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepository HolidayRepository;
    private final SemesterRepository semesterRepository;

    @Override
    public ResponseEntity<Map<String, Object>> getAllHoliday() {
        List<GetHolidayResponse> allHolidayResponses = new ArrayList<>();
        List<Holiday> pageHoliday = HolidayRepository.findAll();
        pageHoliday.forEach(holiday -> {
            GetHolidayResponse HolidayResponse = GetHolidayResponse.builder()
                    .id(holiday.getId())
                    .date(holiday.getDay())
                    .semester_id(holiday.getSemester().getId())
                    .build();
            allHolidayResponses.add(HolidayResponse);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("holidays", allHolidayResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public GetHolidayResponse getHolidayById(UUID id){
        Optional<Holiday> HolidayOpt = HolidayRepository.findById(id);
        Holiday Holiday = HolidayOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Holiday.not_found");
        });

        return GetHolidayResponse.builder()
                .id(Holiday.getId())
                .date(Holiday.getDay())
                .semester_id(Holiday.getSemester().getId())
                .build();
    }

    @Override
    public UUID createHoliday(CreateHolidayRequest createHolidayRequest) {

        Optional<Semester> semesterOpt = semesterRepository.findById1(createHolidayRequest.getSemester_id());
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        createHolidayRequest.getTime().forEach(holiday -> {
            Holiday saveHoliday = Holiday.builder()
                .day(holiday)
                .semester(semester)
                .build();
            HolidayRepository.save(saveHoliday);
        });

        return UUID.randomUUID();
    }

    @Override
    public UUID removeHolidayById(UUID id) {
        Optional<Holiday> HolidayOpt = HolidayRepository.findById(id);
        HolidayOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Holiday.not_found");
        });

        HolidayRepository.deleteById(id);
        return id;
    }

    @Override
    public UUID updateHolidayById(UUID id, CreateHolidayRequest createHolidayRequest) {
        Optional<Holiday> HolidayOpt = HolidayRepository.findById(id);
        Holiday updatedHoliday = HolidayOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Holiday.not_found");
        });

        Optional<Semester> semesterOpt = semesterRepository.findById1(createHolidayRequest.getSemester_id());
        Semester semester = semesterOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.Semester.not_found");
        });

        createHolidayRequest.getTime().forEach(holiday -> {
            updatedHoliday.setDay(holiday);
            updatedHoliday.setSemester(semester);
            HolidayRepository.save(updatedHoliday);
        });

        return updatedHoliday.getId();
    }
}
