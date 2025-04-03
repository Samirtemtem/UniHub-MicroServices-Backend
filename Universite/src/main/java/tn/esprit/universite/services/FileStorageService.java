package tn.esprit.universite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
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
    private final Path fileStorageLocation;
    private boolean isInitialized = true;

    @Autowired
    public FileStorageService(Environment env) {
        try {
            this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", "upload-directory"))
                    .toAbsolutePath().normalize();

            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            System.err.println("Could not create the directory where the uploaded files will be stored: " + ex.getMessage());
            isInitialized = false;
            throw new RuntimeException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
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
        // Check if the file storage was properly initialized
        if (!isInitialized) {
            System.err.println("File storage service was not properly initialized");
            return "default-image.jpg";
        }
        
        // Check if the MultipartFile or its original filename is null
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
            System.err.println("File is null, empty, or has no original filename");
            return "default-image.jpg";
        }

        // Generate a unique filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension;
        String filenameWithoutExtension;
        
        try {
            fileExtension = getFileExtension(originalFilename);
            filenameWithoutExtension = originalFilename.split("\\.")[0];
        } catch (Exception e) {
            // Handle potential array index issues
            filenameWithoutExtension = "file";
            fileExtension = "jpg";
        }
        
        String fileName = filenameWithoutExtension + new Date().getTime() + "-file." + fileExtension;

        try {
            // Check if the filename contains invalid characters
            if (fileName.contains("..")) {
                System.err.println("Filename contains invalid path sequence: " + fileName);
                return "default-image.jpg";
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            System.err.println("Could not store file " + fileName + ": " + ex.getMessage());
            return "default-image.jpg";
        } catch (Exception ex) {
            System.err.println("Unexpected error storing file: " + ex.getMessage());
            return "default-image.jpg";
        }
    }
    
    public ByteArrayResource loadFileAsResource(String fileName) {
        try {
            if (fileName == null || fileName.isEmpty()) {
                System.err.println("Filename is null or empty");
                return null;
            }
            
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            if (!Files.exists(filePath)) {
                System.err.println("File not found: " + fileName);
                return null;
            }
            
            byte[] fileContent = Files.readAllBytes(filePath);
            return new ByteArrayResource(fileContent);
        } catch (IOException ex) {
            System.err.println("Could not load file: " + fileName + " - " + ex.getMessage());
            return null;
        } catch (Exception ex) {
            System.err.println("Unexpected error loading file: " + ex.getMessage());
            return null;
        }
    }
}