package com.app.kidsdrawing.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.app.kidsdrawing.service.CloudinaryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/cloudinary")
public class CloudinaryController {
    private final CloudinaryService cloudinaryGifService;

    @PostMapping("/gifs")
    @CrossOrigin
    public ResponseEntity<Map<String, Object>> uploadGif(@RequestParam("gifFile") MultipartFile gifFile)
            throws IOException {
        String url = cloudinaryGifService.uploadFile(gifFile);
        Map<String, Object> response = new HashMap<>();
        response.put("url_image", url);
        return ResponseEntity.ok().body(response);
    }

}
