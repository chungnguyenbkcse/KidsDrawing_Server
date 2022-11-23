package com.app.kidsdrawing.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.kidsdrawing.dto.CreateResetPasswordRequest;
import com.app.kidsdrawing.dto.GetPasswordResetTokenResponse;
import com.app.kidsdrawing.entity.PasswordResetToken;
import com.app.kidsdrawing.entity.User;
import com.app.kidsdrawing.exception.EntityNotFoundException;
import com.app.kidsdrawing.repository.PasswordTokenRepository;
import com.app.kidsdrawing.repository.UserRepository;
import com.app.kidsdrawing.service.PasswordResetTokentService;
import com.app.kidsdrawing.service.UserService;
import com.google.api.client.util.Value;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class PasswordResetTokentServiceImpl implements PasswordResetTokentService{

    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordTokenRepository passwordTokenRepository;
    private final UserService userService;
    @Value("${spring.mail.username}") private String sender;
    
    @Override
    public GetPasswordResetTokenResponse resetPassword(String email) {
        Optional<User> userOpt = userRepository.findByEmail1(email);
        User user = userOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.user.not_found");
        });
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);
        javaMailSender.send(constructResetTokenEmail(token, user));
        return GetPasswordResetTokenResponse.builder()
            .token(token)
            .build();
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setUser(user);
        myToken.setToken(token);
        myToken.setExpiryDate(new Date(System.currentTimeMillis() + 360000));
        passwordTokenRepository.save(myToken);
    }

    @Override
    public Long savePassword(CreateResetPasswordRequest createResetPasswordRequest) {
        String result = validatePasswordResetToken(createResetPasswordRequest.getToken());

        if(result != null) {
            throw new EntityNotFoundException(result);
        }

        Optional<PasswordResetToken> passwordResetTokenOpt = passwordTokenRepository.findByToken2(createResetPasswordRequest.getToken());
        PasswordResetToken passwordResetToken = passwordResetTokenOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.PasswordResetToken.not_found");
        });
        userService.changeUserPassword(passwordResetToken.getUser(), createResetPasswordRequest.getNewPassword());
        return passwordResetToken.getUser().getId();
    }

    @Override
    public SimpleMailMessage constructResetTokenEmail(String token, User user) {
        String url = "https://server.kidsdrawing.site/api/v1/password-reset-token/user/changePassword/" + token;
        String message = "Hello \n Bạn có yêu cầu reset password\n. Click vào đường link bên dưới: \n";
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    @Override
    public SimpleMailMessage constructEmail(String subject, String body, User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(sender);
        return email;
    }

    @Override
    public String validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> passwordTokenOpt = passwordTokenRepository.findByToken1(token);
        PasswordResetToken passToken  = passwordTokenOpt.orElseThrow(() -> {
            throw new EntityNotFoundException("exception.PasswordResetToken.not_found");
        });

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    @Override
    public String showChangePasswordPage(@RequestParam("token") String token) {
        String result = validatePasswordResetToken(token);
        if(result != null) {
            return "http://localhost:3000/auth";
        } else {
            return "http://localhost:3000/update-password";
        }
    } 

    @Override
    public boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    @Override
    public boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
