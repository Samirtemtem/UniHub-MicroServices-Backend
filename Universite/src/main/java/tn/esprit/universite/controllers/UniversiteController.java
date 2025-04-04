package tn.esprit.universite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.universite.services.FileStorageService;
import tn.esprit.universite.services.IUniversiteService;
import tn.esprit.universite.entities.Universite;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/universite")
public class UniversiteController {

    private final IUniversiteService universiteService;
    private final FileStorageService fileStorageService;

    @Autowired
    public UniversiteController(IUniversiteService universiteService, FileStorageService fileStorageService) {
        this.universiteService = universiteService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/adduniversite")
    public ResponseEntity<?> addUniversite(@ModelAttribute Universite universite,
                                           @RequestParam(value = "file", required = false) MultipartFile imageFile) {
        try {
            if (universite == null) {
                return ResponseEntity.badRequest().body("Universite cannot be null");
            }

            // Call the service method with both arguments
            Universite result = universiteService.addUniversite(universite, imageFile);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logger
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding universite: " + e.getMessage());
        }
    }

    @GetMapping("/universite/{id}")
    public ResponseEntity<?> retrieveUniversite(@PathVariable Long id) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().body("ID cannot be null");
            }
            
            Universite result = universiteService.getUniversite(id);
            if (result == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving universite: " + e.getMessage());
        }
    }

    @GetMapping("/universites")
    public ResponseEntity<List<Universite>> retrieveUniversite() {
        try {
            List<Universite> universites = universiteService.getAllUniversites();
            return ResponseEntity.ok(universites != null ? universites : new ArrayList<>());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @PatchMapping("/universite")
    public ResponseEntity<?> updateUniversite(@ModelAttribute Universite universite, @RequestParam(value = "file", required = false) MultipartFile imageFile) {
        try {
            if (universite == null) {
                return ResponseEntity.badRequest().body("Universite cannot be null");
            }
            
            if (imageFile != null && !imageFile.isEmpty()) {
                String image = fileStorageService.storeFile(imageFile);
                universite.setImage(image);
            }
            
            Universite result = universiteService.updateUniversite(universite, imageFile);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating universite: " + e.getMessage());
        }
    }

    @DeleteMapping("/universite/{id}")
    public ResponseEntity<String> deleteUniversite(@PathVariable long id) {
        try {
            universiteService.deleteUniversite(id);
            return ResponseEntity.ok("Universite deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting universite: " + e.getMessage());
        }
    }

    @GetMapping("/searchUnivarsite/{nomUniversite}")
    public ResponseEntity<List<Universite>> rechercherParNom(@PathVariable String nomUniversite) {
        try {
            if (nomUniversite == null || nomUniversite.isEmpty()) {
                return ResponseEntity.badRequest().body(new ArrayList<>());
            }
            
            List<Universite> result = universiteService.rechercherParNom(nomUniversite);
            return ResponseEntity.ok(result != null ? result : new ArrayList<>());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }
}

