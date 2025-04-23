package com.example.event_ms.consumers;

import com.example.event_ms.config.RabbitMQConfig;
import com.example.event_ms.dto.EventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consumer for event statistics tracking
 */
@Component
public class StatsConsumer {

    private static final Logger logger = LoggerFactory.getLogger(StatsConsumer.class);

    /**
     * Processes event messages from the stats queue
     * 
     * @param message The event message
     */
    @RabbitListener(queues = RabbitMQConfig.STATS_QUEUE)
    public void receiveMessage(EventMessage message) {
        try {
            logger.info("Received stats message: {}", message);
            
            switch (message.getEventAction()) {
                case CREATE:
                    logger.info("Would increment event creation counter for: {}", message.getNomEvent());
                    break;
                case UPDATE:
                    logger.info("Would increment event update counter for: {}", message.getNomEvent());
                    break;
                case DELETE:
                    logger.info("Would increment event deletion counter for: {}", message.getNomEvent());
                    break;
                default:
                    logger.warn("Unknown event action: {}", message.getEventAction());
            }
            
        } catch (Exception e) {
            logger.error("Error processing stats message: {}", e.getMessage(), e);
            throw e;
        }
    }
}
