package com.app.kidsdrawing.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "student")
public class Student{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "phone")
    private String phone;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Parent parent;

    @OneToMany(mappedBy="student")
    private Set<UserRegisterJoinContest> userRegisterJoinContests;


    @OneToMany(mappedBy="student")
    private Set<StudentLeave> studentLeaves_1;

    @OneToMany(mappedBy="student")
    private Set<ExerciseSubmission> exerciseSubmissions;

    @OneToMany(mappedBy = "student")
    private Set<UserRegisterJoinSemester> userRegisterJoinSemesters;

    @OneToMany(mappedBy="student")
    private Set<UserAttendance> userAttendances;
}