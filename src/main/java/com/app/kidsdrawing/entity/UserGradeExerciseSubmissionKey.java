package com.app.kidsdrawing.entity;

import java.io.Serializable;
import java.util.UUID;

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
public class UserGradeExerciseSubmissionKey implements Serializable {

    @Column(name = "teacher_id")
    UUID teacherId;

    @Column(name = "submission_id")
    UUID submissionId;

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}