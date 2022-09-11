package com.app.kidsdrawing.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.kidsdrawing.dto.CreateResetPasswordRequest;
import com.app.kidsdrawing.dto.GetPasswordResetTokenResponse;
import com.app.kidsdrawing.entity.PasswordResetToken;
import com.app.kidsdrawing.entity.User;

public interface PasswordResetTokentService {
    GetPasswordResetTokenResponse resetPassword(String email);
    void createPasswordResetTokenForUser(User user, String token);
    SimpleMailMessage constructResetTokenEmail(String token, User user);
    SimpleMailMessage constructEmail(String subject, String body, User user);
    String validatePasswordResetToken(String token);
    boolean isTokenFound(PasswordResetToken passToken);
    boolean isTokenExpired(PasswordResetToken passToken);
    Long savePassword(CreateResetPasswordRequest createResetPasswordRequest);
    String showChangePasswordPage(@RequestParam("token") String token);
}
