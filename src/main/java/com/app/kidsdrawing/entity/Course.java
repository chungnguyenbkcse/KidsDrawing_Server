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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import org.hibernate.annotations.Type;
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
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @Column(name = "num_of_section")
    private Integer num_of_section;

    @Column(name = "price")
    private Float price;

    @Column(name = "deleted")
    private Boolean deleted;

    @Column(name = "image_url")
    private String image_url;

    @Builder.Default()
    @Column(name = "create_time")
    @CreationTimestamp
    private LocalDateTime create_time = LocalDateTime.now();

    @Builder.Default()
    @Column(name = "update_time")
    @UpdateTimestamp
    private LocalDateTime update_time = LocalDateTime.now();

    @Column(name = "is_enabled")
    private Boolean is_enabled;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "art_level_id", referencedColumnName = "id")
    private ArtLevel artLevels;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "art_type_id", referencedColumnName = "id")
    private ArtType artTypes;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "art_age_id", referencedColumnName = "id")
    private ArtAge artAges;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private Admin admin;

    @OneToMany(mappedBy = "course")
    private Set<SectionTemplate> sectionTemplates;

    @OneToMany(mappedBy = "course")
    private Set<SemesterClass> semesterClasses;

    @OneToMany(mappedBy = "course")
    private Set<TeacherRegisterQualification> teacherRegisterQualifications;
}
