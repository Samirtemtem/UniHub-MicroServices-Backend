package com.example.event_ms.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileStorageService {

    @Autowired
    private Cloudinary cloudinary;

    public String storeFile(MultipartFile file) {
        try {
            // Upload the image to Cloudinary under the "events" folder
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", "events"));

            // Get the secure URL of the uploaded image
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            // Handle the error properly
            throw new RuntimeException("Failed to upload image to Cloudinary", e);
        }
    }
}

