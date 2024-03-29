package com.app.kidsdrawing.entity;

import java.time.LocalDateTime;
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
@Table(name = "semester_class")
public class SemesterClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "semester_id", referencedColumnName = "id")
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @OneToMany(mappedBy="semesterClass")
    private Set<Schedule> schedules;

    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(mappedBy="semesterClass")
    private Set<UserRegisterTeachSemester> userRegisterTeachSemesters;

    @OneToMany(mappedBy="semesterClass")
    private Set<UserRegisterJoinSemester> userRegisterJoinSemesters;

    @Column(name = "max_participant")
    private Integer max_participant;

    @Column(name = "registration_time")
    private LocalDateTime registration_time;

    @Column(name = "registration_expiration_time")
    private LocalDateTime registration_expiration_time;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private Admin admin;
}
