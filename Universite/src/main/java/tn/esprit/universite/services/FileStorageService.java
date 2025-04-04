package tn.esprit.universite.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
// Add Cloudinary
@Service
public class FileStorageService {
    private final Cloudinary cloudinary;

    @Autowired
    public FileStorageService(Environment env) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", env.getProperty("cloudinary.cloud_name"),
                "api_key", env.getProperty("cloudinary.api_key"),
                "api_secret", env.getProperty("cloudinary.api_secret")));
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return "jpg"; // Default extension
        }

        try {
            String[] fileNameParts = fileName.split("\\.");
            return fileNameParts[fileNameParts.length - 1];
        } catch (Exception e) {
            return "jpg"; // Default if any exception occurs
        }
    }

    public String storeFile(MultipartFile file) {
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
            System.err.println("File is null, empty, or has no original filename");
            return "default-image.jpg";
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension;
        String filenameWithoutExtension;

        try {
            fileExtension = getFileExtension(originalFilename);
            filenameWithoutExtension = originalFilename.split("\\.")[0];
        } catch (Exception e) {
            filenameWithoutExtension = "file";
            fileExtension = "jpg";
        }

        String fileName = filenameWithoutExtension + new Date().getTime() + "-file." + fileExtension;

        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("public_id", fileName));
            return (String) uploadResult.get("secure_url");
        } catch (IOException ex) {
            System.err.println("Could not store file " + fileName + ": " + ex.getMessage());
            return "default-image.jpg";
        } catch (Exception ex) {
            System.err.println("Unexpected error storing file: " + ex.getMessage());
            return "default-image.jpg";
        }
    }

    public ByteArrayResource loadFileAsResource(String fileName) {
        // Cloudinary handles files in the cloud, so this method is not applicable.
        System.err.println("Loading files directly from Cloudinary is not supported.");
        return null;
    }
}