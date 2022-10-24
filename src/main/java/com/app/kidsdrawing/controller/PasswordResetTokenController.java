package com.app.kidsdrawing.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import com.app.kidsdrawing.dto.CreateResetPasswordRequest;
import com.app.kidsdrawing.dto.GetPasswordResetTokenResponse;
import com.app.kidsdrawing.service.PasswordResetTokentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/password-reset-token")
public class PasswordResetTokenController {
    private final PasswordResetTokentService passwordResetTokentService;

    @CrossOrigin
    @PostMapping("/user/resetPassword/{email}")
    public ResponseEntity<GetPasswordResetTokenResponse> createResetPassword(@PathVariable String email) {
        return ResponseEntity.ok().body(passwordResetTokentService.resetPassword(email));
    }

    @CrossOrigin
    @GetMapping("/user/changePassword/{token}")
    public RedirectView  showChangePasswordPage(@PathVariable String token) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(passwordResetTokentService.showChangePasswordPage(token));
        return redirectView;
    }

    @CrossOrigin
    @PostMapping("/user/savePassword")
    public ResponseEntity<String> savePassword(@RequestBody CreateResetPasswordRequest createResetPasswordRequest) {
        UUID artTypeId = passwordResetTokentService.savePassword(createResetPasswordRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("")
                .buildAndExpand(artTypeId).toUri();
        return ResponseEntity.created(location).build();
    }
}
