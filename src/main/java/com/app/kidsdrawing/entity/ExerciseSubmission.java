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
@Table(name = "exercise_submission")
public class ExerciseSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "exercise_id", referencedColumnName = "id")
    private Exercise exercise;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "deleted")
    private Boolean deleted;

    @Builder.Default()
    @Column(name = "create_time")
    @CreationTimestamp
    private LocalDateTime create_time = LocalDateTime.now();

    @Builder.Default()
    @Column(name = "update_time")
    @UpdateTimestamp
    private LocalDateTime update_time = LocalDateTime.now();

    @Column(name = "feedback")
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String feedback;

    @Column(name = "score")
    private Float score;

    @Builder.Default()
    @Column(name = "time")
    @CreationTimestamp
    private LocalDateTime time = LocalDateTime.now();

}
