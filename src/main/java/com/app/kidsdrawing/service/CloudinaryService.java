package com.app.kidsdrawing.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadFile(MultipartFile gif);
    File convertMultiPartToFile(MultipartFile file);
}
