package com.app.kidsdrawing.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReadNotificationKey implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "notification_id")
    Long notificationId;

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}