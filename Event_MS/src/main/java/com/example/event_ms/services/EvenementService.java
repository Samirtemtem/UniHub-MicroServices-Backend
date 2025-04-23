package com.example.event_ms.services;

import com.example.event_ms.dto.EventMessage;
import com.example.event_ms.entities.Evenement;
import com.example.event_ms.repositories.EvenementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(EvenementService.class);

    private final EvenementRepository evenementRepository;
    private final FileStorageService fileStorageService;
    private final EventMessagePublisher messagePublisher;

    @Autowired
    public EvenementService(EvenementRepository evenementRepository,
                           FileStorageService fileStorageService,
                           EventMessagePublisher messagePublisher) {
        this.evenementRepository = evenementRepository;
        this.fileStorageService = fileStorageService;
        this.messagePublisher = messagePublisher;
    }

    @Transactional
    @Override
    public Evenement addEvent(Evenement event){
        try {
            Evenement savedEvent = evenementRepository.save(event);
            // Publish message for event creation
            messagePublisher.publishEventMessage(savedEvent, EventMessage.EventAction.CREATE);
            logger.info("Event created and message published: {}", savedEvent.getIdEvent());
            return savedEvent;
        } catch (Exception e) {
            logger.error("Error adding event: {}", e.getMessage(), e);
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
        try {
            // Get the event before deleting it to send in the message
            Evenement event = evenementRepository.findById(idEvent).orElse(null);
            if (event != null) {
                evenementRepository.deleteById(idEvent);
                // Publish message for event deletion
                messagePublisher.publishEventMessage(event, EventMessage.EventAction.DELETE);
                logger.info("Event deleted and message published: {}", idEvent);
            } else {
                logger.warn("Attempted to delete non-existent event with ID: {}", idEvent);
            }
        } catch (Exception e) {
            logger.error("Error deleting event: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Evenement updateEvent(Evenement event){
        try {
            Evenement oldEvent = evenementRepository.findById(event.getIdEvent()).orElse(null);
            if (oldEvent == null) {
                logger.warn("Attempted to update non-existent event with ID: {}", event.getIdEvent());
                return null;
            }

            // Preserve image if not explicitly changed
            event.setImageEvent(oldEvent.getImageEvent());
            Evenement updatedEvent = evenementRepository.save(event);

            // Publish message for event update
            messagePublisher.publishEventMessage(updatedEvent, EventMessage.EventAction.UPDATE);
            logger.info("Event updated and message published: {}", updatedEvent.getIdEvent());

            return updatedEvent;
        } catch (Exception e) {
            logger.error("Error updating event: {}", e.getMessage(), e);
            return null;
        }
    }
/*
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
    */
public Evenement handleImageFileUpload(MultipartFile fileImage, long id) {
    try {
        // Check if the file is empty
        if (fileImage == null || fileImage.isEmpty()) {
            throw new IllegalArgumentException("No file provided for upload");
        }

        // Find the event by ID
        Evenement event = evenementRepository.findById(id).orElse(null);
        if (event == null) {
            throw new IllegalArgumentException("Event not found with ID: " + id);
        }

        // Upload the image to Cloudinary and get the secure URL
        String fileUrl = fileStorageService.storeFile(fileImage);
        event.setImageEvent(fileUrl);  // Set the Cloudinary URL in the event entity

        // Save the updated event
        Evenement updatedEvent = evenementRepository.save(event);

        // Publish message for event update
        messagePublisher.publishEventMessage(updatedEvent, EventMessage.EventAction.UPDATE);
        logger.info("Event image updated and message published: {}", updatedEvent.getIdEvent());

        return updatedEvent;

    } catch (Exception e) {
        logger.error("Error uploading image for event: {}", e.getMessage(), e);
        throw new RuntimeException("Failed to upload image", e);  // Properly handle the exception
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