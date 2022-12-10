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
public class TeacherRegisterQuantificationKey implements Serializable {
    @Column(name = "teacher_id")
    Long teacherId;

    @Column(name = "course_id")
    Long courseId;
}
