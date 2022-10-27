package com.app.kidsdrawing.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
@Table(name = "password_reset_token")
public class PasswordResetToken {

    @Id
    @GenericGenerator(name = "id", strategy = "com.app.kidsdrawing.entity.generator.PasswordResetTokenIdGenerator")
    @GeneratedValue(generator = "id")
    @Column
    private Long  id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
