package com.example.event_ms.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ
 * Sets up the exchange, queues, and bindings for event notifications
 */
@Configuration
public class RabbitMQConfig {

    // Exchange name
    public static final String TOPIC_EXCHANGE_NAME = "unihub.event.exchange";
    
    // Queue names
    public static final String EMAIL_NOTIFICATION_QUEUE = "event.notification.email";
    public static final String NEWS_NOTIFICATION_QUEUE = "event.notification.news";
    public static final String STATS_QUEUE = "event.stats";
    
    // Routing keys
    public static final String EMAIL_ROUTING_KEY = "event.notification.email";
    public static final String NEWS_ROUTING_KEY = "event.notification.news";
    public static final String STATS_ROUTING_KEY = "event.stats";

    /**
     * Creates a topic exchange
     */
    @Bean
    public TopicExchange eventExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    /**
     * Creates the email notification queue
     */
    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(EMAIL_NOTIFICATION_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", "event.notification.email.dlq")
                .build();
    }

    /**
     * Creates the news notification queue
     */
    @Bean
    public Queue newsQueue() {
        return QueueBuilder.durable(NEWS_NOTIFICATION_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", "event.notification.news.dlq")
                .build();
    }

    /**
     * Creates the statistics queue
     */
    @Bean
    public Queue statsQueue() {
        return QueueBuilder.durable(STATS_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", "event.stats.dlq")
                .build();
    }

    /**
     * Creates dead letter queues for failed message handling
     */
    @Bean
    public Queue emailDeadLetterQueue() {
        return QueueBuilder.durable("event.notification.email.dlq").build();
    }

    @Bean
    public Queue newsDeadLetterQueue() {
        return QueueBuilder.durable("event.notification.news.dlq").build();
    }

    @Bean
    public Queue statsDeadLetterQueue() {
        return QueueBuilder.durable("event.stats.dlq").build();
    }

    /**
     * Binds the email queue to the exchange with the email routing key
     */
    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange eventExchange) {
        return BindingBuilder.bind(emailQueue).to(eventExchange).with(EMAIL_ROUTING_KEY);
    }

    /**
     * Binds the news queue to the exchange with the news routing key
     */
    @Bean
    public Binding newsBinding(Queue newsQueue, TopicExchange eventExchange) {
        return BindingBuilder.bind(newsQueue).to(eventExchange).with(NEWS_ROUTING_KEY);
    }

    /**
     * Binds the stats queue to the exchange with the stats routing key
     */
    @Bean
    public Binding statsBinding(Queue statsQueue, TopicExchange eventExchange) {
        return BindingBuilder.bind(statsQueue).to(eventExchange).with(STATS_ROUTING_KEY);
    }

    /**
     * Creates a JSON message converter for serializing/deserializing messages
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Configures the RabbitTemplate with the JSON message converter
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
