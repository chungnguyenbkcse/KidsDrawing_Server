package com.app.kidsdrawing.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "teacher_register_quatification")
public class TeacherRegisterQualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(name = "degree_photo_url", nullable = false)
    private String degree_photo_url;

    @Column(name = "status")
    private String status;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;
}
