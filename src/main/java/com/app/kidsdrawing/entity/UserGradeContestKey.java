package com.app.kidsdrawing.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGradeContestKey implements Serializable {

    @Column(name = "teacher_id")
    Long teacherId;

    @Column(name = "contest_id")
    Long contestId;

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}