package com.app.kidsdrawing.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
@Table(name = "class_has_register_join_semester_class")
public class ClassHasRegisterJoinSemesterClass {
    @EmbeddedId
    ClassHasRegisterJoinSemesterClassKey id;

    @ManyToOne
    @MapsId("classesId")
    @JoinColumn(name = "classes_id")
    Classes classes;

    @OneToOne
    @MapsId("userRegisterJoinSemesterId")
    @JoinColumn(name = "user_register_join_semester_id")
    UserRegisterJoinSemester userRegisterJoinSemester;

    @Column(name = "review_star")
    private Integer review_star;

    @Column(name = "student_feedback")
    private String student_feedback;

    @Column(name = "teacher_feedback")
    private String teacher_feedback;

    @Column(name = "final_grade")
    private Float final_grade;

}
