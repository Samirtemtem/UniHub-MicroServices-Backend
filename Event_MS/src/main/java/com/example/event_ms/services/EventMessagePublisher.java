package com.example.event_ms.services;

import com.example.event_ms.config.RabbitMQConfig;
import com.example.event_ms.dto.EventMessage;
import com.example.event_ms.entities.Evenement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service responsible for publishing event messages to RabbitMQ
 */
@Service
public class EventMessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(EventMessagePublisher.class);
    
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public EventMessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publishes an event message to all queues
     * 
     * @param event The event entity
     * @param action The action performed on the event (CREATE, UPDATE, DELETE)
     */
    public void publishEventMessage(Evenement event, EventMessage.EventAction action) {
        try {
            EventMessage message = mapToEventMessage(event, action);
            
            // Publish to email notification queue
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.TOPIC_EXCHANGE_NAME,
                    RabbitMQConfig.EMAIL_ROUTING_KEY,
                    message
            );
            logger.info("Event message sent to email queue: {}", message);
            
            // Publish to news notification queue
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.TOPIC_EXCHANGE_NAME,
                    RabbitMQConfig.NEWS_ROUTING_KEY,
                    message
            );
            logger.info("Event message sent to news queue: {}", message);
            
            // Publish to stats queue
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.TOPIC_EXCHANGE_NAME,
                    RabbitMQConfig.STATS_ROUTING_KEY,
                    message
            );
            logger.info("Event message sent to stats queue: {}", message);
            
        } catch (Exception e) {
            logger.error("Error publishing event message: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to publish event message", e);
        }
    }

    /**
     * Maps an Evenement entity to an EventMessage DTO
     * 
     * @param event The event entity
     * @param action The action performed on the event
     * @return The mapped EventMessage
     */
    private EventMessage mapToEventMessage(Evenement event, EventMessage.EventAction action) {
        EventMessage message = new EventMessage();
        message.setIdEvent(event.getIdEvent());
        message.setNomEvent(event.getNomEvent());
        message.setDescriptionEvent(event.getDescriptionEvent());
        message.setDateDebEvent(event.getDateDebEvent());
        message.setDateFinEvent(event.getDateFinEvent());
        message.setLieuEvent(event.getLieuEvent());
        message.setImageEvent(event.getImageEvent());
        message.setEventAction(action);
        return message;
    }
}
