package tn.esprit.News.consumers;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tn.esprit.News.Services.IActualiteService;
import tn.esprit.News.Entities.Actualite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class NewsEventConsumer {
    
    private static final Logger logger = LoggerFactory.getLogger(NewsEventConsumer.class);
    private final IActualiteService actualiteService;

    @Autowired
    public NewsEventConsumer(IActualiteService actualiteService) {
        this.actualiteService = actualiteService;
    }

    @RabbitListener(queues = "news.events")
    public void handleEventMessage(EventMessage event, Message message) {
        String action = message.getMessageProperties().getHeaders().get("action").toString();
        Long eventId = (Long) message.getMessageProperties().getHeaders().get("eventId");
        
        try {
            switch (action) {
                case "CREATE" -> createNews(event);
                case "UPDATE" -> updateNews(eventId, event);
                case "DELETE" -> deleteNews(eventId);
                default -> logger.warn("Unknown action: {}", action);
            }
        } catch (Exception e) {
            logger.error("Failed to process event: {}", e.getMessage(), e);
            throw e; // Will be sent to DLQ
        }
    }

    private void createNews(EventMessage event) {
        Actualite actualite = mapEventToActualite(event);
        actualiteService.addActualite(actualite);
        logger.info("Created news from event: {}", event.getIdEvent());
    }

    private void updateNews(Long eventId, EventMessage event) {
        Actualite existing = actualiteService.findByEventId(eventId);
        if (existing != null) {
            updateActualiteFromEvent(existing, event);
            actualiteService.updateActualite(existing);
            logger.info("Updated news for event: {}", eventId);
        }
    }

    private void deleteNews(Long eventId) {
        Actualite existing = actualiteService.findByEventId(eventId);
        if (existing != null) {
            actualiteService.deleteActualiteById(existing.getIdActualite());
            logger.info("Deleted news for event: {}", eventId);
        }
    }

    private Actualite mapEventToActualite(EventMessage event) {
        Actualite actualite = new Actualite();
        actualite.setEventId(event.getIdEvent());
        actualite.setTitreActualite("Event: " + event.getNomEvent());
        actualite.setDescriptionActualite(createNewsDescription(event));
        actualite.setImageActualite(event.getImageEvent());
        return actualite;
    }

    private void updateActualiteFromEvent(Actualite actualite, EventMessage event) {
        actualite.setTitreActualite("Event: " + event.getNomEvent());
        actualite.setDescriptionActualite(createNewsDescription(event));
        actualite.setImageActualite(event.getImageEvent());
    }

    private String createNewsDescription(EventMessage event) {
        return String.format("Event: %s\nLocation: %s\nFrom %s to %s\n\n%s",
            event.getNomEvent(),
            event.getLieuEvent(),
            event.getDateDebEvent(),
            event.getDateFinEvent(),
            event.getDescriptionEvent()
        );
    }
}