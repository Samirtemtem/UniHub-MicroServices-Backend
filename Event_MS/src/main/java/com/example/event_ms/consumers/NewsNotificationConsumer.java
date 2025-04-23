package com.example.event_ms.consumers;

import com.example.event_ms.clients.NewsClient;
import com.example.event_ms.config.RabbitMQConfig;
import com.example.event_ms.dto.EventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Consumer for news notifications about events
 * Integrates with the News microservice to create, update, or delete news items
 */
@Component
public class NewsNotificationConsumer {

    private static final Logger logger = LoggerFactory.getLogger(NewsNotificationConsumer.class);

    private final NewsClient newsClient;

    @Autowired
    public NewsNotificationConsumer(NewsClient newsClient) {
        this.newsClient = newsClient;
    }

    /**
     * Processes event messages from the news notification queue
     * and updates the News microservice accordingly
     *
     * @param message The event message
     */
    @RabbitListener(queues = RabbitMQConfig.NEWS_NOTIFICATION_QUEUE)
    public void receiveMessage(EventMessage message) {
        try {
            logger.info("Received news notification message: {}", message);

            Mono<?> result = null;

            if (message.getEventAction() == EventMessage.EventAction.CREATE) {
                logger.info("Creating news item for new event: {}", message.getNomEvent());
                result = newsClient.createNewsFromEvent(message);
            } else {
                logger.info("Ignoring non-CREATE event action: {}", message.getEventAction());
                return;
            }

            if (result != null) {
                result.block();
            }

        } catch (Exception e) {
            logger.error("Error processing news notification message: {}", e.getMessage(), e);
            throw e;
        }
    }
}
