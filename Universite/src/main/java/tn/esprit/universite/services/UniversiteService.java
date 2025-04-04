package tn.esprit.universite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.universite.repositories.UniversiteRepository;
import tn.esprit.universite.entities.Universite;

import java.util.List;

@Service
public class UniversiteService implements IUniversiteService {

    private final FileStorageService fileStorageService;
    private final UniversiteRepository universiteRepository;

    @Autowired
    public UniversiteService(FileStorageService fileStorageService, UniversiteRepository universiteRepository) {
        this.fileStorageService = fileStorageService;
        this.universiteRepository = universiteRepository;
    }

    @Override
    public Universite addUniversite(Universite universite, MultipartFile imageFile) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = fileStorageService.storeFile(imageFile);
                universite.setImage(imageUrl);
            }

            // Optional: Validate the universite object here
            // validateUniversite(universite); // Implement this method as needed

            return universiteRepository.save(universite);
        } catch (Exception e) {
            // Print the error message to the console
            System.err.println("Error in addUniversite: " + e.getMessage());
            e.printStackTrace(); // Optionally print the stack trace
            throw e; // Consider throwing a custom exception
        }
    }

    @Override
    public Universite getUniversite(Long id) {
        return universiteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Universite> getAllUniversites() {
        return universiteRepository.findAll();
    }

    @Override
    public void deleteUniversite(long idUniversite) {
        universiteRepository.deleteById(idUniversite);
    }

    @Override
    public Universite updateUniversite(Universite universite, MultipartFile imageFile) {
        try {
            Universite univ = universiteRepository.findById(universite.getIdUniversite()).orElse(null);
            if (univ != null) {
                // Keep existing image if no new image is provided
                if (imageFile != null && !imageFile.isEmpty()) {
                    String image = fileStorageService.storeFile(imageFile);
                    universite.setImage(image);
                } else {
                    universite.setImage(univ.getImage());
                }
                return universiteRepository.save(universite);
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error in updateUniversite: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Universite> rechercherParNom(String nomUniversite) {
        return universiteRepository.findByNomUniversiteContainingIgnoreCase(nomUniversite);
    }
}
