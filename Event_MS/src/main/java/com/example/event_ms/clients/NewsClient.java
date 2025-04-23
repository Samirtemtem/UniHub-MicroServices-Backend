package com.example.event_ms.clients;

import com.example.event_ms.dto.EventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Client for interacting with the News microservice
 * Only handles event creation (no updates or deletes)
 */
@Service
public class NewsClient {

    private static final Logger logger = LoggerFactory.getLogger(NewsClient.class);
    private final RestTemplate restTemplate;
    private final String newsServiceUrl;
    
    /**
     * Constructor that initializes the RestTemplate with the News service URL
     * 
     * @param newsServiceUrl The URL of the News service
     */
    public NewsClient(@Value("${news.service.url:http://news:8085}") String newsServiceUrl) {
        this.restTemplate = new RestTemplate();
        this.newsServiceUrl = newsServiceUrl;
        
        logger.info("Initialized NewsClient with URL: {}", newsServiceUrl);
    }
    
    /**
     * Creates a news item based on an event
     * 
     * @param eventMessage The event message
     * @return A Mono that completes when the news item is created
     */
    public Mono<Long> createNewsFromEvent(EventMessage eventMessage) {
        return Mono.fromCallable(() -> {
            try {
                // Create a news article payload
                Map<String, Object> newsArticle = new HashMap<>();
                
                // Set the title
                newsArticle.put("titreActualite", "New Event: " + eventMessage.getNomEvent());
                
                // Create a description that includes event details
                StringBuilder description = new StringBuilder();
                description.append("Event Details:\n\n");
                description.append("Name: ").append(eventMessage.getNomEvent()).append("\n");
                description.append("Description: ").append(eventMessage.getDescriptionEvent()).append("\n");
                description.append("Location: ").append(eventMessage.getLieuEvent()).append("\n");
                description.append("Start Date: ").append(eventMessage.getDateDebEvent()).append("\n");
                description.append("End Date: ").append(eventMessage.getDateFinEvent()).append("\n");
                description.append("\nThis news item was automatically generated from an event.");
                
                newsArticle.put("descriptionActualite", description.toString());
                
                // Set the date to current date
                newsArticle.put("dateActualite", new Date());
                
                // Use the event image if available
                if (eventMessage.getImageEvent() != null && !eventMessage.getImageEvent().isEmpty()) {
                    newsArticle.put("imageActualite", eventMessage.getImageEvent());
                }
                
                // Set headers
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                
                // Create the request entity
                HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(newsArticle, headers);
                
                // Send the request to create a news article
                ResponseEntity<Map> response = restTemplate.postForEntity(
                        newsServiceUrl + "/news/addActualite",
                        requestEntity,
                        Map.class);
                
                logger.info("Created news article for event: {}. Response status: {}", 
                        eventMessage.getIdEvent(), response.getStatusCode());
                
                // Return the ID of the created news item if available
                if (response.getBody() != null && response.getBody().containsKey("idActualite")) {
                    return Long.valueOf(response.getBody().get("idActualite").toString());
                }
                
                return 0L;
                
            } catch (Exception e) {
                logger.error("Error creating news article for event {}: {}", 
                        eventMessage.getIdEvent(), e.getMessage(), e);
                throw e;
            }
        });
    }
    
    /**
     * Updates a news item based on an event
     * This is a placeholder method that does nothing since we don't track relationships
     * 
     * @param eventMessage The event message
     * @return A Mono that completes immediately
     */
    public Mono<Boolean> updateNewsFromEvent(EventMessage eventMessage) {
        logger.info("Update operation ignored for event: {}", eventMessage.getIdEvent());
        return Mono.just(true);
    }
    
    /**
     * Deletes a news item based on an event
     * This is a placeholder method that does nothing since we don't track relationships
     * 
     * @param eventMessage The event message
     * @return A Mono that completes immediately
     */
    public Mono<Boolean> deleteNewsFromEvent(EventMessage eventMessage) {
        logger.info("Delete operation ignored for event: {}", eventMessage.getIdEvent());
        return Mono.just(true);
    }
}
