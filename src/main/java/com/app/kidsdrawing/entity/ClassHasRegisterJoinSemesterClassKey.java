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
public class ClassHasRegisterJoinSemesterClassKey implements Serializable {

    @Column(name = "classes_id")
    Long classesId;

    @Column(name = "user_register_join_semester_id")
    Long userRegisterJoinSemesterId;

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}