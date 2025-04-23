package tn.esprit.specialiteMS.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.specialiteMS.Repositories.SpecialiteRepository;
import tn.esprit.specialiteMS.entities.Specialite;

import java.util.List;

@Service
public class SpecialiteService implements ISpecialiteService{

    private final SpecialiteRepository specialiteRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public SpecialiteService(SpecialiteRepository specialiteRepository, FileStorageService fileStorageService) {
        this.specialiteRepository = specialiteRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public Specialite add(Specialite specialite) {
        return specialiteRepository.save(specialite);
    }

    @Override
    public Specialite getById(Long id) {
        return specialiteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Specialite> getAll() {
        return specialiteRepository.findAll();
    }

    @Override
    public void delete(long id) {
        specialiteRepository.deleteById(id);
    }

    @Override
    public Specialite update(Specialite specialite) {
        try {
            Specialite oldSpecialite = specialiteRepository.findById(specialite.getId()).orElse(null);
            if (oldSpecialite == null) {
                return null;
            }
            specialite.setImageUrl(oldSpecialite.getImageUrl());
            specialite.setPlanEtudePdf(oldSpecialite.getPlanEtudePdf());
            return specialiteRepository.save(specialite);
        } catch (Exception e) {
            System.err.println("Error updating specialite: " + e.getMessage());
            return null;
        }
    }

    public Specialite handlePlanEtudeFileUpload(MultipartFile file, long id) {
        try {
            if (file == null || file.isEmpty()) {
                return null;
            }
            
            Specialite specialite = specialiteRepository.findById(id).orElse(null);
            if (specialite == null) {
                return null;
            }
            
            String fileName = fileStorageService.storeFile(file);
            specialite.setPlanEtudePdf(fileName);
            return specialiteRepository.save(specialite);
        } catch (Exception e) {
            System.err.println("Error uploading plan étude for specialite: " + e.getMessage());
            return null;
        }
    }

    public Specialite handleImageFileUpload(MultipartFile fileImage, long id) {
        try {
            if (fileImage == null || fileImage.isEmpty()) {
                return null;
            }
            
            Specialite specialite = specialiteRepository.findById(id).orElse(null);
            if (specialite == null) {
                return null;
            }
            
            String fileName = fileStorageService.storeFile(fileImage);
            specialite.setImageUrl(fileName);
            return specialiteRepository.save(specialite);
        } catch (Exception e) {
            System.err.println("Error uploading image for specialite: " + e.getMessage());
            return null;
        }
    }
}
