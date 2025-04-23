package tn.esprit.News.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.News.Entities.Actualite;
import tn.esprit.News.Services.IActualiteService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/news")
public class actualiteController {
    
    private final IActualiteService actualiteService;
    
    @Autowired
    public actualiteController(IActualiteService actualiteService) {
        this.actualiteService = actualiteService;
    }

    @PostMapping("/addActualite")
    public ResponseEntity<?> addActualite(@RequestBody Actualite actualite) {
        try {
            if (actualite == null) {
                return ResponseEntity.badRequest().body("Actualite cannot be null");
            }
            // Set current date if not provided
            if (actualite.getDateActualite() == null) {
                actualite.setDateActualite(new Date());
            }
            Actualite result = actualiteService.addActualite(actualite);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding actualite: " + e.getMessage());
        }
    }

    @GetMapping("/getOneActualite/{id}")
    public ResponseEntity<?> getActualite(@PathVariable Long id) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().body("ID cannot be null");
            }
            Actualite result = actualiteService.getActualite(id);
            if (result == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving actualite: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Actualite>> getAllActualites() {
        try {
            List<Actualite> actualites = actualiteService.getAllActualites();
            return ResponseEntity.ok(actualites != null ? actualites : new ArrayList<>());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @DeleteMapping("/deleteActualite/{id}")
    public ResponseEntity<String> deleteClubById(@PathVariable Long id) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().body("ID cannot be null");
            }
            actualiteService.deleteActualiteById(id);
            return ResponseEntity.ok("Actualite deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting actualite: " + e.getMessage());
        }
    }

    @PutMapping("/updateActualite")
    public ResponseEntity<?> updateActualite(@RequestBody Actualite actualite) {
        try {
            if (actualite == null) {
                return ResponseEntity.badRequest().body("Actualite cannot be null");
            }
            Actualite result = actualiteService.updateActualite(actualite);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating actualite: " + e.getMessage());
        }
    }

    @PostMapping("/uploadImage/{id}")
    public ResponseEntity<?> handleImageFileUpload(@RequestParam("fileImage") MultipartFile fileImage, @PathVariable long id) {
        try {
            if (fileImage == null || fileImage.isEmpty()) {
                return ResponseEntity.badRequest().body("Image file cannot be null or empty");
            }
            Actualite result = actualiteService.handleImageFileUpload(fileImage, id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading image: " + e.getMessage());
        }
    }

    @PostMapping("/shareFb/{id}")
    public ResponseEntity<String> shareFb(@PathVariable Long id) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().body("ID cannot be null");
            }
            String result = actualiteService.shareFb(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sharing on Facebook: " + e.getMessage());
        }
    }
}
