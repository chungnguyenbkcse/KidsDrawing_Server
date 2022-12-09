package com.app.kidsdrawing.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "teacher")
public class Teacher {
    @Id
    private Long  id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy="teacher")
    private Set<TeacherRegisterQualification> teacher_register_qutifications;

    @OneToMany(mappedBy="teacher")
    private Set<UserRegisterTeachSemester> teacher_teach_semester;

    @OneToMany(mappedBy="teacher")
    private Set<UserGradeContest> userGradeContests;

    @OneToMany(mappedBy="substitute_teacher")
    private Set<TeacherLeave> teacherLeaves;
}
