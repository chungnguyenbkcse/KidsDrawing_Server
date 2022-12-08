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
@Table(name = "admin")
public class Admin {
    @Id
    private Long  id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy="admin")
    private Set<Course> courses;

    @OneToMany(mappedBy="admin")
    private Set<Contest> contests;

    @OneToMany(mappedBy="admin")
    private Set<ArtType> artTypes;

    @OneToMany(mappedBy="admin")
    private Set<ArtLevel> artLevels;

    @OneToMany(mappedBy="admin")
    private Set<ArtAge> artAges;

    @OneToMany(mappedBy="admin")
    private Set<Classes> classes;

    @OneToMany(mappedBy="admin")
    private Set<SemesterClass> semesterClasses;
    
    @OneToMany(mappedBy="admin")
    private Set<Semester> semester;

    @Column(name = "phone")
    private String phone;
}
