package com.example.event_ms.controllers;

import com.example.event_ms.entities.Evenement;
import com.example.event_ms.services.IEvenementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/event")
public class EvenementController {
    
    private final IEvenementService eventService;
    
    @Autowired
    public EvenementController(IEvenementService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/addEvent")
    public Evenement addEvent(@RequestBody Evenement event) {
        return eventService.addEvent(event);
    }

    @GetMapping("/getOneEvent/{id}")
    public Evenement getEvent(@PathVariable Long id) {
        return eventService.getEvent(id);
    }

    @GetMapping("/events")
    public List<Evenement> getAllEvent() {
        return eventService.getAllEvents();
    }

    @DeleteMapping("/deleteEvent/{id}")
    public void deleteEvent(@PathVariable Long id) {
        this.eventService.deleteEvent(id);
    }

    @PutMapping("/updateEvent")
    public Evenement updateEvent(@RequestBody Evenement event) {
        return this.eventService.updateEvent(event);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @PostMapping("/events/uploadImage/{id}")
    public Evenement handleImageFileUpload(@RequestParam("fileImage") MultipartFile fileImage, @PathVariable long id) {
        return eventService.handleImageFileUpload(fileImage, id);
    }
    
    @GetMapping("/sortedByDateAsc")
    public List<Evenement> getAllEventsSortedByDateAsc() {
        return eventService.getAllEventsSortedByDateAsc();
    }

    @GetMapping("/sortedByDateDesc")
    public List<Evenement> getAllEventsSortedByDateDesc() {
        return eventService.getAllEventsSortedByDateDesc();
    }
}