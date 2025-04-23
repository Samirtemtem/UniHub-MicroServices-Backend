package com.example.event_ms.consumers;

import com.example.event_ms.config.RabbitMQConfig;
import com.example.event_ms.dto.EventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Consumer for email notifications about events
 */
@Component
public class EmailNotificationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationConsumer.class);

    /**
     * Processes event messages from the email notification queue
     * 
     * @param message The event message
     */
    @RabbitListener(queues = RabbitMQConfig.EMAIL_NOTIFICATION_QUEUE)
    public void receiveMessage(EventMessage message) {
        try {
            logger.info("Received email notification message: {}", message);
            
            switch (message.getEventAction()) {
                case CREATE:
                    logger.info("Would send email about new event: {}", message.getNomEvent());
                    break;
                default:
                    logger.warn("Unknown event action: {}", message.getEventAction());
            }
            
        } catch (Exception e) {
            logger.error("Error processing email notification message: {}", e.getMessage(), e);
            throw e;
        }
    }
}
