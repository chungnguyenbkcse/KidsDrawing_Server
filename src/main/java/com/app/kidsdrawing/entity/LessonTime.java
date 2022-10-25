package com.app.kidsdrawing.entity;

import java.time.LocalTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Lesson_Time")
public class LessonTime {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID  id;

    @Column(name = "start_time")
    private LocalTime start_time;

    @Column(name = "end_time")
    private LocalTime end_time;

    @OneToOne(mappedBy="lessonTime")
    private Schedule schedule;

}
