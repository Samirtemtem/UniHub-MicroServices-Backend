package com.example.event_ms.services;

import com.example.event_ms.entities.Evenement;
import com.example.event_ms.repositories.EvenementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import java.util.List;

@Service
public class EvenementService implements IEvenementService {

    private final EvenementRepository evenementRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public EvenementService(EvenementRepository evenementRepository, FileStorageService fileStorageService) {
        this.evenementRepository = evenementRepository;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    @Override
    public Evenement addEvent(Evenement event){
        try {
            return evenementRepository.save(event);
        } catch (Exception e) {
            System.err.println("Error adding event: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Evenement getEvent(Long idEvent){
        return this.evenementRepository.findById(idEvent).orElse(null);
    }

    @Override
    public List<Evenement> getAllEvents(){
        return this.evenementRepository.findAll();
    }

    @Override
    public void deleteEvent(Long idEvent){
        this.evenementRepository.deleteById(idEvent);
    }

    @Override
    public Evenement updateEvent(Evenement event){
        try {
            Evenement oldEvent = evenementRepository.findById(event.getIdEvent()).orElse(null);
            if (oldEvent == null) {
                return null;
            }
            
            // Preserve image if not explicitly changed
            event.setImageEvent(oldEvent.getImageEvent());
            return evenementRepository.save(event);
        } catch (Exception e) {
            System.err.println("Error updating event: " + e.getMessage());
            return null;
        }
    }

    public Evenement handleImageFileUpload(MultipartFile fileImage, long id) {
        try {
            if (fileImage == null || fileImage.isEmpty()) {
                return null;
            }
            
            Evenement event = evenementRepository.findById(id).orElse(null);
            if (event == null) {
                return null;
            }
            
            String fileName = fileStorageService.storeFile(fileImage);
            event.setImageEvent(fileName);
            return evenementRepository.save(event);
        } catch (Exception e) {
            System.err.println("Error uploading image for event: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public List<Evenement> getAllEventsSortedByDateAsc() {
        return evenementRepository.findAllByOrderByDateDebEventAsc();
    }

    @Override
    public List<Evenement> getAllEventsSortedByDateDesc() {
        return evenementRepository.findAllByOrderByDateDebEventDesc();
    }
}