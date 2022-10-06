package com.app.kidsdrawing.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
@Table(name = "user")
public class User{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID  id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "sex")
    private String sex;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private String status;

    @Builder.Default()
    @Column(name = "create_time")
    @CreationTimestamp
    private LocalDateTime createTime = LocalDateTime.now();

    @Builder.Default()
    @Column(name = "update_time")
    @UpdateTimestamp
    private LocalDateTime updateTime = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_has_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> roles;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private User parent;
     
    @OneToMany(mappedBy = "parent")
    private Set<User> childrens;

    @OneToMany(mappedBy="user")
    private Set<ArtLevel> artLevels;

    @OneToMany(mappedBy="user")
    private Set<ArtType> artTypes;

    @OneToMany(mappedBy="user")
    private Set<Contest> contests;

    @OneToMany(mappedBy="user")
    private Set<Course> courses;

    @OneToMany(mappedBy="student")
    private Set<UserRegisterJoinContest> userRegisterJoinContests;

    @OneToMany(mappedBy="reviewer")
    private Set<TeacherRegisterQualification> admin_review_register_qutifications;

    @OneToMany(mappedBy="teacher")
    private Set<TeacherRegisterQualification> teacher_register_qutifications;

    @OneToMany(mappedBy="teacher")
    private Set<UserRegisterTeachSemester> teacher_teach_semester;

    @OneToMany(mappedBy="teacher")
    private Set<TeacherLeave> teacherLeaves_1;

    @OneToMany(mappedBy="reviewer")
    private Set<TeacherLeave> teacherLeaves_2;

    @OneToMany(mappedBy="substitute_teacher")
    private Set<TeacherLeave> teacherLeaves_3;

    @OneToMany(mappedBy="student")
    private Set<StudentLeave> studentLeaves_1;

    @OneToMany(mappedBy="reviewer")
    private Set<StudentLeave> studentLeaves_2;

    @OneToMany(mappedBy="creator")
    private Set<Tutorial> tutorials;

    @OneToMany(mappedBy="creator")
    private Set<TutorialTemplate> tutorialTemplates;

    @OneToMany(mappedBy="creator")
    private Set<UserRegisterTutorial> userRegisterTutorials;

    @OneToMany(mappedBy="student")
    private Set<ExerciseSubmission> exerciseSubmissions;

    @OneToMany(mappedBy = "teacher")
    private Set<UserGradeExerciseSubmission> userGradeExerciseSubmissions;

    @OneToMany(mappedBy = "payer")
    private Set<UserRegisterJoinSemester> userRegisterJoinSemesters;

    @OneToMany(mappedBy = "student")
    private Set<UserRegisterJoinSemester> userRegisterJoinSemesters2;

    @OneToMany(mappedBy = "teacher")
    private Set<UserGradeContestSubmission> userGradeContestSubmissions;

    @OneToMany(mappedBy="user")
    private Set<UserReadNotification> userReadNotifications;

    @OneToMany(mappedBy="user")
    private Set<SectionTemplate> sectionTemplates;

    @OneToMany(mappedBy="student")
    private Set<UserAttendance> userAttendances;

}