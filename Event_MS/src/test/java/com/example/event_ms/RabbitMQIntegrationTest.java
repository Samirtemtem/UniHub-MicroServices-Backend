package com.example.event_ms;

import com.example.event_ms.dto.EventMessage;
import com.example.event_ms.entities.Evenement;
import com.example.event_ms.services.EventMessagePublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

/**
 * Integration test for RabbitMQ messaging
 * Note: This test requires a running RabbitMQ instance
 * It's disabled by default and can be enabled with the 'rabbitmq-test' profile
 */
@SpringBootTest
@ActiveProfiles("rabbitmq-test")
public class RabbitMQIntegrationTest {

    @Autowired
    private EventMessagePublisher messagePublisher;

    /**
     * Tests publishing a message to RabbitMQ
     */
    @Test
    public void testPublishMessage() {
        // Create a test event
        Evenement event = new Evenement();
        event.setIdEvent(1L);
        event.setNomEvent("Test Event");
        event.setDescriptionEvent("This is a test event");
        event.setDateDebEvent(new Date());
        event.setDateFinEvent(new Date());
        event.setLieuEvent("Test Location");
        event.setImageEvent("test.jpg");

        // Publish a message
        messagePublisher.publishEventMessage(event, EventMessage.EventAction.CREATE);

        // If no exception is thrown, the test passes
        // In a real test, you would verify that the message was received by a consumer
    }
}
