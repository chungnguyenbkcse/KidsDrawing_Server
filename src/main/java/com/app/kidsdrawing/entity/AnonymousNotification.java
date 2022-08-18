package com.app.kidsdrawing.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
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
@Table(name = "anonymous_notification")
public class AnonymousNotification {

    @EmbeddedId
    AnonymousNotificationKey id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId("nontificationId")
    @JoinColumn(name = "notification_id")
    Notification notification;

    @Column(name = "user_full_name")
    private String user_full_name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;
}
