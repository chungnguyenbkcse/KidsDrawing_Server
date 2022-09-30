package com.app.kidsdrawing.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
@Table(name = "student_leave")
public class StudentLeave {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID  id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "classes_id", referencedColumnName = "id")
    private Classes classes;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "section_id", referencedColumnName = "id")
    private Section section;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "Student_id", referencedColumnName = "id")
    private User student;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "reviewer_id", referencedColumnName = "id")
    private User reviewer;

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
