package com.app.kidsdrawing.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID  id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Builder.Default()
    @Column(name = "time")
    @CreationTimestamp
    private LocalDateTime time = LocalDateTime.now();

    @OneToOne(mappedBy="notification")
    private UserReadNotification userReadNotification;
}
