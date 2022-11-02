package com.app.kidsdrawing.service.impl;

import java.io.File;
import java.io.FileOutputStream;

import javax.transaction.Transactional;

import com.app.kidsdrawing.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinaryConfig;

    @Override
    public String uploadFile(MultipartFile gif) {
        try {
            File uploadedFile = convertMultiPartToFile(gif);
            System.out.println(uploadedFile.getPath());
            System.out.println(uploadedFile.getAbsolutePath());
            //Map uploadResult = cloudinaryConfig.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
/*             boolean isDeleted = uploadedFile.delete();

            if (isDeleted) {
                System.out.println("File successfully deleted");
            } else
                System.out.println("File doesn't exist"); */
            String res = cloudinaryConfig.uploader().upload(uploadedFile, ObjectUtils.emptyMap()).get("url").toString();
            /* boolean isDeleted = uploadedFile.delete();

            if (isDeleted) {
                System.out.println("File successfully deleted");
            } else
                System.out.println("File doesn't exist"); */ 
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File convertMultiPartToFile(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        try {
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (Exception e) {
            System.out.println("File doesn't exist");
        }
        
        return convFile;
    }

}
