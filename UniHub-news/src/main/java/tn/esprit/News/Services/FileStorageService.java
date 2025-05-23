package tn.esprit.News.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class FileStorageService {
    private Path fileStorageLocation;
    private boolean isInitialized = false;

    @Autowired
    public FileStorageService(Environment env) {
        try {
            this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", "./upload-directory"))
                    .toAbsolutePath().normalize();

            Files.createDirectories(this.fileStorageLocation);
            isInitialized = true;
        } catch (Exception ex) {
            System.err.println("Could not create the directory where the uploaded files will be stored: " + ex.getMessage());
            // Instead of throwing exception, we'll set a flag and handle it gracefully
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String[] fileNameParts = fileName.split("\\.");

        return fileNameParts[fileNameParts.length - 1];
    }

    public String storeFile(MultipartFile file) {
        if (!isInitialized) {
            throw new RuntimeException("File storage service is not properly initialized");
        }
        
        // Normalize file name
        String fileName =
                file.getOriginalFilename().split("\\.")[0]  +new Date().getTime() + "-file." + getFileExtension(file.getOriginalFilename());

        try {
            // Check if the filename contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException(
                        "Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
