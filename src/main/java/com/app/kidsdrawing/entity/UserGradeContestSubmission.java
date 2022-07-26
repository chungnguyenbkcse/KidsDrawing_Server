package com.app.kidsdrawing.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

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
@Table(name = "user_grade_contest_submission")
public class UserGradeContestSubmission {
    @EmbeddedId
    UserGradeContestSubmissionKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    User student;

    @ManyToOne
    @MapsId("submissionId")
    @JoinColumn(name = "submission_id")
    ContestSubmission contestSubmission;

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
