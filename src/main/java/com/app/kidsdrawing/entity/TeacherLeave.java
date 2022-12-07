package com.app.kidsdrawing.entity;

import java.time.LocalDateTime;

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
@Table(name = "teacher_leave")
public class TeacherLeave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    private Section section;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "substitute_teacher_id", referencedColumnName = "id")
    private Teacher substitute_teacher;

    @Column(name = "description")
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @Column(name = "status")
    private String status;

    @Builder.Default()
    @Column(name = "create_time")
    @CreationTimestamp
    private LocalDateTime create_time = LocalDateTime.now();

    @Builder.Default()
    @Column(name = "update_time")
    @UpdateTimestamp
    private LocalDateTime update_time = LocalDateTime.now();
}
