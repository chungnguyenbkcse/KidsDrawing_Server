package com.app.kidsdrawing.entity;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.GenerationType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
@Table(name = "classes")
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long  id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User user;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserRegisterTeachSemester userRegisterTeachSemester;

    @Column(name = "security_code", nullable = false, unique = true)
    private String security_code;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "link_meeting")
    private String link_meeting;

    @Builder.Default()
    @Column(name = "create_time")
    @CreationTimestamp
    private LocalDateTime create_time = LocalDateTime.now();

    @Builder.Default()
    @Column(name = "update_time")
    @UpdateTimestamp
    private LocalDateTime update_time = LocalDateTime.now();

    @OneToMany(mappedBy="classes")
    private Set<Section> sections;

    @OneToMany(mappedBy="classes")
    private Set<TeacherLeave> teacherLeaves;

    @OneToMany(mappedBy="classes")
    private Set<StudentLeave> studentLeaves;

    @OneToMany(mappedBy="classes")
    private Set<ClassHasRegisterJoinSemesterClass> classHasRegisterJoinSemesterClasses;
}
