package com.app.kidsdrawing.entity;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "section")
public class Section {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID  id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "classes_id", referencedColumnName = "id")
    private Classes classes;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number")
    private Integer number;

    @Column(name = "teaching_form")
    private Boolean teaching_form;

    @Column(name = "recording")
    private String recording;

    @Column(name = "message")
    private String message;

    @Builder.Default()
    @Column(name = "create_time")
    @CreationTimestamp
    private LocalDateTime create_time = LocalDateTime.now();

    @Builder.Default()
    @Column(name = "update_time")
    @UpdateTimestamp
    private LocalDateTime update_time = LocalDateTime.now();

    @OneToMany(mappedBy="section")
    private Set<TeacherLeave> teacherLeaves;

    @OneToMany(mappedBy="section")
    private Set<StudentLeave> studentLeaves;

    @OneToMany(mappedBy="section")
    private Set<Exercise> exercises;

    @OneToMany(mappedBy="section")
    private Set<UserRegisterTutorial> userRegisterTutorials;

    @OneToMany(mappedBy="section")
    private Set<UserAttendance> userAttendances;
}
